#!/usr/bin/python
# -*- coding:utf-8 -*-
import glob
import random
import time
from math import *

import numpy as np
import pandas as pd
from sklearn.cross_validation import StratifiedKFold, cross_val_score
from sklearn.ensemble import AdaBoostClassifier, RandomForestClassifier
from sklearn.grid_search import GridSearchCV
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.pipeline import Pipeline
from sklearn.svm import SVC
from sklearn.tree import DecisionTreeClassifier

from CCSVM import *
from gaft.gaft import GAEngine
from gaft.gaft.components import GAIndividual
from gaft.gaft.components import GAPopulation
from gaft.gaft.operators import FlipBitMutation
from gaft.gaft.operators import RouletteWheelSelection
from gaft.gaft.operators import UniformCrossover
from gaft.gaft.plugin_interfaces import OnTheFlyAnalysis


def getSkipRowsAndAttrs(filepath):
  skipRows = 0
  attrs = -1
  with open(filepath) as f:
    line = f.readline()
    while not line.startswith("@data"):
      skipRows += 1
      if line.startswith("@attribute"):
        attrs += 1
      line = f.readline()
  skipRows += 1
  return skipRows, attrs


# 获取标记为label的样本
def getExamples(x, y, label):
  xLabeled = []
  for i in range(len(y)):
    if y[i] == label:
      xLabeled.append(x[i])
  return xLabeled


def stratifiedSampling(x, y, scale):
  xSelected = []
  ySelected = []
  posiNum, negaNum = examplesDistri(y)
  idxSelectedPosi = random.sample(range(int(len(y) * posiNum / len(y))),
                                  int(scale * posiNum / len(y)))
  idxSelectedNega = random.sample(range(int(len(y) * negaNum / len(y))),
                                  int(scale * negaNum / len(y)))
  idxSelectedNega.sort()
  idxSelectedPosi.sort()
  posiCount = 0
  negaCount = 0
  posiIdx = 0
  negaIdx = 0
  for i in range(len(y)):
    if y[i] == 1:
      if posiIdx < len(idxSelectedPosi) and idxSelectedPosi[
        posiIdx] == posiCount:
        xSelected.append(x[i])
        ySelected.append(y[i])
        posiIdx += 1
      posiCount += 1
    else:
      if negaIdx < len(idxSelectedNega) and idxSelectedNega[
        negaIdx] == negaCount:
        xSelected.append(x[i])
        ySelected.append(y[i])
        negaIdx += 1
      negaCount += 1
  return xSelected, ySelected


# 从文件夹读取测试数据
def readData(dirPath):
  result = {}
  for filename in glob.glob(dirPath + '*.arff'):
    # if not filename.endswith("ar5.arff"):
    #   continue
    skipRows, attrs = getSkipRowsAndAttrs(filename)
    data = pd.read_csv(filename, header=None, skiprows=skipRows)
    y = pd.Categorical(data[attrs]).codes
    y.shape = len(y), 1
    data = data.values
    x = data[:, : -1]
    # 样本数量大于300时，随机抽取300份
    if len(y) > 1000:
      posiNum, negaNum = examplesDistri(y)
      x, y = stratifiedSampling(x, y, 1000)
      posiNum, negaNum = examplesDistri(y)
    result[filename] = np.hstack((x, y))
  return result


# 统计分对的正类，错分的正类，分对的负类，错分的负类
def resultStatistic(y, yHat):
  TN = TP = FP = FN = 0.0
  for index in range(len(y)):
    if yHat[index] == 1:
      if y[index] == 1:
        TP += 1
      else:
        FP += 1
    else:
      if y[index] == 0:
        TN += 1
      else:
        FN += 1
  return TP, FP, TN, FN


# 用G-mean值评价模型
def g_mean(estimator, x, y):
  yHat = estimator.predict(x)
  TP, FP, TN, FN = resultStatistic(y, yHat)
  TPrate = 0.0
  if TP + FN > 0:
    TPrate = TP / (TP + FN)
  TNrate = 0.0
  if TN + FP > 0:
    TNrate = TN / (TN + FP)
  return sqrt(TPrate * TNrate)


# 另外一种模型评价方法，目前没有用到
def modelValidation(model, x, y):
  # 把数据分层抽样
  kfold = StratifiedKFold(y=y, n_folds=10, random_state=1)
  totalTP = totalFP = totalTN = totalFN = 0
  for k, (train, test) in enumerate(kfold):
    model.fit(x[train], y[train])
    yHat = model.predict(x[test])
    TP, FP, TN, FN = resultStatistic(y[test], yHat)
    totalTP += TP
    totalFP += FP
    totalTN += TN
    totalFN += FN
  TPrate = 0.0
  if totalTP + totalFN > 0:
    TPrate = totalTP / (totalTP + totalFN)
  TNrate = 0.0
  FPrate = 0.0
  if totalTN + totalFP > 0:
    TNrate = totalTN / (totalTN + totalFP)
    FPrate = totalFP / (totalTN + totalFP)
  bal = 1 - sqrt((0 - TPrate) ** 2 + (1 - FPrate) ** 2) / sqrt(2)
  gmean = sqrt(TPrate * TNrate)
  return bal, gmean


# 十折交叉验证
def crossValidation(model, x, y, scorer):
  scores = cross_val_score(estimator=model,
                           X=x,
                           y=y,
                           cv=5,
                           n_jobs=-1,
                           scoring=scorer)
  return np.mean(scores)


# 统计样本正类和负类的数量
# 返回正类和负类样本的数量
def examplesDistri(y):
  positiveCounter = 0
  negativeCounter = 0
  for i in y:
    if i == 1:
      positiveCounter += 1
    else:
      negativeCounter += 1
  return positiveCounter, negativeCounter


def GASvm(dataSetName, x, y):
  x = preprocessing.scale(x)
  search(dataSetName, x, y)


# 获取制定参数建立模型的F1值
def getSvmModelF1(weight, C, gama, features, x, y, neededSelFeatures):
  if neededSelFeatures:
    x = featuresSeclection(x, features)
  svmModel = SVC(class_weight={1: weight}, gamma=gama, C=C)
  return crossValidation(svmModel, x, y, scorer='f1')


def featuresSeclection(x, features):
  # 选择特征
  features = format(np.long(features), 'b')
  selectedFetures = []
  for i, ch in enumerate(features):
    if ch == '1':
      selectedFetures.append(i)
  return x[:, selectedFetures]


# 获取制定参数建立模型的G-mean值
def getSvmModelGmean(weight, C, gama, features, x, y, neededSelFeatures):
  if neededSelFeatures:
    x = featuresSeclection(x, features)
  svmModel = SVC(class_weight={1: weight}, gamma=gama, C=C)
  return crossValidation(svmModel, x, y, scorer=g_mean)


def f1Fitness(indv, x, y):
  # return random.randint(1, 10) * 1.0
  # print(indv.variants)
  weight, C, gama, features = indv.variants
  return getSvmModelF1(weight, C, gama, features, x, y, False)


def gmeanFitness(engine, indv, x, y):
  # return random.randint(1, 10) * 1.0
  key = engine.getKey(indv)
  if engine.getKey(indv) in engine.resultTmp:
    return engine.resultTmp[key]
  weight, C, gama, features = indv.variants
  val = getSvmModelGmean(weight, C, gama, features, x, y, False)
  engine.resultTmp[key] = val
  return val


# 迭代过程中结果保存
class AnalySis(OnTheFlyAnalysis):
  master_only = True
  interval = 1

  def setup(self, ng, engine):
    # Generation numbers.
    self.ngs = []

    # Best fitness in each generation.
    self.fitness_values = []

    # Best variants.
    self.variants = []

    self.f1 = []

  def register_step(self, g, population, engine):
    best_indv = population.best_indv(engine.fitness)
    f1 = f1Fitness(best_indv, engine.dataSet.x, engine.dataSet.y)
    msg = 'Generation: {},\n best g-mean: {:.3f}, best-f1:{:.3f}'. \
      format(g, engine.fitness(best_indv), f1)
    engine.logger.info(best_indv.variants)
    engine.logger.info(msg)

    self.ngs.append(g)
    self.variants.append(best_indv.variants)
    self.fitness_values.append(engine.fitness(best_indv))
    self.f1.append(f1)

  def finalize(self, population, engine):
    best_indv = population.best_indv(engine.fitness)
    x = best_indv.variants
    y = engine.fitness(best_indv)
    f1 = f1Fitness(best_indv, engine.dataSet.x, engine.dataSet.y)
    msg = 'Optimal solution:\n ({},\n gmean:{:.3f},f1:{:.3f})\n'.format(x, y,
                                                                        f1)
    with open('result.txt', 'a', encoding='utf-8') as f:
      nowTime = time.strftime('%Y-%m-%d %H:%m', time.localtime(time.time()))
      f.write('%s\n%s %s\n' % (nowTime, engine.dataSet.name, msg))
    self.logger.info(msg)

    with open('best_fit.py', 'a',
              encoding='utf-8') as f:
      f.write('best_fit[\"%s\"] = [\n' % engine.dataSet.name)
      for ng, x, y, f1 in zip(self.ngs, self.variants, self.fitness_values,
                              self.f1):
        f.write('    ({}, {}, {}, {}),\n'.format(ng, x, y, f1))
      f.write(']\n\n')

    self.logger.info('Best fitness values are written to best_fit.py')


class DataSet:
  x = None
  y = None
  name = None

  def __init__(self, name, x, y):
    self.name = name
    self.x = x
    self.y = y


def search(dataSetName, x, y):
  # Define  weight, C, gamma, features
  features = len(x[0])
  indv_template = GAIndividual(
      ranges=[(1, 20), (1, 1000), (0.0001, 1), (1, 2 ** features - 1)],
      encoding='binary',
      eps=[0.001, 0.01, 0.0001, 1])
  population = GAPopulation(indv_template=indv_template, size=30).init()

  # Create genetic operators.
  selection = RouletteWheelSelection()
  crossover = UniformCrossover(pc=0.8, pe=0.5)
  mutation = FlipBitMutation(pm=0.1)

  # Create genetic algorithm engine.
  # Here we pass all built-in analysis to engine constructor.
  engine = GAEngine(population=population, selection=selection,
                    crossover=crossover, mutation=mutation,
                    analysis=[AnalySis],
                    dataSet=DataSet(dataSetName, x, y))

  engine.fitness_register(gmeanFitness)
  engine.dynamic_linear_scaling(target='max', ksi0=0.5, r=0.9)
  engine.run(ng=10)


def getFileName():
  localTime = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
  return 'result_' + localTime + '.txt'


def decisionTree(x, y):
  # 决策树
  deTreeModel = DecisionTreeClassifier(criterion='entropy')

  f1 = crossValidation(deTreeModel, x, y, 'f1')
  gmean = crossValidation(deTreeModel, x, y, g_mean)
  return gmean, f1


# 随机森林
def randomForest(x, y):
  gmean = crossValidation(RandomForestClassifier(n_estimators=10), x, y,
                          g_mean)
  f1 = crossValidation(RandomForestClassifier(n_estimators=10), x, y, 'f1')
  return gmean, f1


def kNN(x, y):
  # 测试k近邻模型的准确率
  k_range = range(1, 31)
  bestGmean = 0
  bestF1 = 0
  for i in k_range:
    gmean = crossValidation(KNeighborsClassifier(n_neighbors=i), x, y, gmean)
    f1 = crossValidation(KNeighborsClassifier(n_neighbors=i), x, y, 'f1')
    if gmean > bestGmean:
      bestGmean = gmean
      bestF1 = f1
  return bestGmean, bestF1


def beyes(x, y):
  f1 = crossValidation(GaussianNB(), x, y, 'f1')
  gmean = crossValidation(GaussianNB(), x, y, g_mean)
  return gmean, f1


def adaboost(x, y):
  baseEstimator = DecisionTreeClassifier(criterion='entropy')
  adaBoostModel = AdaBoostClassifier(base_estimator=baseEstimator,
                                     n_estimators=10, learning_rate=0.1)
  f1 = crossValidation(adaBoostModel, x, y, 'f1')
  gmean = crossValidation(adaBoostModel, x, y, g_mean)
  return gmean, f1


def svm(x, y):
  x = preprocessing.scale(x)
  pipeSvc = Pipeline([('clf', SVC())])
  rangeC = np.linspace(1, 1000, num=30)
  rangeGama = np.linspace(0, 1, num=100)
  paramGrid = [{
    'clf__C': rangeC,
    'clf__gamma': rangeGama
  }
  ]
  greadSearch = GridSearchCV(estimator=pipeSvc,
                             param_grid=paramGrid,
                             scoring=g_mean,
                             cv=5,
                             n_jobs=-1)
  greadSearch = greadSearch.fit(x, y)
  bestParams = greadSearch.best_params_
  bestModel = SVC(C=bestParams['clf__C'], gamma=bestParams['clf__gamma'])
  f1 = crossValidation(bestModel, x, y, 'f1')
  return greadSearch.best_score_, f1


def getDataInfo(filename, data):
  x = data[:, : -1]
  y = data[:, -1]
  f = open('dataInfo.txt', 'w')
  print(filename)
  print('Samples:', len(y))
  print('Attributes:', len(x[0]))
  posiNum, negaNum = examplesDistri(y)
  print('Defect Class:', posiNum)
  print('Non-Defect Class:', negaNum)
  print('Defect:%.2f\n' % (posiNum * 100.0 / (negaNum + posiNum)))


def compare(f, x, y):
  gmean, f1 = svm(x, y)
  f.write('svm模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))
  print('svm模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))

  gmean, f1 = adaboost(x, y)
  f.write('adaboost模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))
  print('adaboost模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))

  gmean, f1 = beyes(x, y)
  f.write('贝叶斯模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))
  print('贝叶斯模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))

  gmean, f1 = randomForest(x, y)
  f.write('随机森林模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))
  print('随机森林模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))

  gmean, f1 = kNN(x, y)
  f.write('k近邻模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))
  print('k近邻模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))

  gmean, f1 = decisionTree(x, y)
  f.write('决策树模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))
  print('决策树模型：\ng-mean值为%.2f,f1值%.2f\n' % (gmean, f1))


def main():
  dataSets = readData("./data/")
  for filename, data in dataSets.items():
    # if not filename.startswith('./data/CM1'):
    #   continue
    # getDataInfo(filename, data)
    x = data[:, : -1]
    y = data[:, -1]
    x = x.astype(np.float64)
    y = y.astype(np.int32)
    print('\n数据集%s\n' % (filename))
    print('样本数量:', len(y))
    print('特征数量:', len(x[0]))
    # compare(f, x, y)
    GASvm(filename, x, y)
    # CCSVM(x, y)


if __name__ == "__main__":
  main()
  # computeF1()
  # test()
  # customCompute()

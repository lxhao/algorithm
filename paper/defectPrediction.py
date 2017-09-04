#!/usr/bin/python
# -*- coding:utf-8 -*-
import glob

from math import *
import numpy as np
import pandas as pd
from sklearn import svm
from sklearn.cross_validation import StratifiedKFold, train_test_split
from sklearn.ensemble import AdaBoostClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score
from sklearn.metrics import classification_report
from sklearn.metrics import fbeta_score
from sklearn.model_selection import cross_val_score
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier


# 从文件读取需要跳过的行数和数据的属性数量
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


# 从文件夹读取测试数据
def readData(dirPath):
  result = {}
  for filename in glob.glob(dirPath + '*.arff'):
    if not filename.endswith("ar1.arff"):
      continue
    skipRows, attrs = getSkipRowsAndAttrs(filename)
    data = pd.read_csv(filename, header=None, skiprows=skipRows)
    x, y = data[range(0, attrs)], pd.Categorical(data[attrs]).codes
    y.shape = len(y), 1
    result[filename] = np.hstack((x, y))
  return result


# 计算g-mean和val值
def getGmeanAndBal(y, yHat):
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


# 模型评估
def modelValidation(model, x, y):
  # 把数据分层抽样
  kfold = StratifiedKFold(y=y, n_folds=10, random_state=1)
  totalTP = totalFP = totalTN = totalFN = 0
  for k, (train, test) in enumerate(kfold):
    model.fit(x[train], y[train])
    yHat = model.predict(x[test])
    # print 'y:'
    # print y[test]
    # print 'yHat:'
    # print yHat
    TP, FP, TN, FN = getGmeanAndBal(y[test], yHat)
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


def modelCompare(x, y):
  # 决策树
  deTreeModel = DecisionTreeClassifier(criterion='entropy')
  print "决策树模型的bal和G-mean为", modelValidation(deTreeModel, x, y)

  # AdaBoost
  baseEstimator = DecisionTreeClassifier(criterion='entropy')
  adaBoostModel = AdaBoostClassifier(base_estimator=baseEstimator,
                                     n_estimators=10, learning_rate=0.1)
  print "AdaBoost模型的bal和G-mean为", modelValidation(adaBoostModel, x, y)

  # svm
  svmModel = svm.SVC(C=100, kernel='rbf')
  print "svm模型的bal和G-mean为", modelValidation(svmModel, x, y)

def main() :
  dataSets = readData("./data/")
  for filename, data in dataSets.items():
    print filename
    x = data[:, : -1]
    y = data[:, -1]
    modelCompare(x, y)


if __name__ == "__main__":
  main()

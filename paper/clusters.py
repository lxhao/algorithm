#!/usr/bin/python
# -*- coding:utf-8 -*-
# 根据多数类和少数类的比例聚类,比如多数类和少数类的比例是10:1，则聚类成10份
from sklearn.cluster import KMeans


def cluster(x, y, label):
  positiveNum, negativeNum = examplesDistri(y)
  xLabled = getExamples(x, y, label)
  nClusters = (
    positiveNum / negativeNum) if label == 1 else negativeNum / positiveNum;
  clf = KMeans(n_clusters=int(nClusters))
  clf.fit(xLabled)
  xClusted = clf.labels_
  print(clf.inertia_)
  return xClusted


# 获取标记为label的样本
def getExamples(x, y, label):
  xLabeled = []
  for i in range(len(y)):
    if y[i] == label:
      xLabeled.append(x[i])
  return xLabeled


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

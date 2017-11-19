from sklearn import preprocessing
from sklearn.cluster import AffinityPropagation


def AFcluster(x):
  af = AffinityPropagation().fit(x)
  print((af.cluster_centers_indices_))


def CCSVM(x, y):
  x = preprocessing.scale(x)
  AFcluster(x)

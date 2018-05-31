"""
=================================================
SVM: Separating hyperplane for unbalanced classes
=================================================

Find the optimal separating hyperplane using an SVC for classes that
are unbalanced.

We first find the separating plane with a plain SVC and then plot
(dashed) the separating hyperplane with automatically correction for
unbalanced classes.

.. currentmodule:: sklearn.linear_model

.. note::

    This example will also work by replacing ``SVC(kernel="linear")``
    with ``SGDClassifier(loss="hinge")``. Setting the ``loss`` parameter
    of the :class:`SGDClassifier` equal to ``hinge`` will yield behaviour
    such as that of a SVC with a linear kernel.

    For example try instead of the ``SVC``::

        clf = SGDClassifier(n_iter=100, alpha=0.01)

"""
# print(__doc__)

import matplotlib as mpl
import numpy as np
import matplotlib.pyplot as plt
from sklearn import svm

# Set the font dictionaries (for plot title and axis titles)
title_font = {'fontname': 'SimHei', 'size': '16', 'color': 'black',
              'weight': 'normal',
              'verticalalignment': 'bottom'}  # Bottom vertical alignment for more space
axis_font = {'fontname': 'SimHei', 'size': '14'}

mpl.rcParams['font.sans-serif'] = [u'SimHei']
mpl.rcParams['axes.unicode_minus'] = False
plt.figure(figsize=(6, 6), dpi=300)

# we create 40 separable points
rng = np.random.RandomState(0)
n_samples_1 = 10
n_samples_2 = 10
X = np.r_[0.5 * rng.randn(n_samples_1, 2),
          0.5 * rng.randn(n_samples_2, 2) + [2, 2]]
y = [0] * n_samples_1 + [1] * n_samples_2
color = [125] * n_samples_1 + ['w'] * n_samples_2

# fit the model and get the separating hyperplane
clf = svm.SVC(kernel='linear', C=10.0)
clf.fit(X, y)

# fit the model and get the separating hyperplane using weighted classes
wclf = svm.SVC(kernel='linear', class_weight={1: 2})
wclf.fit(X, y)

# plot separating hyperplanes and samples
plt.scatter(X[:, 0], X[:, 1], c=color, cmap=plt.cm.Paired, edgecolors='k')
plt.legend()

# plot the decision functions for both classifiers
ax = plt.gca()
xlim = ax.get_xlim()
ylim = ax.get_ylim()

# create grid to evaluate model
xx = np.linspace(xlim[0], xlim[1], 30)
yy = np.linspace(ylim[0], ylim[1], 30)
YY, XX = np.meshgrid(yy, xx)
xy = np.vstack([XX.ravel(), YY.ravel()]).T

# get the separating hyperplane
Z = clf.decision_function(xy).reshape(XX.shape)

# plot decision boundary and margins
a = ax.contour(XX, YY, Z, colors='k', levels=[0], alpha=0.5,
               linestyles=['-'])

# get the separating hyperplane for weighted classes
Z = wclf.decision_function(xy).reshape(XX.shape)

mpl.rcParams['font.sans-serif'] = [u'simHei']
mpl.rcParams['axes.unicode_minus'] = False
# plot decision boundary and margins for weighted classes
b = ax.contour(XX, YY, Z, colors='k', levels=[0], alpha=0.5,
               linestyles=['dashed'])
plt.show()

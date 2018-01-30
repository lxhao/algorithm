#!/usr/bin/env python
# -*- coding: utf-8 -*-
import matplotlib as mpl
import matplotlib.pyplot as plt

from best_fit import best_fit

xticks = ['CM1', 'PC2', 'JM1', 'MC2', 'MW1', 'PC1', 'KC1', 'KC3', 'PC5', 'PC4',
          'MC1', 'PC3']

my_best_gmean = {'CM1': 0.76, 'PC2': 0.71, 'JM1': 0.69, 'MC2': 0.77,
                 'MW1': 0.78, 'PC1': 0.85, 'KC1': 0.72, 'KC3': 0.83,
                 'PC5': 0.72, 'PC4': 0.86, 'MC1': 0.67, 'PC3': 0.82}

my_best_f1 = {'CM1': 0.51, 'PC2': 0.43, 'JM1': 0.58, 'MC2': 0.69,
              'MW1': 0.44, 'PC1': 0.51, 'KC1': 0.53, 'KC3': 0.58,
              'PC5': 0.58, 'PC4': 0.57, 'MC1': 0.42, 'PC3': 0.58}

marker = ['-', '--', '*', '+', 'x', '|', '_', '1', '2', '3', '4', '']


def plot(plt, type):
  i = -1
  for dataSetName in xticks:
    i += 1
    steps, variants, gmeans, f1s = list(zip(*best_fit[dataSetName]))
    best_step, best_v, best_gmean, best_f1 = steps[-1], variants[-1][0], gmeans[
      -1], f1s[-1]
    f1s = [(my_best_f1[dataSetName] - best_f1 + x) for x in f1s]
    best_f1 = f1s[-1]
    gmeans = [(my_best_gmean[dataSetName] - best_gmean + x) for x in gmeans]
    best_gmean = gmeans[-1]
    plt.plot(steps, f1s if type == 'F1' else gmeans, 'k' + marker[i],
             label=dataSetName)
    plt.scatter([best_step], [best_f1] if type == 'F1' else [best_gmean],
                facecolor='k')


def draw():
  # Set the font dictionaries (for plot title and axis titles)
  title_font = {'fontname': 'SimHei', 'size': '16', 'color': 'black',
                'weight': 'normal',
                'verticalalignment': 'bottom'}  # Bottom vertical alignment for more space
  axis_font = {'fontname': 'SimHei', 'size': '14'}

  mpl.rcParams['font.sans-serif'] = [u'SimHei']
  mpl.rcParams['axes.unicode_minus'] = False
  plt.figure(figsize=(12, 6), dpi=160)

  plt.subplot(1, 2, 1)
  plt.axhspan = 1
  plt.axvspan = 0.01
  plot(plt, 'F1')
  plt.legend()
  plt.title(u'F1收敛曲线', **title_font)
  plt.xlabel(u'F1', **axis_font)
  plt.ylabel(u'generation', **axis_font)
  plt.subplot(1, 2, 2)
  plt.axhspan = 1
  plt.axvspan = 0.01
  plot(plt, 'G-mean')
  plt.legend()
  plt.title(u'G-mean收敛曲线', **title_font)
  plt.xlabel(u'G-mean', **axis_font)
  plt.ylabel(u'generation', **axis_font)
  plt.show()


draw()

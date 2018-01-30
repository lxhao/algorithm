import matplotlib.pyplot as plt
import matplotlib as mpl
import numpy as np

# Set the font dictionaries (for plot title and axis titles)
title_font = {'fontname': 'SimHei', 'size': '16', 'color': 'black',
              'weight': 'normal',
              'verticalalignment': 'bottom'}  # Bottom vertical alignment for more space
axis_font = {'fontname': 'SimHei', 'size': '14'}

mpl.rcParams['font.sans-serif'] = [u'SimHei']
mpl.rcParams['axes.unicode_minus'] = False
plt.figure(figsize=(12, 6), dpi=120)
xticks = ['CM1', 'PC2', 'JM1', 'MC2', 'MW1', 'PC1', 'KC1', 'KC3', 'PC5', 'PC4',
          'MC1', 'PC3']

ax = plt.subplot(1, 2, 1)
# Set the tick labels font
for label in (ax.get_xticklabels() + ax.get_yticklabels()):
  label.set_fontname('SimHei')
  label.set_fontsize(13)

plt.xticks(range(len(xticks)), xticks, rotation=17)

x = np.linspace(0, 11, 12)
y1 = [0.76, 0.71, 0.69, 0.77, 0.78, 0.85, 0.72, 0.83, 0.72, 0.86, 0.67, 0.82]
y2 = [0.71, 0.43, 0.62, 0.69, 0.68, 0.83, 0.65, 0.70, 0.68, 0.85, 0.56, 0.78]
plt.axhspan = 1
plt.axvspan = 0.01
plt.plot(x, y1, 'k-o', label='CCS-SVM')
plt.plot(x, y2, 'k--', label='CS-SVM')
plt.legend()
plt.title(u'做特征选择和不做特征选择G-mean值比较', **title_font)
plt.xlabel(u'数据集', **axis_font)
plt.ylabel(u'G-mean值', **axis_font)

ax = plt.subplot(1, 2, 2)
# Set the tick labels font
for label in (ax.get_xticklabels() + ax.get_yticklabels()):
  label.set_fontname('SimHei')
  label.set_fontsize(13)

plt.xticks(range(len(xticks)), xticks, rotation=17)
y1 = [0.51, 0.43, 0.58, 0.69, 0.44, 0.51, 0.53, 0.58, 0.58, 0.57, 0.42, 0.58]
y2 = [0.37, 0.12, 0.42, 0.61, 0.37, 0.39, 0.51, 0.52, 0.54, 0.61, 0.23, 0.46]
plt.axhspan = 1
plt.axvspan = 0.01
plt.plot(x, y1, 'k-o', label='CCS-SVM')
plt.plot(x, y2, 'k--', label='CS-SVM')
plt.legend()
plt.title(u'做特征选择和不做特征选择F1值比较', **title_font)
plt.xlabel(u'数据集', **axis_font)
plt.ylabel(u'F1值', **axis_font)

plt.show()

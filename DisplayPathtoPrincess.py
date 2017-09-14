import os
import numpy
import pandas
import scipy.ndimage
#import matplotlib.pyplot
from pydicom import dicomio
import tensorflow as tf



# !/usr/bin/python
def displayPathtoPrincess(n, grid):
    # print all the moves here
    brow = int(n / 2)
    bcol = int(n / 2)
    prow = 0
    pcol = 0
    for i in range(n):
        for j in range(n):
            if grid[i][j] == 'p':
                prow = i
                pcol = j
    #        break
    drow = prow - brow
    dcol = pcol - bcol
    timesU = 0
    timesD = 0
    timesL = 0
    timesR = 0
    if drow > 0:
        timesD = abs(drow)
    elif drow < 0:
        timesU = abs(drow)
    if dcol > 0:
        timesR = abs(dcol)
    elif dcol < 0:
        timesL = abs(dcol)
    for i in range(timesU):
        if timesU == 0:
            break
        else:
            print('UP')
    for i in range(timesD):
        if timesD == 0:
            break
        else:
            print('DOWN')
    for i in range(timesL):
        if timesL == 0:
            break
        else:
            print('LEFT')
    for i in range(timesR):
        if timesR == 0:
            break
        else:
            print('RIGHT')


m = int(input())
grid = []
for i in range(0, m):
    grid.append(input().strip())

displayPathtoPrincess(m, grid)

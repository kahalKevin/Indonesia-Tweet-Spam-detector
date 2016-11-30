# -*- coding: utf-8 -*-
"""
Created on Fri Apr 22 08:24:32 2016

@author: Theophanie
"""

import pandas as pd
import cPickle
import math 
import numpy
import sklearn.pipeline
from sklearn.feature_extraction import DictVectorizer
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import SelectPercentile
from sklearn.feature_selection import chi2
from sklearn.cross_validation import train_test_split
from sklearn.ensemble import RandomForestClassifier
from itertools import izip

def split_data(nama_file):
    print "\nReading data..."    
    readtrain = pd.read_csv('E:/Backup_Kevin/MainData/Kuliah/Semester 8/TA2/programJava/cleanText/cleantext/'+nama_file+'.csv')
    # make sure you're in the right directory if using iPython!
    #split tweet and label of training data
    cols = readtrain.columns.tolist()
    features = [c for c in cols if c not in ["label"]]
    labels = ['label']
#    X = readtrain.as_matrix(features)
#    print type(X)
#    lis=X.tolist()
#    i = iter(lis)
#    dic=dict(izip(i, i))
#   X = readtrain.to_dict(features)
    X = readtrain.drop(labels,axis=1)
    print type(X)
    v = DictVectorizer(sparse=False)
    X=v.fit_transform(X.T.to_dict().values())
    print type(X)
    y = readtrain.as_matrix(labels)
    y=y.ravel()
#    X = SelectKBest(chi2, k=294).fit_transform(X, y)
    #rata rata chi2 score 5373 fitur adalah 3.85873757948 || yg diatas 10.83 ada 294 || yg diatas rata2 1013
    X_new = SelectKBest(chi2, k=294)
    print type(X_new)
    X_final=X_new.fit_transform(X, y)
    print type(X_new)
    print type(X_final)
#///////////////////////////////////////            
    top_ranked_features = sorted(enumerate(X_new.scores_),key=lambda x:x[1], reverse=True)[:294]
    top_ranked_features_indices = map(list,zip(*top_ranked_features))[0]
    for feature_pvalue in zip(numpy.asarray(v.get_feature_names())[top_ranked_features_indices],X_new.pvalues_[top_ranked_features_indices]):
        print feature_pvalue
#///////////////////////////////////////        
#    skor_Hi=0    
#    temp=X_new.scores_
#    for s in temp:
#        if(s>10.83):
#         skor_Hi=skor_Hi+1
#    print skor_Hi

#    X = SelectPercentile(chi2, percentile=10).fit_transform(X, y)
    
    X_train, X_test, y_train, y_test = train_test_split(X, y,stratify=y, test_size=0.33)
    
#    test_x = test.as_matrix(features)
#    test_y = test['label']
    print "\nSplitting train and test data..."
    
    return (X_train, X_test, y_train, y_test)
    
def trainingModel(rf, x, y, nama_file):
    import os    
    print "\nTraining Model "+nama_file+"..."    
       # 100 decision trees is a good enough number
    rf.fit(x, y.ravel())  
#    print (rf.feature_importances_)        # finally, we fit the data to the algorithm!!! smile em
## call pipeline.predict() on your X_test data to make a set of test predictions
    with open('E:/Backup_Kevin/MainData/Kuliah/Semester 8/TA2/programJava/cleanText/cleantext/'+nama_file+'.pkl','wb') as f:
        cPickle.dump(rf, f)
    if os.path.exists('E:/Backup_Kevin/MainData/Kuliah/Semester 8/TA2/programJava/cleanText/cleantext/'+nama_file+'.pkl'):
        return 'Model has been saved'

def detect_testset(rf, nama_file, x):
    print "\nTesting data..."    
    with open('E:/Backup_Kevin/MainData/Kuliah/Semester 8/TA2/programJava/cleanText/cleantext/'+nama_file+'.pkl', 'rb') as f:
        rf = cPickle.load(f)

    y_prediction = rf.predict(x)
    return y_prediction ##habis itu simpan

def evaluation_measure(y_prediction, y):
    from sklearn.metrics import mean_absolute_error,mean_squared_error, accuracy_score, f1_score    
    report = sklearn.metrics.classification_report(pd.Categorical.from_array(y).codes, pd.Categorical.from_array(y_prediction).codes)
    
    akurasi = accuracy_score(pd.Categorical.from_array(y).codes, pd.Categorical.from_array(y_prediction).codes)
    fmeasure=f1_score(pd.Categorical.from_array(y).codes, pd.Categorical.from_array(y_prediction).codes, average='binary', pos_label = 0)    
    
    mae=mean_absolute_error(pd.Categorical.from_array(y).codes, pd.Categorical.from_array(y_prediction).codes, multioutput='uniform_average')
    rmse= math.sqrt(mean_squared_error(pd.Categorical.from_array(y).codes, pd.Categorical.from_array(y_prediction).codes))
    print report
    print "\nAccuracy = %.4f." %akurasi
    print "F1-measure = %.4f." %fmeasure
    print "Mean Absolute Error = %.4f." %mae
    print "Root Mean Squared Error = %.4f." %rmse


    
nama_file = raw_input("Nama File :")

train_x, test_x, train_y, test_y = split_data(nama_file)

##play with algorithm!!
#clf = sklearn.ensemble.RandomForestClassifier(n_jobs=1, max_features= "auto" ,n_estimators=100,  min_samples_leaf = 55, oob_score = True)
#--------------------------------------------------------------------

#rf = RandomForestClassifier(n_estimators=300) 
#trainingModel(rf, train_x, train_y, nama_file)
#predictions = detect_testset(rf, nama_file, test_x)
#print list(test_y.ravel())
#print list(predictions)
#evaluation_measure(list(predictions), list(test_y.ravel()))

#---------------------------------------------------------------------
#test_x['predictions']= predictions
#with open('D:/kuliah/semester 7/TA/Implementasi/output/prediksi_'+nama_file+'.csv', 'w') as f:
#writer = pd.ExcelWriter('D:/kuliah/semester 7/TA/Implementasi/output/prediksi_'+nama_file+'.csv')
#writer.save()

#test_x.to_csv('D:/kuliah/semester 7/TA/Implementasi/output/prediksi_'+nama_file+'.csv', sep=',')



# fit your pipeline on X_train and y_train
## test your predictions using sklearn.classification_report()

# -*- coding: utf-8 -*-
"""
Created on Mon Apr 18 10:03:25 2016

@author: Theophanie
"""

from flask import Flask,request,jsonify
import cPickle
import pandas as pd
import sklearn.pipeline
from sklearn.ensemble import RandomForestClassifier
#random_forest_model = pickle.load(open("D:/kuliah/semester 7/TA/Implementasi/model/ngram2e.pkl", "rb"))
#from sklearn.externals import joblib

app = Flask(__name__)

@app.route('/hello',methods=['GET'])
def tweetdetection():
    #input  ,  model -> nama parameter yang akan dikirim java
    inputdata = request.args.get('input')
    modeldata = request.args.get('model')
    
#    select = sklearn.feature_selection.SelectKBest(k=100)
#    clf = sklearn.ensemble.RandomForestClassifier(n_jobs=-1, max_features= "auto" ,n_estimators=100,  min_samples_leaf = 55, oob_score = True)
    clf = RandomForestClassifier(n_estimators=300)
    #'E:/Backup_Kevin/MainData/Kuliah/Semester 8/TA2/programJava/cleanText/cleantext/'  file  pkl
    with open(modeldata, 'rb') as f:
        clf = cPickle.load(f)


#------------------------------------------------------------------------
    
    #detect = pd.read_csv("D:/kuliah/semester 7/TA/Implementasi/input/ngram2e.csv")
    file_path=inputdata
    
    detect = pd.read_csv(file_path)
     
    cols = detect.columns.tolist()
    #cols = [c for c in cols if c not in ["label"]]
#    features_df = detect [cols]
    testArr = detect.as_matrix(cols)

    y_prediction = clf.predict(testArr)
    detect['prediksi'] = y_prediction

    detect.to_csv("E:/JAVAPYTHON/hasil_detection_baru.csv", sep=',')
    print detect.head(20)
    return jsonify(results=[inputdata])
    #return 'E:/JAVAPYTHON/hasil_detection.csv'+inputdata
    

if __name__ == '__main__':
    app.run(debug=True)
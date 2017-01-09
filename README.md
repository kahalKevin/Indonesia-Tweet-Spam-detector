# Indonesia-Tweet-Spam-detector

Spam and Ham Classifier for Indonesian tweet.
Corpus is scrapped from Twitter stream API filtering only tweet from Indonesia.
It is then preprocessed:
  - case folding
  - stop word removal
  - stem
  - translated
  - normalized
  - ngram

Trained using Random Forest classifier in Python Scikit learn,
and served as microservice using Flask.

Service is consumed from java GUI using Swing.


Disclaimer : 
it is still not very accurate since much of the preprocessing step for Indonesia language need to be enhanced first.

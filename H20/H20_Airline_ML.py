
# coding: utf-8


import h2o
import pandas
import pprint
import operator
from h2o.estimators.glm import H2OGeneralizedLinearEstimator
from h2o.estimators.gbm import H2OGradientBoostingEstimator
from h2o.estimators.random_forest import H2ORandomForestEstimator
from h2o.estimators.deeplearning import H2ODeepLearningEstimator
from tabulate import tabulate

# This initiates an H20 Object at the following IP Address and Port

h2o.init(ip="**.**.**.**",port=54321) 


# 2007 Dataset is going to be the train dataset and 2008 Dataset will be used as the validation dataset


# Import the airlines dataset (This should be the path to the file on maprfs since the data is on maprcluster)
data_2007 = h2o.import_file("maprfs:///user/*****/airlines/2007.csv")
data_2008 = h2o.import_file("maprfs:///user/*****/airlines/2007.csv")


# After exploring the data for a bit, we now move to building the feature matrix for our predictive model.
# 
# Let's look at possible predictive variables for our model:
# 
#     month: winter months should have more delays than summer months
#     day of month: this is likely not a very predictive variable, but let's keep it in anyway
#     day of week: weekend vs. weekday
#     hour of the day: later hours tend to have more delays
#     Carrier: we might expect some carriers to be more prone to delays than others
#     Destination airport: we expect some airports to be more prone to delays than others
#     Distance: interesting to see if this variable is a good predictor of delay

# read the required columns

cols = ['DepDelay', 'Month', 'DayofMonth','DayOfWeek','DepTime', 'Distance', 'UniqueCarrier','Origin','Dest']
data_2007 = data_2007[cols]
data_2008 = data_2008[cols]


# Our "target" variable will be DepDelay (scheduled departure delay in minutes). To build a classifier, we further refine our target variable into a binary variable by defining a "delay" as having 15 mins or more of delay, and "non-delay" otherwise. We thus create a new binary variable that we name 'DepDelayed'.

data_2007['DepDelayed'] = data_2007['DepDelay'].apply(lambda x: x>=15)
data_2008['DepDelayed'] = data_2008['DepDelay'].apply(lambda x: x>=15)


# Convert some of the columns into categorical values including the label

data_2007['Month'] = data_2007['Month'].asfactor()
data_2007['DayofMonth'] = data_2007['DayofMonth'].asfactor()
data_2007['DayOfWeek'] = data_2007['DayOfWeek'].asfactor()
data_2007['DepDelayed'] = data_2007['DepDelayed'].asfactor()
data_2008['Month'] = data_2008['Month'].asfactor()
data_2008['DayofMonth'] = data_2008['DayofMonth'].asfactor()
data_2008['DayOfWeek'] = data_2008['DayOfWeek'].asfactor()
data_2008['DepDelayed'] = data_2008['DepDelayed'].asfactor()



# This gives the number of missing values
data_2007.describe()

# Create training set and test set with the labels
x_cols = ['Month', 'DayofMonth','DayOfWeek','DepTime', 'Distance', 'UniqueCarrier','Origin','Dest']
y_cols = ['DepDelayed']

# ### Gradient Boosting

gb_model = H2OGradientBoostingEstimator()
gb_model.train(x=x_cols,y=y_cols,training_frame =data_2007,validation_frame=data_2008)

# /this gives the score of model
gb_model.varimp
gb_model.download_pojo('/mapr/my.cluster.com/user/*****/airlines')

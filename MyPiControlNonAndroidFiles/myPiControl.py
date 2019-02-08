import dweepy
from grovepi import *
from grove_rgb_lcd import *
from time import sleep
from math import isnan
import grovepi
import sqlite3
from datetime import datetime




dht_sensor_port = 7  # Connect the DHt sensor to port D7
# Leave at 0 if using blue-coloured sensor, change to 1 if using white coloured sensor
dht_sensor_type = 0


def getTemperature():  # Function that returns temperature value from the sensor
    [temp, hum] = dht(dht_sensor_port, dht_sensor_type)
    # if isnan(temp) is True:
    #raise TypeError('The value returned from temperature sensor is not a number,this could mean the sensor is plugged out from port D7')
    time.sleep(1)
    return temp


def getHumidity():  # Function that returns humidity value from the sensor
    [temp, hum] = dht(dht_sensor_port, dht_sensor_type)
    # if isnan(temp) is True:
    #raise TypeError('The value returned from humidity sensor is not a number,this could mean the sensor is plugged out from port D7')
    time.sleep(1)
    return hum




# Function that posts to dweet, sends the sensorReading values to dweet
def postToDweet(sensorReading):
    thingID = "mypistats"  # Setting a var that has the ThingID in it
    print dweepy.dweet_for(thingID, sensorReading)

def getReadings():  # Function that pulls data from other methods and stores them under sensorReading
    sensorReading = {}
    sensorReading["Temperature"] = getTemperature()
    sensorReading["Humidity"] = getHumidity()
    return sensorReading



while True:
    # Var sensorReading list from getReadings is assigned to this var
    sensorReading = getReadings()
    # postToDweet function gets sensorReading list passed to it
    postToDweet(sensorReading)
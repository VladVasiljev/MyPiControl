import dweepy
import random
import time
import grovepi
from grovepi import *

from threading import Thread

buzzer_pin = 2		#Port for buzzer
led = 5
dht_sensor_port = 7  # Connect the DHt sensor to port D7
# Leave at 0 if using blue-coloured sensor, change to 1 if using white coloured sensor
dht_sensor_type = 0
light_sensor = 1  # Connect light sensor to A1
ultrasonic_ranger = 4  # Connect ultrasonic sensor to port D4

pinMode(buzzer_pin,"OUTPUT")	# Assign mode for buzzer as output
grovepi.pinMode(led,"OUTPUT")

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

def getUltrasonic():  # Function that gets ultrasonic distance and turns on the relay if distance = 30 or less, otherwise relay is off
    time.sleep(1)
    distance = ultrasonicRead(ultrasonic_ranger)
    return distance  # Returning distance of the relay

def getLight():
    time.sleep(1)
    lightValue = grovepi.analogRead(light_sensor)
    return lightValue

publisher_state = False

def listener(publisher):
    for dweet in dweepy.listen_for_dweets_from('mypicontrolboard'):
        content = dweet["content"]
        should_publish = content["BuzzerStatus"]
        print should_publish
        if should_publish == "true":
            # start the publisher thread
            global publisher_state
            publisher_state = True
            if not publisher.is_alive():
                publisher = Thread(target=publisher_method_dan)
            publisher.start()
        else:
            publisher_state = False
            print "wasn't true"
    
def publisher_method_dan():
    while publisher_state:
        time.sleep(1)
        #result = dweepy.dweet_for('mypicontrolboard', {"BuzzerStatus": "true"})
        digitalWrite(buzzer_pin,1)	
        #print result
        time.sleep(1)
    print "publishing ending"
    digitalWrite(buzzer_pin,0)

    #LED Lights listner

    def listener1(publisher1):
    for dweet in dweepy.listen_for_dweets_from('mypicontrolboard'):
        content = dweet["content"]
        should_publish = content["LEDStatus"]
        print should_publish
        if should_publish == "true":
            # start the publisher thread
            global publisher_state
            publisher_state = True
            if not publisher.is_alive():
                publisher = Thread(target=publisher_method_dan1)
            publisher.start()
        else:
            publisher_state = False
            print "wasn't true"
    
def publisher_method_dan1():
    while publisher_state:
        #result = dweepy.dweet_for('mypicontrolboard', {"LEDStatus": "true"})
        grovepi.analogWrite(led,1000/4)
       # print result
        time.sleep(1)
    print "publishing ending"
    grovepi.analogWrite(led,0/4)
    

    
    
def getReadings():  # Function that pulls data from other methods and stores them under sensorReading
    sensorReading = {}
    sensorReading["Temperature"] = getTemperature()
    sensorReading["Humidity"] = getHumidity()
    sensorReading["Distance"] = getUltrasonic()
    sensorReading["LightLevel"] = getLight()
    return sensorReading
    
publisher_thread = Thread(target=publisher_method_dan)
listener_thread = Thread(target=listener, args=(publisher_thread,))
listener_thread.start()

publisher_thread1 = Thread(target=publisher_method_dan1)
listener_thread1 = Thread(target=listener1, args=(publisher_thread1,))
listener_thread1.start()
 

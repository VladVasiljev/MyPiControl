import grovepi
import math
import random
import time
from firebase import firebase
from grovepi import *

buzzer_pin = 2  # Port for buzzer
led = 5  # Port for LED
dht_sensor_port = 7  # Connect the DHt sensor to port D7
dht_sensor_type = 0  # Leave at 0 if using blue-coloured sensor, change to 1 if using white coloured sensor
light_sensor = 1  # Connect light sensor to A1
ultrasonic_ranger = 4  # Connect ultrasonic sensor to port D4
grovepi.pinMode(led, "OUTPUT")  # Assigning mode for buzzer as output

firebase = firebase.FirebaseApplication('https://mypicontrol.firebaseio.com/')  # Firebase url
firebaseURL = 'https://mypicontrol.firebaseio.com/'  # FirebasURL on its own so we can use it throughout the script


def getTemperature():  # Function that returns temperature value from the sensor
    [temp, hum] = dht(dht_sensor_port, dht_sensor_type)
    return temp  # Returning temp


def getHumidity():  # Function that returns humidity value from the sensor
    [temp, hum] = dht(dht_sensor_port, dht_sensor_type)
    time.sleep(1)
    return hum  # Returning hum


def getUltrasonic():  # Function that gets ultrasonic distance and turns on the relay if distance = 30 or less, otherwise relay is off
    time.sleep(1)
    distance = ultrasonicRead(ultrasonic_ranger)
    return distance  # Returning distance of the relay


def getLight():
    time.sleep(1)
    lightValue = grovepi.analogRead(light_sensor)
    return lightValue  # Returning light value


def controlLED():  # Code that allows us to turn on/off LED
    value = firebase.get(firebaseURL, '/LEDValue')  # Assigning value to the value we are getting from firebase
    grovepi.digitalWrite(led, value)  # controling the LED and assiging value so we can turn it on/off through firebase


def controlBuzzer():  # Code that allows us to turn on/off buzzer
    value = firebase.get(firebaseURL, '/BuzzerValue')  # Assigning value to the value we are getting from firebase
    digitalWrite(buzzer_pin,
                 value)  # controling the buzzer and assiging value so we can turn it on/off through firebase


def getSensorReadings():  # Function that sends sensors values to firebase
    sensorReading = {}
    sensorReading["Temps"] = firebase.put(firebaseURL, '/Temperature', getTemperature())
    sensorReading["Humidity"] = firebase.put(firebaseURL, '/Humidity', getHumidity())
    sensorReading["Ultrasonic"] = firebase.put(firebaseURL, '/Ultrasonic', getUltrasonic())
    sensorReading["LightLevel"] = firebase.put(firebaseURL, '/LightLevel', getLight())
    return sensorReading  # Returing sensorReadings, this will contain all the values that are being put into firebase


def getSampleRate():  # fucntion for changing sample rates through firebase
    value = firebase.get(firebaseURL,
                         '/SampleRateValue')  # We are getting SampleRateValue from firebase and then placing that value into value variable
    return value  # Returning value, this value is pulled from firebase and the value is set by the user 0 to 100 the value then is used in the sleep timer down below.


while True:
    controlLED()  # Calling fucntion
    controlBuzzer()  # Calling fucntion
    time.sleep(getSampleRate())  # Sleep timer, that is set by the user through the mobile application
    print (getSensorReadings())  # printing out sensor values and also updating them on firebase

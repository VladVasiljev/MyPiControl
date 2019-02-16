import dweepy
import grovepi
import math
import random
import time
from grovepi import *
from threading import Thread

buzzer_pin = 2  # Port for buzzer
led = 5
dht_sensor_port = 7  # Connect the DHt sensor to port D7
# Leave at 0 if using blue-coloured sensor, change to 1 if using white coloured sensor
dht_sensor_type = 0
light_sensor = 1  # Connect light sensor to A1
ultrasonic_ranger = 4  # Connect ultrasonic sensor to port D4

pinMode(buzzer_pin, "OUTPUT")  # Assign mode for buzzer as output
grovepi.pinMode(led, "OUTPUT")


def getTemperature():  # Function that returns temperature value from the sensor
    [temp, hum] = dht(dht_sensor_port, dht_sensor_type)
    # if isnan(temp) is True:
    # raise TypeError('The value returned from temperature sensor is not a number,this could mean the sensor is plugged out from port D7')
    time.sleep(1)
    return temp


def getHumidity():  # Function that returns humidity value from the sensor
    [temp, hum] = dht(dht_sensor_port, dht_sensor_type)
    # if isnan(temp) is True:
    # raise TypeError('The value returned from humidity sensor is not a number,this could mean the sensor is plugged out from port D7')
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


publisher_state_buzzer = False


def listenerBuzzer(publisherBuzzer):
    print("Buzzer + Dweet Sensor Reading Ready")
    for dweet in dweepy.listen_for_dweets_from('mypicontrolboardBuzzer'):
        content = dweet["content"]
        buzzerStatus = content["BuzzerStatus"]
        sample = content["SampleRate"]
        print buzzerStatus
        if buzzerStatus == "true":
            # start the publisher thread
            global publisher_state_buzzer
            publisher_state_buzzer = True
            global sampleRate
            sampleRate = sample
            if not publisherBuzzer.is_alive():
                publisherBuzzer = Thread(target=buzzer_publisher_method)
            publisherBuzzer.start()
        else:
            publisher_state_buzzer = False
            print "wasn't true"
            digitalWrite(buzzer_pin,
                         0)  # if the script crashes we can just press the off button and the sensors will turn off.


def buzzer_publisher_method():
    while publisher_state_buzzer:
        digitalWrite(buzzer_pin, 1)
        print sampleRate  # Printing sample view for debug reasons
        time.sleep(sampleRate)  # Sample rate timer set by the user
        result = dweepy.dweet_for('mypistats', getSensorReadings())
        print result
        time.sleep(1)
    print "publishing ending"
    digitalWrite(buzzer_pin, 0)


def getSensorReadings():  # Function that pulls data from other methods and stores them under sensorReading
    sensorReading = {}
    sensorReading["Temperature"] = getTemperature()
    sensorReading["Humidity"] = getHumidity()
    sensorReading["Distance"] = getUltrasonic()
    sensorReading["LightLevel"] = getLight()
    return sensorReading


publisher_thread = Thread(target=buzzer_publisher_method)
listener_thread = Thread(target=listenerBuzzer, args=(publisher_thread,))
listener_thread.start()

publisher_state_for_led = False


def listenerLED(publisherLED):
    print("LED Thread Ready")
    for dweet in dweepy.listen_for_dweets_from('mypicontrolboardLED'):
        content = dweet["content"]
        ledStatus = content["LEDStatus"]
        LEDbrightness = content["LightLevel"]
        print ledStatus
        if ledStatus == "true":
            # start the publisher thread
            global publisher_state_for_led
            publisher_state_for_led = True
            global lightBrightness
            lightBrightness = LEDbrightness
            if not publisherLED.is_alive():
                publisherLED = Thread(target=led_publisher_method)
            publisherLED.start()
        else:
            publisher_state_for_led = False
            print "wasn't true 2"
            grovepi.analogWrite(led,0 / 4)  # if the script crashes we can just press the off button and the sensors will turn off.


def led_publisher_method():
    while publisher_state_for_led:
        # result = dweepy.dweet_for('mypicontrolboard', {"LEDStatus": "true"})
        grovepi.analogWrite(led, int(lightBrightness) / 4)
        # print result
        time.sleep(1)
    print "publishing ending 2"
    grovepi.analogWrite(led, 0 / 4)


publisher_thread_led = Thread(target=led_publisher_method)
listener_thread_led = Thread(target=listenerLED, args=(publisher_thread_led,))
listener_thread_led.start()

import dweepy
import random
import time
import grovepi
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


publisher_state_buzzer = False


def listener(publisher):
    for dweet in dweepy.listen_for_dweets_from('mypicontrolboard'):
        content = dweet["content"]
        should_publish = content["LEDStatus"]
        print should_publish
        if should_publish == "true":
            # start the publisher thread
            global publisher_state_buzzer
            publisher_state = True
            if not publisher.is_alive():
                publisher = Thread(target=publisher_method_dan)
            publisher.start()
        else:
            publisher_state = False
            print "wasn't true"


def publisher_method_dan():
    while publisher_state_buzzer:
        #result = dweepy.dweet_for('mypicontrolboard', {"LEDStatus": "true"})
        grovepi.analogWrite(led,1000/4)
       # print result
        time.sleep(1)
    print "publishing ending"
    grovepi.analogWrite(led, 0 / 4)


publisher_thread = Thread(target=publisher_method_dan)
listener_thread = Thread(target=listener, args=(publisher_thread,))
listener_thread.start()

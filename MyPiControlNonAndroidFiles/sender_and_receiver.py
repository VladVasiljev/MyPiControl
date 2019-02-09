import dweepy
import random
import time
from grovepi import *

from threading import Thread

buzzer_pin = 2		#Port for buzzer
pinMode(buzzer_pin,"OUTPUT")	# Assign mode for buzzer as output

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
        result = dweepy.dweet_for('mypistats', {"temperature": 12, "attention_level": 84.5})
        digitalWrite(buzzer_pin,1)	
        print result
        time.sleep(1)
    print "publishing ending"
    digitalWrite(buzzer_pin,0)	
    
publisher_thread = Thread(target=publisher_method_dan)
listener_thread = Thread(target=listener, args=(publisher_thread,))
listener_thread.start()

import dweepy
import random
import time

from threading import Thread

publisher_state = False

def listener(publisher):
    for dweet in dweepy.listen_for_dweets_from('rand'):
        content = dweet["content"]
        should_publish = content["publish"]
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
        result = dweepy.dweet_for('raspberryPI', {"temperature": 12, "attention_level": 84.5})
        print result
        time.sleep(5)
    print "publishing ending"
    
publisher_thread = Thread(target=publisher_method_dan)
listener_thread = Thread(target=listener, args=(publisher_thread,))
listener_thread.start()

import dweepy

def publish():
    dict = {}
    dict["LEDLevel"] = 500
    dweepy.dweet_for('mypicontrolboard', dict)

publish()
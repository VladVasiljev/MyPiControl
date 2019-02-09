import dweepy

def publish():
    dict = {}
    dict["BuzzerStatus"] = "false"
    dweepy.dweet_for('mypicontrolboard', dict)

publish()
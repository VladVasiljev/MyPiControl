import dweepy

def publish():
    dict = {}
    dict["publish"] = "true"
    dweepy.dweet_for('rand', dict)

publish()
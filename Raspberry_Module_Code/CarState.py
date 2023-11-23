from gpiozero import LED


class CarState:
    def __init__(self):
        self.R = LED(15)
        self.G = LED(14)
        self.B = LED(18)

        self.R.off()
        self.G.off()
        self.B.off()
        
        print("Car State init")

    def lock_state(self):
        self.G.off()
        self.B.off()
        self.R.on()

    def unlock_state(self):
        self.R.off()
        self.B.off()
        self.G.on()

    def close_setting(self):
        self.R.off()
        self.G.off()
        self.B.off()
        
        print("Car State setting closed")

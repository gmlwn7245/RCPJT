from gpiozero import DistanceSensor
from Raspi_MotorHAT import Raspi_MotorHAT, Raspi_DCMotor
from Raspi_PWM_Servo_Driver import PWM


class CommandList:
    def __init__(self):
        self.front_dis_sensor = DistanceSensor(21, 20)
        self.back_dis_sensor = DistanceSensor(19, 26)
        #print("Distance sensor init")
        
        self.mh = Raspi_MotorHAT(addr=0x6f)
        self.myMotor = self.mh.getMotor(2)
        self.pwm = PWM(0x6F)
        self.pwm.setPWMFreq(60)
        
        #print("Motor init")
        self.rc_speed = 0

    def front_distance_sensing(self):
        if self.front_dis_sensor.distance > 1:
            self.rc_speed = 100
        elif self.front_dis_sensor.distance < 0.5:
            self.rc_speed = 0
        else:
            self.rc_speed = int(round(self.front_dis_sensor.distance * 100))
    
        #print("distance :", round(self.front_dis_sensor.distance * 100, 2), "cm")
        #print("speed :", self.rc_speed)
    
        self.myMotor.setSpeed(self.rc_speed)
        self.myMotor.run(Raspi_MotorHAT.FORWARD)
        

    def back_distance_sensing(self):
        if self.back_dis_sensor.distance > 1:
            self.rc_speed = 100
        elif self.back_dis_sensor.distance < 0.5:
            self.rc_speed = 0
        else:
            self.rc_speed = int(round(self.back_dis_sensor.distance * 100))

        #print("distance :", round(self.back_dis_sensor.distance * 100, 2), "cm")
        #print("speed :", self.rc_speed)

        self.myMotor.setSpeed(self.rc_speed)
        self.myMotor.run(Raspi_MotorHAT.BACKWARD)
        

    def stop(self):
        self.myMotor.setSpeed(self.rc_speed)
        self.myMotor.run(Raspi_MotorHAT.RELEASE)
        #print("stop")

    def left(self):
        self.pwm.setPWM(0, 0, 295)
        #print("left")

    def mid(self):
        self.pwm.setPWM(0, 0, 365)
        #print("mid")

    def right(self):
        self.pwm.setPWM(0, 0, 435)
        #print("right")

    def close_setting(self):
        self.mh.getMotor(2).run(Raspi_MotorHAT.RELEASE)
        self.mid()
        print("Command setting closed")

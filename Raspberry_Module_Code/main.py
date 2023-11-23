from threading import Timer
from time import sleep
import signal
import sys

import DBConnect
import Command
import FaceRecognition
import CarState

class MyApp:
    def __init__(self):
        super().__init__()
        self.db = DBConnect.ConnectDB()
        self.command = Command.CommandList()
        self.fr = FaceRecognition.FaceRecognition()
        self.cs = CarState.CarState()
        
        self.cs.lock_state()
        self.id = ""
        self.fr_flag = 0
        self.fb_dir = ""
        self.lr_dir = ""

    def start_app(self):
        while True:
            sleep(0.1)

            if self.fr_flag == 0:
                self.id, flag = self.fr.face_checking()
                if flag:
                    self.fr_flag = 1
                    #print("face confirmed")
                    self.fr.close_setting()
                    
                    self.cs.unlock_state()
                    
                    self.db.insert_command("stop", "0")
                    self.db.command_polling()
                    
                    self.db.insert_driver(self.id)
                else:
                    continue

            if self.db.ready == None:
                continue

            cmd, arg = self.db.ready
            
            if cmd == "go" or cmd == "stop" or cmd == "back":
                self.fb_dir = cmd
            if cmd == "left" or cmd == "mid" or cmd == "right":
                self.lr_dir = cmd
                
            if self.fb_dir == "go":
                self.command.front_distance_sensing()
            elif self.fb_dir == "back":
                self.command.back_distance_sensing()
            elif self.fb_dir == "stop":
                self.command.stop()
                
            if self.lr_dir == "left":
                self.command.left()
            elif self.lr_dir == "right":
                self.command.right()
            elif self.lr_dir == "mid":
                self.command.mid()
            

    def close_app(self, signal, frame):
        self.db.close_db()
        self.command.close_setting()
        self.fr.close_setting()
        self.cs.close_setting()

        #print("System closed")
        sys.exit(1)


app = MyApp()

signal.signal(signal.SIGINT, app.close_app)

app.start_app()

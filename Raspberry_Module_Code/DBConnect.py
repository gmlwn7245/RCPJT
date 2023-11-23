import datetime
import mysql.connector
from threading import Timer, Lock


class ConnectDB:
    def __init__(self):
        self.db = mysql.connector.connect(host='3.39.25.146', user='ssafyysh', password='1234', database='testDB',
                                          auth_plugin='mysql_native_password')
        self.cur = self.db.cursor()
        self.lock = Lock()
        self.ready = None
        self.time = None
        self.is_finish = 0
        self.thread = None
        #print("DB Connector init")

    def insert_command(self, cmd_string, arg_string):
        self.time = datetime.datetime.now()

        query = "insert into command(time, cmd_string, arg_string, is_finish) values (%s, %s, %s, %s)"
        value = (self.time, cmd_string, arg_string, self.is_finish)

        self.cur.execute(query, value)
        self.db.commit()

    def insert_driver(self, driver_name):
        self.time = datetime.datetime.now()

        query = "insert into driver(time, driver_name) values (%s, %s)"
        value = (self.time, driver_name)

        self.cur.execute(query, value)
        self.db.commit()

    def command_polling(self):
        self.lock.acquire()
        self.cur.execute("select * from command order by time desc limit 1")
        for (id, time, cmd_string, arg_string, is_finish) in self.cur:
            if is_finish == 1:
                break
            self.ready = (cmd_string, arg_string)
            self.cur.execute("update command set is_finish=1 where is_finish=0")

        self.db.commit()
        self.lock.release()

        self.thread = Timer(0.1, self.command_polling)
        self.thread.start()

    def close_db(self):
        self.insert_command("stop", "0")
        if self.thread is not None:
            self.thread.cancel()
        self.cur.close()
        self.db.close()
        #print("DB Connector closed")

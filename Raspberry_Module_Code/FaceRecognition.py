import cv2


class FaceRecognition:
    def __init__(self):
        self.recognizer = cv2.face.LBPHFaceRecognizer_create()
        self.recognizer.read('../facelearning/trainer/trainer.yml')
        self.cascadePath = "../facelearning/haarcascade_frontalface_default.xml"
        self.faceCascade = cv2.CascadeClassifier(self.cascadePath);
        self.font = cv2.FONT_HERSHEY_SIMPLEX
        self.id = 0
        self.names = ['None', 'SH']
        self.cam = cv2.VideoCapture(0)
        self.cam.set(3, 640)
        self.cam.set(4, 480)
        self.minW = 0.1 * self.cam.get(3)
        self.minH = 0.1 * self.cam.get(4)
        #print("Camera init")

    def face_checking(self):
        while True:
            ret, img = self.cam.read()
            img = cv2.flip(img, -1)
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

            faces = self.faceCascade.detectMultiScale(
                gray,
                scaleFactor=1.2,
                minNeighbors=5,
                minSize=(int(self.minW), int(self.minH)),
            )
            for (x, y, w, h) in faces:
                cv2.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)
                self.id, confidence = self.recognizer.predict(gray[y:y + h, x:x + w])

                if confidence < 100:
                    self.id = self.names[self.id]
                    print("face is", self.id)
                else:
                    self.id = "unknown"

            if self.id == "SH":
                return self.id, True
            else:
                return self.id, False

    def close_setting(self):
        self.cam.release()
        cv2.destroyAllWindows()
        #print("Camera closed")

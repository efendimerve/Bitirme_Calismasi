import RPi.GPIO as GPIO
from time import sleep
import datetime
from firebase import firebase
from http.server import BaseHTTPRequestHandler, HTTPServer
import Adafruit_DHT
import time
import busio
import adafruit_adxl34x
import urllib3, urllib, httplib2
import json
import os
from functools import partial
import threading
import speech_recognition as sr
import webbrowser as wb
import pygame
from keypad import keypad
import cv2,os
import numpy as np 
import pickle
from PIL import Image 
from threading import *
from pirc522 import RFID
rdr = RFID()
id = 0


pygame.init()
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
hirsiz=35
GPIO.setup(hirsiz, GPIO.IN)
GPIO.setup(33, GPIO.OUT)
GPIO.setup(32, GPIO.OUT)
GPIO.setup(8, GPIO.OUT)
GPIO.setup(12, GPIO.OUT)
GPIO.setup(13, GPIO.OUT)
GPIO.setup(15, GPIO.OUT)
GPIO.setup(16, GPIO.OUT)
p = GPIO.PWM(33, 100) # GPIO 11 fof PWM with 50Hz
s = GPIO.PWM(32, 100)

GPIO.setwarnings(False)
pir=40
tit=38
ivm=37
water= 36
channel = 31
GPIO.setmode(GPIO.BOARD)
GPIO.setup(channel, GPIO.IN)
gaz=29
GPIO.setup(gaz, GPIO.IN)
GPIO.setup(water, GPIO.IN)
GPIO.setup(ivm, GPIO.IN)
GPIO.setup(tit, GPIO.IN)
GPIO.setup(pir, GPIO.IN)

r = sr.Recognizer()
music1=pygame.mixer.Sound('/home/pi/Music/intheend.wav')
music2=pygame.mixer.Sound('/home/pi/Music/badguy.wav')
music3=pygame.mixer.Sound('/home/pi/Music/dinlenme.wav')
music4=pygame.mixer.Sound('/home/pi/Music/eglence.wav')
music5=pygame.mixer.Sound('/home/pi/Music/kitap.wav')
music6=pygame.mixer.Sound('/home/pi/Music/masal.wav')

Request = None
firebase = firebase.FirebaseApplication('https://raspberrypi-ee2d2-default-rtdb.firebaseio.com/', None)


humidity, temperature = Adafruit_DHT.read_retry(11,4)
shake = 0
#firebase.put("/dht", "/temp", "0.00")
#firebase.put("/dht", "/humidity", "0.00")

# names related to ids: example ==> yasinakun: id=1,  etc
def rc():
    while True:
        rdr.wait_for_tag()
        (error, tag_type) = rdr.request()    
        if not error:
            print("Tag detected")
            (error, uid) = rdr.anticoll()
            if not error:
                print("UID: " + str(uid))
          # Select Tag is required before Auth
                if not rdr.select_tag(uid):
            # Auth for block 10 (block 2 of sector 2) using default shipping key A
                    if rdr.card_auth(rdr.auth_a, 10, [0xC3, 0xFC, 0x7C, 0x0C, 0x4F], uid):
                        p.stop()
                        p.start(100)
                        for i in range(100):
                            p.ChangeDutyCycle(100-i)
                            time.sleep(0.001)
                        p.stop()
              # This will print something like (False, [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
                        print("Reading block 10: " + str(rdr.read(10)))
              # Always stop crypto1 when done working
                        
                        rdr.stop_crypto()
    # Calls GPIO cleanup
    rdr.cleanup()


def keyy():
    
    while True:
        
        # Initialize
        kp = keypad(columnCount = 3)
     
        seq = []


        for i in range(4):
            digit = None
            while digit == None:
                digit = kp.getKey()
            seq.append(digit)
            time.sleep(0.5)
     
        # Check digit code
        print(seq)
        if seq == [1, 1, 1, 1]:
            print ("Code accepted")
            p.stop()
            p.start(100)
            for i in range(100):
                p.ChangeDutyCycle(100-i)
                time.sleep(0.001)
            p.stop()
            
    
        


class RequestHandler_httpd(BaseHTTPRequestHandler):
    def do_GET(self): 
        global Request
        messagetosend = bytes('Hello!',"utf")
        self.send_response(200)
        self.send_header('Content-Type', 'text/plain')
        self.send_header('Content-Length', len(messagetosend))
        self.end_headers()
        self.wfile.write(messagetosend)
        Request = self.requestline
        Request = Request[5 : int(len(Request)-9)]
        print(Request)
        if Request == 'on':
            GPIO.output(12,GPIO.HIGH)
            GPIO.output(8,GPIO.HIGH)         
            #Request = 'off'
        if Request == 'off':
            GPIO.output(8,GPIO.LOW)
            GPIO.output(12,GPIO.LOW)          
        if Request == 'open':
            p.stop()
            p.start(100)
            for i in range(100):
                p.ChangeDutyCycle(100-i)
                time.sleep(0.001)
            p.stop()
        if Request == 'close':
            p.stop()
            p.start(0)
            for i in range(100):
                p.ChangeDutyCycle(i)
                time.sleep(0.001)
            p.stop()
        if Request == 'close_window':
            s.stop()
            s.start(0)
            for i in range(100):
                s.ChangeDutyCycle(i)
                time.sleep(0.001)
            s.stop()
        if Request == 'open_window':
            s.stop()
            s.start(100)
            for i in range(100):
                s.ChangeDutyCycle(100-i)
                time.sleep(0.001)
            s.stop()
        if Request == 'play_bad_guy':
            music2.play()
            time.sleep(25)
            music2.stop()
        if Request == 'play_in_the_end':
            music1.play()
            time.sleep(25)
            music1.stop()
        if Request == 'sleep_mode':
            GPIO.output(12,GPIO.LOW)
            GPIO.output(8,GPIO.LOW)
            music3.play()
            time.sleep(2)
            music6.play()
            time.sleep(25)
            music3.stop()
            music6.stop()
        if Request == 'funny_mode':
            GPIO.output(12,GPIO.LOW)
            GPIO.output(8,GPIO.LOW)
            music4.play()
            for i in range(150):
                GPIO.output(13,GPIO.HIGH)
                GPIO.output(15,GPIO.LOW)
                time.sleep(0.1)
                GPIO.output(13,GPIO.LOW)
                GPIO.output(15,GPIO.HIGH)
                time.sleep(0.1)
            time.sleep(1)
            GPIO.output(13,GPIO.LOW)
            GPIO.output(15,GPIO.LOW)
            music4.stop()
            GPIO.output(12,GPIO.HIGH)
            GPIO.output(8,GPIO.HIGH)
        if Request == 'rest_mode':
            music3.play()
            GPIO.output(8,GPIO.HIGH)
            GPIO.output(12,GPIO.LOW)
            time.sleep(25)
            GPIO.output(8,GPIO.LOW)
            music3.stop()
        if Request == 'reading_mode':
            music5.play()
            GPIO.output(8,GPIO.LOW)
            GPIO.output(12,GPIO.LOW)
            GPIO.output(16,GPIO.HIGH)
            time.sleep(25)
            music5.stop()
            GPIO.output(16,GPIO.LOW)
              
        if Request == 'camera_on':
            os.system("raspivid -o - -t 9999999 -w 640 -h 360 -fps 25|cvlc stream:///dev/stdin --sout '#standard{access=http,mux=ts,dst=:8090}' :demux=h264 & ")
        if Request == 'camera_off':
            os.system("pkill raspivid")
        
            #sleepTime = int(sleepTime)
        if Request == 'giris':
            c=threading.Thread(target=kamera)
            c.start()
        
                
        if Request == 'talking':
            with sr.Microphone() as source:
                os.system("you ")
                print ('Ready...')
                audio = r.listen(source) #dinle
                print ('...')

            text = r.recognize_google(audio, language = "en-US") #algila
            print("You said: "+text)
            if text == 'open light':
                print ('Yan')
                GPIO.output(12,GPIO.HIGH)
                GPIO.output(8,GPIO.HIGH)

            elif text == 'close light':
                GPIO.output(8,GPIO.LOW)
                GPIO.output(12,GPIO.LOW)

            elif text == 'close door':
                p.stop()
                p.start(0)
                for i in range(100):
                    p.ChangeDutyCycle(i)
                    time.sleep(0.001)
                p.stop()

            elif text == 'open door':
                p.stop()
                p.start(100)
                for i in range(100):
                    p.ChangeDutyCycle(100-i)
                    time.sleep(0.001)
                p.stop()

            elif text == 'close window':
                s.stop()
                s.start(0)
                for i in range(100):
                    s.ChangeDutyCycle(i)
                    time.sleep(0.001)
                s.stop()

            elif text == 'open window':
                s.stop()
                s.start(100)
                for i in range(100):
                    s.ChangeDutyCycle(100-i)
                    time.sleep(0.001)
                s.stop()

            elif text == 'play Bad Guy':
                music2.play()
                time.sleep(25)
                music2.stop()
            elif text == 'play in the end':
                music1.play()
                time.sleep(25)
                music1.stop()
            elif text == 'sleep':
                GPIO.output(12,GPIO.LOW)
                GPIO.output(8,GPIO.LOW)
                music3.play()
                time.sleep(2)
                music6.play()
                time.sleep(25)
                music3.stop()
                music6.stop()
            elif text == 'funny':
                GPIO.output(12,GPIO.LOW)
                GPIO.output(8,GPIO.LOW)

                music4.play()
                for i in range(150):
                    GPIO.output(13,GPIO.HIGH)
                    GPIO.output(15,GPIO.LOW)
                    time.sleep(0.1)
                    GPIO.output(13,GPIO.LOW)
                    GPIO.output(15,GPIO.HIGH)
                    time.sleep(0.1)

                time.sleep(1)
                GPIO.output(13,GPIO.LOW)
                GPIO.output(15,GPIO.LOW)
                music4.stop()
                GPIO.output(12,GPIO.HIGH)
                GPIO.output(8,GPIO.HIGH)

            elif text == 'rest':
                music3.play()
                GPIO.output(8,GPIO.HIGH)
                GPIO.output(12,GPIO.LOW)
                time.sleep(25)
                GPIO.output(8,GPIO.LOW)
                music3.stop()
            elif text == 'reading':
                music5.play()
                GPIO.output(8,GPIO.LOW)
                GPIO.output(12,GPIO.LOW)
                GPIO.output(16,GPIO.HIGH)
                time.sleep(25)
                music5.stop()
                GPIO.output(16,GPIO.LOW)
            
            else:
                print ("I cant understand.")
                       
        return
    
def kamera():
    while True:
        names = ['None', 'Merve', 'merve', 'X', 'A', 'B', 'C']

        with open('labels', 'rb') as f:
            dicti = pickle.load(f)
            f.close()

        camera = cv2.VideoCapture(0)
        camera.set(3,640)
        camera.set(4,480)
        minW = 0.1*camera.get(3)
        minH = 0.1*camera.get(4)

        path = os.path.dirname(os.path.abspath(__file__))

        faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")
        recognizer = cv2.face.LBPHFaceRecognizer_create()
        recognizer.read("trainer.yml")

        font = cv2.FONT_HERSHEY_SIMPLEX
            
        while True:
            ret, im =camera.read()
            gray=cv2.cvtColor(im,cv2.COLOR_BGR2GRAY)
            faces = faceCascade.detectMultiScale(gray, scaleFactor=1.2, minNeighbors=5, minSize=(100, 100),flags=cv2.CASCADE_SCALE_IMAGE)
            for (x, y, w, h) in faces:

                cv2.rectangle(im, (x, y), (x + w, y + h), (0, 255, 0), 2)

                id, confidence = recognizer.predict(gray[y:y + h, x:x + w])

                        # Check if confidence is less them 100 ==> "0" is perfect match
                if (confidence < 100):
                    p.stop()
                    p.start(100)
                    for i in range(100):
                        p.ChangeDutyCycle(100-i)
                        time.sleep(0.001)
                    p.stop()
                    id = names[id]
                    confidence = "  {0}%".format(round(100 - confidence))
                    time.sleep(20)
                else:
                    p.stop(0)
                    id = "unknown"
                    confidence = "  {0}%".format(round(100 - confidence))
                        

                cv2.putText(im, str(id), (x + 5, y - 5), font, 1, (255, 255, 255), 2)
                cv2.putText(im, str(confidence), (x + 5, y + h - 5), font, 1, (255, 255, 0), 1)

            cv2.imshow('camera', im)
            k = cv2.waitKey(10) & 0xff  # Press 'ESC' for exiting video
            k=0
            if Request == 'k_kapa':
                k=27

            
            if k == 27:
                break
                
        camera.release()
        cv2.destroyAllWindows()
        
        if k == 27:
            k=0
            break
        
        
        
def update_firebase():
    
    if Request == 'safe_a':
        h=0
        while True:
            
            if GPIO.input(hirsiz):
                hirsizz=1
                if h!=hirsizz:
                    
                    print ("Hırsız " + str(datetime.datetime.now()))
                    data ={"Hirsiz": 1}
                    firebase.post('/sensor/hirsiz', data)
                h=hirsizz
                time.sleep(5)
            
            
                    
            if Request == 'safe_c':
                firebase.delete('/sensor/hirsiz', None)
                break
        
    
    x=0
    y=0
    while True:
        humidity, temperature = Adafruit_DHT.read_retry(11,4)
        if x!=temperature and y!=humidity :
            
            if humidity is not None and temperature is not None:
                time.sleep(5)
                str_temp = ' {0:0.2f} *C '.format(temperature)	
                str_hum  = ' {0:0.2f} %'.format(humidity)
                print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))	
                    
            else:
                print('Failed to get reading. Try again!')
            time.sleep(1)
                
            #data = {temperature,humidity}
            firebase.post('/sensor/dht/temperature', temperature)
            firebase.post('/sensor/dht/humidity', humidity)
            x= temperature
            y= humidity
        else:
            break
    x=0
    while True:
        if GPIO.input(pir) and GPIO.input(tit) and GPIO.input(ivm) :
            deprem=1
            if x!=deprem:
                print ("Deprem! " + str(datetime.datetime.now()))
                data ={"Deprem": deprem}
                firebase.post('/sensor/deprem', data)
            x=deprem
        else:
            firebase.delete('/sensor/deprem', None)
            break
        time.sleep(0.0001)
        
    s=0
    while True:
        if GPIO.input(water):
            su=1
            if s!=su:
                print ("Su basıyor")
                data={"Su": 1}
                firebase.post('/sensor/su', data)
            s=su
        else:
            firebase.delete('/sensor/su', None)
            break
    time.sleep(0.0001)
    
    a=1
    while True:
        alev= GPIO.input(31)
        if (alev==0):
            if a!=alev:
                print("yangın")
                data={"Yangın": 1}
                firebase.post('/sensor/yangın', data)
            a=alev
        else:
            firebase.delete('/sensor/yangın', None)
            break
    time.sleep(0.0001)
    
    g=1
    while True:
        gaz= GPIO.input(29)
        if (gaz==0):
            if g!=gaz:
                print("gaz")
                data={"gaz": 1}
                firebase.post('/sensor/gaz', data)
            g=gaz
        else:
            firebase.delete('/sensor/gaz', None)
            break
        
    time.sleep(0.0001)
    
   
	
    
server_address_httpd = ('192.168.1.107',8080)
httpd = HTTPServer(server_address_httpd, RequestHandler_httpd)
print('Starting server')
t2 = threading.Thread(target=rc)
t1 = threading.Thread(target=keyy)
t=threading.Thread(target=httpd.serve_forever)
t.daemon = True
t1.daemon = True
t2.daemon = True
t.start()
t1.start()
t2.start()

while True: 
		update_firebase()	
        #sleepTime = int(sleepTime)
		sleep(5)



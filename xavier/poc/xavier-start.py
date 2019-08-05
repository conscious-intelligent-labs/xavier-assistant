import speech_recognition
import pyttsx3
import os

speech_engine = pyttsx3.init()
speech_engine.setProperty('rate', 150)

def speak(text):
	speech_engine.say(text)
	speech_engine.runAndWait()

recognizer = speech_recognition.Recognizer()

def listen():
    with speech_recognition.Microphone() as source:
        recognizer.adjust_for_ambient_noise(source)
        audio = recognizer.listen(source)

    try:
        return recognizer.recognize_google(audio)
# or: return recognizer.recognize_google(audio)
    except speech_recognition.UnknownValueError:
        speak("I could not understand what you said")
        return False
    except speech_recognition.RequestError as e:
        print("Recog Error; {0}".format(e))
    return ""
while True :
    print("Say something...")
    speak("I heard you say " + listen())

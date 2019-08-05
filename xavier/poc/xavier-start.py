import speech_recognition
import pyttsx3
import os

speech_engine = pyttsx3.init()
speech_engine.setProperty('rate', 150)

# Define what to say
def xavierResponse(audio) :
    print(audio)
    for line in audio.splitlines() :
        xavierSays(audio)
# Turn on the mouth
def xavierSays(text):
	speech_engine.say(text)
	speech_engine.runAndWait()

recognizer = speech_recognition.Recognizer()

# Turn on the ears
def xavierHears():
    with speech_recognition.Microphone() as source:
        recognizer.adjust_for_ambient_noise(source, duration = 1)
        audio = recognizer.listen(source)
    try:
        request = recognizer.recognize_google(audio).lower()
        # Having Xavier repeat what he thinks he heard for debugging
        xavierResponse("You said: " + request + "\n")
        return request
    except speech_recognition.UnknownValueError:
        print("I didn't catch that...")
        request = xavierHears()
    except speech_recognition.RequestError as e:
        print("Recog Error; {0}".format(e))
    return request

# Turn on the brain
def xavierThinks(request) :
    if "hello xavier" in request :
        xavierResponse("Hello there")

    elif "go to sleep" in request :
        xavierResponse("okay, heading to bed!")
        exit(0)

while True :
    xavierThinks(xavierHears())

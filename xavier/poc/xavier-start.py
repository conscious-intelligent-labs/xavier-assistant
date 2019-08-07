import speech_recognition
import pyttsx3
import os

speech_engine = pyttsx3.init()
speech_engine.setProperty('rate', 175)

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
        askXavier = recognizer.recognize_google(audio).lower()
        # Having Xavier repeat what he thinks he heard for debugging
        xavierResponse("You said: " + askXavier + "\n")
        return askXavier
    except speech_recognition.UnknownValueError:
        print("I didn't catch that...")
        askXavier = xavierHears()
    except speech_recognition.RequestError as e:
        print("Recog Error; {0}".format(e))
    return askXavier

# Turn on the brain
def xavierThinks(askXavier) :
    if "hello xavier" in askXavier :
        xavierResponse("Hello there")

    elif "go to sleep" in askXavier :
        xavierResponse("okay, heading to bed!")
        exit(0)

while True :
    xavierThinks(xavierHears())

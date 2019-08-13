import speech_recognition
import snowboydecoder
# import pyttsx3
import os

# Define what to say
def respond(audio) :
    print(audio)
    for line in audio.splitlines() :
        os.system('mimic -t "' + audio + '"')

recognizer = speech_recognition.Recognizer()

# Only for use with pyttsx3 which will be implemented as a backup in future versions of this start file_path

# speech_engine = pyttsx3.init()
# speech_engine.setProperty('rate', 162)
# def speak(text) :
#    speech_engine.say(text)
#    speech_engine.runAndWait()

# Turn on the ears
def listen():
    with speech_recognition.Microphone() as source:
        recognizer.adjust_for_ambient_noise(source, duration = 1)
        audio = recognizer.listen(source)
        try:
            command = recognizer.recognize_google(audio).lower()
            # Only run the skills methods if the word starts with Xavier
            # if "xavier" in command :
                #Have Xavier repeat what he thinks you said for debugging, comment next line for skip that
            respond(f'You said: {command} \n')
            xavier(command)

        except speech_recognition.UnknownValueError:
            print("I am sorry, I didn't catch that...")
        except speech_recognition.RequestError as e:
            print("Recog Error; {0}".format(e))


# Turn on the brain
def xavier(command) :
    if "hello" in command :
        respond("Hello there, how are you doing today?")

    elif "go to sleep" in command :
        respond("okay, heading to bed!")
        exit(0)
    else :
        respond("i'm sorry, i'm not sure how you want me to respond to that")

while True :
    listen()

import speech_recognition
import snowboydecoder
import sys
import signal
# import pyttsx3
import os

interrupted = False

def signal_handler(signal, frame) :
    global interrupted
    interrupted = True


def interrupt_callback() :
    global interrupted
    return interrupted

# Define what to say
def respond(audio) :
    print(audio)
    for line in audio.splitlines() :
        os.system('mimic -t "' + audio + '"')

# Only for use with pyttsx3 which will be implemented as a backup in future versions of this start file_path

# speech_engine = pyttsx3.init()
# speech_engine.setProperty('rate', 162)
# def speak(text) :
#    speech_engine.say(text)
#    speech_engine.runAndWait()

# Turn on the ears
recognizer = speech_recognition.Recognizer()
def listen() :
    with speech_recognition.Microphone() as source:
        recognizer.adjust_for_ambient_noise(source, duration = 1)
        audio = recognizer.listen(source)
        try :
            command = recognizer.recognize_google(audio).lower()
            # Only run the skills methods if the word starts with Xavier
            # if "xavier" in command :
                #Have Xavier repeat what he thinks you said for debugging, comment next line for skip that
            respond(f'You said: {command} \n')
            xavier(command)

        except speech_recognition.UnknownValueError:
            print("I am sorry, I didn't catch that...")
        except speech_recognition.RequestError as e:
            print(f"Recog Error; {0}")

# Individual skills can be defined as functions and called from the callback lambdas, which can include functions to run detection again, etc.
signal.signal(signal.SIGINT, signal_handler)
def wakeUp(words) :
    sensitivity = [0.5]*len(models)
    detector = snowboydecoder.HotwordDetector(words, sensitivity = sensitivity, audio_gain = 1)
    callbacks = [lambda: respond("yes sir, is there something I can help you with? If i can do anything for you, just let me know!"),
                lambda: xavier("go to sleep")]
    detector.start(detected_callback = callbacks,
                   interrupt_check = interrupt_callback,
                   sleep_time = 0.03)
    detector.terminate()

# Turn on the brain
def xavier(command) :
    if "hello" in command :
        respond("Hello there, how are you doing today?")

    elif "go to sleep" in command :
        respond("okay, heading to bed!")
        exit(0)
    else :
        respond("i'm sorry, i'm not sure how you want me to respond to that")
modelPath = "/usr/local/lib/python3.7/dist-packages/models/"
models = [f"{modelPath}Xavier.pmdl", f"{modelPath}GoToSleep.pmdl"]
wakeUp(models)

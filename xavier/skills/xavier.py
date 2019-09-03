import speech_recognition
import snowboydecoder
import sys
import signal
import os

interrupted = False

def signal_handler(signal, frame) :
    global interrupted
    interrupted = True


def interrupt_callback() :
    global interrupted
    return interrupted

# Define what to say
def respond(text) :
    print(text)
    for line in text.splitlines() :
        os.system('mimic -t "' + text + '"') # "-voice awb" can be replaced with other voices, or removed entirely to use the default.

# Turn on the ears when needed.
recognizer = speech_recognition.Recognizer()
def listen(fname) :
    with speech_recognition.AudioFile(fname) as source:
        audio = recognizer.record(source)
        try :
            command = recognizer.recognize_google(audio)
            # Only run the skills methods if the word starts with Xavier
            # if "xavier" in command :
            # Have Xavier repeat what he thinks you said for debugging, comment next line for skip that
            respond(f'You said: {command} \n')
            xavier(command)

        except speech_recognition.UnknownValueError:
            print("I am sorry, I didn't catch that...")
        except speech_recognition.RequestError as e:
            print(f"Recog Error; {0}")
    os.remove(fname)

# Individual skills can be defined as functions and called from the callback lambdas, which can include functions to run detection again, etc.
signal.signal(signal.SIGINT, signal_handler)
def wakeUp(words) :
    sensitivity = [0.38]*len(words)
    detector = snowboydecoder.HotwordDetector(words, sensitivity = sensitivity, audio_gain = 1)
    callbacks = [lambda: print('recording audio...', end='', flush=True),
                lambda: xavier("go to sleep")]
    detector.start(detected_callback = callbacks,
                   audio_recorder_callback = listen,
                   interrupt_check = interrupt_callback,
                   sleep_time = 0.02)
    detector.terminate()



# Turn on the brain
def xavier(command) :
    if "hello" in command :
        respond("Hello there, how are you doing today?")
    elif command == "xavier" :
        respond("yes?")
        listen()
    elif "go to sleep" in command :
        respond("okay, heading to bed")
        exit(0)
    else :
        respond("i'm sorry, i'm not sure how you want me to respond to that")
modelPath = "models/"
models = [f"{modelPath}Xavier.pmdl", f"{modelPath}GoToSleep.pmdl"]
wakeUp(models)

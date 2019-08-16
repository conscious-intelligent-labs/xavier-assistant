import speech_recognition
import os

# Define what to say
def respond(audio) :
    print(audio)
    for line in audio.splitlines() :
        os.system('~/mimic1/mimic -t "' + audio + '"\n')

recognizer = speech_recognition.Recognizer()

# Turn on the ears
def listen():
    with speech_recognition.Microphone() as source:
        recognizer.adjust_for_ambient_noise(source, duration = 1)
        audio = recognizer.listen(source)
    try:
        command = recognizer.recognize_google(audio).lower()
        # Having Xavier repeat what he thinks he heard for debugging
        respond("You said: " + command + "\n")
        return command
    except speech_recognition.UnknownValueError:
        print("I didn't catch that...")
        command = listen()
    except speech_recognition.RequestError as e:
        print("Recog Error; {0}".format(e))
    return command

# Turn on the brain
def xavierThinks(command) :
    if "hello xavier" in command :
        respond("Hello there")

    elif "go to sleep" in command :
        respond("okay, heading to bed!")
        exit(0)

while True :
    xavierThinks(listen())

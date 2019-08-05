This repository will serve as the hub for our machine learning projects to incorporate with the other projects we do.

Current plans revolve around setting up xavier assistant, where we will skill build to provide support to the other projects, to include:

- Voice activated network status reports from the network monitor
- Voice control of PiRouter configuration
- Alerts from network monitor and PiRouter for various events

The first step we have is to give Xavier the ability to hear and talk.
This is achieved by using Text-To-Speech (TTS) and Speech-To-Text(STT) engines.
A number of options exist, many of them paid. For TTS, for the sake of Proof of Concept and to get working on skill building, a Free and Open Source Software (FOSS) package called PyTTSx3 (for Python 3) is being used. Google's default
API is being used for the STT, using the package SpeechRecognition.

Moving forward, Mozilla's DeepSpeech project will be used on a server for STT, with MaryTTS also being run. This will give us the ability to train the Speech Recognition machine ourselves, and to customize the voice output of Xavier with MaryTTS.

The "thinking" portion of Xavier will be handled by our skill building, which can begin with the basic tools that we will start with.
First steps:

- Simple "repeat after me" functionality that can be used to debug recognition
- Open websites and read from them in certain cases
- Modify files on the system
- Connect to other computers using SSH and FTP and interact with them as needed

A couple of advantages to using PyTTSx3 are that it can be set up easily and does not require any server to run. It is lightweight, fully FOSS, and can run on Raspberry Pi without needing an external server. Disadvantage is the very robotic sound. MaryTTS will provide more robust speech output with customization.

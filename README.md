This repository houses the core code and skills needed to run our Xavier assistant.

Xavier Assitant will interface with and control our other projects with voice, with plans to include things like:

- Voice activated network status reports from the network monitor
- Voice control of PiRouter configuration
- Alerts from network monitor and PiRouter for various events

Xavier will also handle various other standard assistant tasks.

Xavier assistant uses the mimic engine to speak using Text-To-Speech (TTS) and the preliminary setup uses the SpeechRecognition python module with Google's Speech-To-Text (STT) API to listen. Optionally, PyTTSx3 can be used with an alternate start file or by modifying the start file if preferred, or if mimic is unavailable on the machine.

Mimic has 2 versions, mimic2 being the online and primary version which we will leave running on a service, with mimic1 as an offline fallback if no connection to the mimic2 server can be made. PyTTSx3 is a much more robotic-sounding option, but easier to setup and a bit more lightweight.

This project is focused on privacy-oriented, lightweight, but powerful functionality. As such, moving forward, migration to alternative Speech Recognition options will be a priority. As it stands, the SpeechRecognition module provides built in functionality for a number of different recognition options, some paid and some free, with a couple being available offline.

# Installation Instructions

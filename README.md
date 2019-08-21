# Xavier AI-Assistant

"Xavier" is an AI Assistant with intelligent voice recognition and natural language understanding that gives you the ability to voice-enable any connected device that has a microphone and speaker and perform defined skills/task.

## Installation

```bash
git clone https://github.com/conscious-intelligent-labs/xavier-assistant.git
cd xavier-assistant/xavier/xavier
chmod 755 run.sh
chmod 755 setup.sh

# Setup project.
./setup.sh

# Start project.
./run.sh

## Manually Install Mouth and Ears
# Refer to wiki for manual mimic and snowboy installation instructions until this step is automated (see isues)
```

## Usage

### Start Xavier.
```bash
cd xavier-assistant/xavier
python ./manage.py runserver
```

### View Xavier via HTTP
```bash
http://127.0.0.1:8000
```

### View Xavier Skill Service Status via HTTP
```bash
http://127.0.0.1:8000/skills/status
```
#!/bin/bash

echo "Installing xavier dependencies..."
python3 -m pip install -r requirements.txt

echo "Installing Mimic dependencies..."
apt-get install gcc make pkg-config automake libtool libasound2-dev

echo "Done installing dependencies"

echo "Installing Mimic..."
cd /opt/
git clone https://github.com/MycroftAI/mimic1.git mimic
cd /opt/mimic
./dependencies.sh --prefix="/usr/local"
./autogen.sh
./configure --prefix="/usr/local"
make
make check

echo "Setting mimic path"

export PATH=$PATH:/opt/mimic

echo "Mimic install complete"

echo "Testing Mimic Install..."
./mimic -t "Mimic is working."


FILE=bin/activate
if [ -f "$FILE" ]; then
    echo "$FILE exist"
    source bin/activate
else 
    echo "$FILE does not exist"
    echo "Creating Virtual Environment"
    python3 -m venv ./
    source bin/activate
    python3 -m pip install -r requirements.txt
fi

echo "Done setup!"

#!/bin/bash

echo "Installing dependencies..."
python3 -m pip install -r requirements.txt
echo "Done installing dependencies"

echo "Set Python Path"

echo "Python path set."

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

#!/bin/bash

# Function to display help message
function show_help() {
    echo "Usage: ./set-venv.sh [OPTION]"
    echo "Options:"
    echo "  --no-cache     Delete the '_build' directory to remove cache before building"
    echo "  --help         Show this help message"
}

# Check for --help argument and display help message
if [[ $1 == "--help" ]]; then
    show_help
    exit 0
fi

# Check for --no-cache argument and delete _build directory if present
if [[ $1 == "--no-cache" ]]; then
    echo "Deleting '_build/' directory to remove cache..."
    rm -rf _build/
fi

# Define the virtual environment directory
VENV_DIR="al-venv"

# Check if the virtual environment already exists
if [ ! -d "$VENV_DIR" ]; then
  # Create the virtual environment
  python3 -m venv $VENV_DIR
  echo "Virtual environment '$VENV_DIR' created."

  # Activate the virtual environment
  source $VENV_DIR/bin/activate

  # Install required packages
  pip install sphinx sphinx-immaterial sphinx-autobuild sphinx-tabs
  echo "Packages 'sphinx', 'sphinx-immaterial', and 'sphinx-autobuild' installed."
else
  # Activate the virtual environment
  source $VENV_DIR/bin/activate
  echo "Virtual environment '$VENV_DIR' activated."
fi

# Build the tutorial using sphinx-autobuild
sphinx-autobuild . _build/html --port 8111
echo "Sphinx live server started."


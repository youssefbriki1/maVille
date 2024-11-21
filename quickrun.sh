#!/bin/bash

SPRING_BOOT_BACKEND_PATH="maville_backend"
STREAMLIT_FRONTEND_PATH="maville_frontend"
VENV_PATH=".venv"

if [[ -d "$VENV_PATH" ]]; then
    source "$VENV_PATH/bin/activate"
    echo "Virtual environment activated."
else
    echo "Virtual environment not found at $VENV_PATH."
    exit 1
fi

echo "Starting Spring Boot backend with Maven..."
cd "$SPRING_BOOT_BACKEND_PATH"
if [[ -f "pom.xml" ]]; then
    mvn spring-boot:run &
    SPRING_PID=$!
    echo "Spring Boot backend started with PID $SPRING_PID."
else
    echo "pom.xml not found. Make sure this is a Maven project."
    exit 1
fi

echo "Starting Streamlit frontend..."
cd "../$STREAMLIT_FRONTEND_PATH"
if [[ -f "app.py" ]]; then
    streamlit run app.py &
    STREAMLIT_PID=$!
    echo "Streamlit frontend started with PID $STREAMLIT_PID."
else
    echo "Streamlit app.py not found. Make sure your frontend script exists."
    kill $SPRING_PID
    exit 1
fi

echo "Press Ctrl+C to stop both backend and frontend."
trap "echo 'Stopping backend and frontend...'; kill $SPRING_PID $STREAMLIT_PID; exit" SIGINT SIGTERM
wait $SPRING_PID $STREAMLIT_PID

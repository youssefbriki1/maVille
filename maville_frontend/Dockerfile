# Use a Python 3.11 slim base image
FROM python:3.11-slim

# Set working directory inside the container
WORKDIR /app

# Copy the requirements file and install dependencies
COPY requirements.txt .
RUN pip install -r requirements.txt

# Copy all the application files into the container
COPY . .

# Expose the default Streamlit port
EXPOSE 8501

# Configure Streamlit to allow external access
ENV STREAMLIT_SERVER_HEADLESS true
ENV STREAMLIT_SERVER_ADDRESS 0.0.0.0
ENV STREAMLIT_SERVER_PORT 8501

# Run Streamlit when the container starts
ENTRYPOINT ["streamlit", "run", "app.py"]

FROM python:3.11-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

EXPOSE 8501

ENV STREAMLIT_SERVER_HEADLESS true
ENV STREAMLIT_SERVER_ADDRESS 0.0.0.0
ENV STREAMLIT_SERVER_PORT 8501

ENTRYPOINT ["streamlit", "run", "app.py"]

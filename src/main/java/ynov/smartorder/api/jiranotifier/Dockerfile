# Dockerfile - jira-notifier
FROM python:3.11-slim

WORKDIR /app

# Copie du script Python
COPY app.py .

# Install des dépendances
RUN pip install flask python-dotenv requests docker

# Accès au port Flask
EXPOSE 5000

CMD ["python", "app.py"]

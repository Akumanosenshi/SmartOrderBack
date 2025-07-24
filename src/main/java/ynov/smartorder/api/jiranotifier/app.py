import requests
import json
from requests.auth import HTTPBasicAuth
from dotenv import load_dotenv
import os
from flask import Flask, request, jsonify
import docker  # Utilisation de l'API Docker Python

# Charger .env
load_dotenv()

# Config Jira
JIRA_EMAIL = os.getenv("JIRA_EMAIL")
JIRA_TOKEN = os.getenv("JIRA_TOKEN")
JIRA_URL = os.getenv("JIRA_URL")
JIRA_PROJECT_KEY = os.getenv("JIRA_PROJECT_KEY")
ISSUE_TYPE_ID = os.getenv("JIRA_ISSUE_TYPE_ID")

auth = HTTPBasicAuth(JIRA_EMAIL, JIRA_TOKEN)

headers = {
    "Accept": "application/json",
    "Content-Type": "application/json"
}

app = Flask(__name__)

def get_container_logs(container_name: str, tail=30):
    try:
        client = docker.from_env()
        container = client.containers.get(container_name)
        logs = container.logs(tail=tail).decode("utf-8")
        return logs
    except Exception as e:
        return f"Erreur lors de la récupération des logs : {str(e)}"

@app.route("/alert", methods=["POST"])
def create_issues():
    data = request.get_json() or {}
    alerts = data.get("alerts", [])

    results = []
    for alert in alerts:
        instance = alert.get("labels", {}).get("instance", "Inconnu")
        summary = alert.get("annotations", {}).get("summary", "Incident détecté")
        description = alert.get("annotations", {}).get("description", "Pas de description fournie")

        logs = get_container_logs("smartorder_app", tail=30)
        full_description = f"{description}\n\nLogs récents du conteneur :\n```\n{logs}\n```"

        payload = json.dumps({
            "fields": {
                "project": {"key": JIRA_PROJECT_KEY},
                "summary": summary,
                "description": {
                    "type": "doc",
                    "version": 1,
                    "content": [
                        {
                            "type": "paragraph",
                            "content": [
                                { "type": "text", "text": full_description }
                            ]
                        }
                    ]
                },
                "issuetype": {"id": ISSUE_TYPE_ID}
            }
        })

        response = requests.post(
            f"{JIRA_URL}/rest/api/3/issue",
            headers=headers,
            auth=auth,
            data=payload
        )

        if response.status_code == 201:
            issue = response.json()
            results.append({ "status": "created", "key": issue["key"] })
        else:
            results.append({ "status": "error", "message": response.text })

    return jsonify(results), 201

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)

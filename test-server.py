import requests
import json
from requests.auth import HTTPBasicAuth
from dotenv import load_dotenv
import os

# Charger .env local (optionnel)
load_dotenv()

# Configuration
JIRA_EMAIL = os.getenv("JIRA_EMAIL") or input("JIRA Email: ")
JIRA_TOKEN = os.getenv("JIRA_TOKEN") or input("JIRA API Token: ")
JIRA_URL = os.getenv("JIRA_URL") or "https://akumanosenshi.atlassian.net"
JIRA_PROJECT_ID = os.getenv("JIRA_PROJECT_ID") or "10001"  # Change par ton vrai ID projet
ISSUE_TYPE_ID = os.getenv("JIRA_ISSUE_TYPE_ID") or "10011"  # ex: Bug ou Tâche

auth = HTTPBasicAuth(JIRA_EMAIL, JIRA_TOKEN)

headers = {
    "Accept": "application/json",
    "Content-Type": "application/json"
}

# Exemple de données dynamiques à injecter
user_list = ["Service API", "Base de données", "Supervision Prometheus"]

# Création des tickets
for name in user_list:
    summary = f"Incident détecté sur le service : {name}"
    description = f"Le service '{name}' a rencontré un problème critique détecté par Prometheus."

    payload = json.dumps({
    "fields": {
        "project": {
            "id": JIRA_PROJECT_ID
        },
        "summary": summary,
        "description": {
            "type": "doc",
            "version": 1,
            "content": [
                {
                    "type": "paragraph",
                    "content": [
                        {
                            "type": "text",
                            "text": description
                        }
                    ]
                }
            ]
        },
        "issuetype": {
            "id": ISSUE_TYPE_ID
        }
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
        print(f"✅ Ticket créé : {issue['key']} → {JIRA_URL}/browse/{issue['key']}")
    else:
        print(f"❌ Erreur ({response.status_code}): {response.text}")

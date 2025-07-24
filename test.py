import requests

url = "http://127.0.0.1:5000/alert"
payload = {"alerts": ["Service API", "Base de données"]}

resp = requests.post(url, json=payload)
print("Statut HTTP :", resp.status_code)
print("Réponse JSON :", resp.json())

# 🍽️ SmartOrder – API REST (Spring Boot)

**SmartOrder** est une API REST complète, développée avec **Spring Boot**, qui permet à un restaurant de gérer les **commandes à emporter**, les **réservations**, et le **menu en ligne**.  
Le projet s’intègre avec un front-end Angular/Ionic, utilise **JWT pour la sécurité**, et propose un **système de supervision en temps réel** (Prometheus, Grafana) ainsi qu’une **intégration continue via GitHub Actions**.

---

## 🚀 Fonctionnalités principales

- Authentification sécurisée par JWT (Spring Security)
- Gestion des utilisateurs, commandes, plats et réservations
- Filtrage par rôle (`USER` / `RESTAURANT`)
- Statistiques et indicateurs métiers (revenu, nombre de réservations…)
- Monitoring technique (Grafana, Prometheus)
- Alertes automatisées vers Jira en cas d’incident

---

## ⚙️ Déploiement local avec Docker

### 🔧 Prérequis
- Avoir cloner le projet en local
- Docker Desktop installé et lancé
- Un outil d’administration PostgreSQL (HeidiSQL, pgAdmin, DBeaver ou autre)

### 🐘 Étape 1 – Lancer la base de données

```bash
cd SmartOrderBack/docker/Postgres
docker-compose up -d
```

Cela démarre PostgreSQL avec l’utilisateur :

Utilisateur : smartorder_user

Mot de passe : V82g!dxgZ!tR9#qm

Port : 5432

# ☕ Étape 2 – Lancer l’API Spring Boot

```bash
cd SmartOrderBack
docker-compose up -d
```

L’API est accessible à :
http://localhost:8080


# 💾 Étape 3 – Injection des données initiales
Se connecter à la base avec un outil client PostgreSQL (HeidSQL, pgAdmin, DBeaver...) puis injecter manuellement le script suivant :

```sql
INSERT INTO public.t_users (id, email, firstname, lastname, password, role) VALUES
  ('f61448b7-0c66-443c-ac32-5b4c368aec59', 'admin@gmail.com',   'Admin',   'Admin', '$2a$10$u0HUwoFvJHlsnP4i4RmCdubYsamTwv7hs0QXDd83cLUeTpgXQ/kAe', 'RESTAURANT')

INSERT INTO public.t_meals (id, category, description, emoji, price, title) VALUES
  ('c103b637-392c-4fc3-b1fa-649800af8f0b', 'sushi',            'Tranches de saumon cru, marinées au soja',      '🍣', 17.50, 'Sushi Saumon'),
  ('5e596c6c-078a-4237-a2e9-4a25dee57342', 'sushi',            'Assortiment de thon et daurade',                '🍣', 18.90, 'Sushi Mix'),
  ('8c932e09-e520-41cd-9201-67ae69ce1cf7', 'pizza',            'Pizza Margherita avec mozzarella fraîche',      '🍕', 12.00, 'Pizza Margherita'),
  ('e693f6ba-1fe6-44b6-ad91-f8f6db81e4fb', 'pizza',            'Pizza pepperoni épicée et fromage fondu',       '🍕', 14.00, 'Pizza Pepperoni'),
  ('415f776e-58b4-47cf-bf90-faa695ffe2e2', 'dessert',          'Fondant au chocolat cœur coulant',              '🍫',  6.50, 'Fondant Chocolat'),
  ('756e105c-e852-4620-9875-3b2c7b411531', 'dessert',          'Tarte aux fraises maison',                      '🍓',  6.90, 'Tarte Fraises'),
  ('4181d643-a407-43ac-8b91-b78b9667765d', 'salade',           'Salade César au poulet grillé',                 '🥗',  9.80, 'Salade César'),
  ('45dd07d6-4f67-43ee-a2b9-dc4e74363950', 'salade',           'Salade grecque avec feta et olives',            '🥗',  8.50, 'Salade Grecque'),
  ('c3efa317-dc66-4ea6-bf40-1fc61e8483f8', 'boisson',          'Coca-Cola 33cl',                                '🥤',  2.50, 'Coca-Cola'),
  ('fdb71767-e421-4eb7-a5fe-04f7e7e77a35', 'boisson',          'Eau pétillante San Pellegrino',                '💧',  2.20, 'San Pellegrino'),
  ('31becda3-2df2-413e-a11e-d895dcffd6b4', 'burger',           'Burger bœuf cheddar avec frites',              '🍔', 11.50, 'Burger Classique'),
  ('cdbd40bf-6174-4f02-9468-152c95ae8498', 'burger',           'Burger végétarien steak pois chiches',         '🥬', 10.90, 'Burger Veggie'),
  ('b547be5d-b009-4fd6-9414-59aabb148d96', 'pâtes',            'Spaghetti bolognaise maison',                  '🍝', 10.50, 'Spaghetti Bolognaise'),
  ('4b615415-e176-40ec-8e07-104c39407d2f', 'pâtes',            'Penne à la crème et au poulet',                '🍝', 10.80, 'Penne Poulet'),
  ('213d2678-b1c3-4fef-8669-f94d2bc28ce1', 'poisson',          'Filet de bar grillé, purée maison',            '🐟', 16.90, 'Filet de Bar'),
  ('752dba42-1d4c-45d4-ae5f-68ec1dbed3ec', 'viande',           'Entrecôte grillée, sauce au poivre',           '🥩', 18.00, 'Entrecôte Poivre'),
  ('0e048496-246c-4496-8eaf-f66681851bb3', 'plat_vegetarien',  'Curry de légumes aux noix de cajou',           '🌶️', 11.90, 'Curry Végétarien'),
  ('b8e59485-8067-44e3-b715-a2ab7e27efca', 'accompagnement',    'Frites maison croustillantes',                 '🍟',  3.50, 'Frites Maison'),
  ('1895db7d-1fba-4bb1-9b8a-1e260c923e87', 'boisson',          'Smoothie mangue ananas frais',                 '🥭',  4.20, 'Smoothie Tropical'),
  ('8770c61a-f305-4d5f-af66-b4c552d79299', 'dessert',          'Glace vanille, caramel beurre salé',           '🍨',  5.00, 'Glace Vanille Caramel');

```

pour se connecter : 

Utilisateur : smartorder_user

Mot de passe : V82g!dxgZ!tR9#qm

Port : 5432


# 🌐 Étape 4 – Lancer le front-end (optionnel) 
lien github :  https://github.com/Akumanosenshi/SmartOrderFront

```bash
cd smart-order-client
docker-compose up -d
```

Accès à l’interface utilisateur (client ou restaurant) :
http://localhost:8100

# 📊 Supervision & Alertes
L’état du système est monitoré en temps réel via Prometheus et Grafana.

## 🔍 Dashboard Grafana
http://localhost:3000

## 📈 Alertes Prometheus
http://localhost:9090/alerts

En cas d’interruption de service (/actuator/health), une alerte est levée automatiquement et un ticket Jira est créé via un microservice Flask.

# 🔁 Intégration continue (CI/CD)
Le dépôt intègre un pipeline GitHub Actions (.github/workflows/maven.yml) qui :

Compile le projet à chaque push ou pull request

Exécute les tests unitaires (mvn verify)

Empêche le merge en cas d’erreur

Assure un suivi de la qualité en continu

```yaml
on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Build & Test
        run: mvn clean verify
```

# 🧪 Tests
Le projet back-end comprend plus de 140 tests unitaires (services, sécurité, logique métier), exécutés automatiquement via la CI.
Les tests sont écrits avec JUnit 5 et Mockito.

# 📚 Liens utiles

✅ API SmartOrder (localhost:8080)

# 🙏 Remerciements
Ce projet a été développé dans le cadre du Projet de fin d’année 2024/2025 à YNOV Bordeaux.
Un grand merci à tous les encadrants, intervenants et soutiens techniques ayant contribué à la réussite de ce développement.

Développé par Perard Alison – M2 Développement Full Stack

# Groupe 2 - Maville - Application de Gestion des Projets de Construction à Montréal

## Description du Projet

L'application **Maville** est conçue pour faciliter la mise en relation directe entre les habitants et les intervenants de la ville de Montréal concernant les projets de construction. L'objectif de l'application est de simplifier les démarches, d'améliorer la communication et, en fin de compte, de rendre la vie quotidienne des citoyens plus agréable en offrant une gestion efficace des travaux urbains.

Les principales fonctionnalités de l'application incluent :

- Notification des résidents concernant les projets de construction dans leur quartier
- Soumission de requêtes de travaux par les résidents
- Gestion des réponses par les intervenants (entreprises, services municipaux)
- Suivi en temps réel de l'évolution des travaux

## Liste des fonctionnalités de l'application par rôle

### Résidents
- Soumettre des requêtes de travaux
- Recevoir des notifications sur les projets de construction
- Suivre l'évolution des travaux

### Intervenants
- Gérer les requêtes de travaux soumises par les résidents
- Répondre aux requêtes et mettre à jour leur statut
- Soumettre des projets de construction

## Organisation des fichiers du répertoire

- `src/main/java/ca/umontreal/ift2255/groupe2/maville_backend/controllers` : Contient les contrôleurs REST pour gérer les requêtes HTTP.
- `src/main/java/ca/umontreal/ift2255/groupe2/maville_backend/model` : Contient les classes de modèle représentant les entités de l'application.
- `src/main/resources` : Contient les fichiers de configuration et les ressources statiques.
- `src/main/java/ca/umontreal/ift2255/groupe2/maville_backend/data` : Contient les données.
- `src/test/java/ca/umontreal/ift2255/groupe2/maville_backend/test` : Contient les tests unitaires pour les différentes classes de l'application.


# Devoir 3

## Prerequis
- Assurez-vous d'avoir installé Java 17 ou une version ultérieure sur votre machine. 
```bash
java --version
```
- Assurez-vous d'avoir installé Maven sur votre machine.
```bash
mvn --version
```

sinon, vous pouvez installer Maven sur UNIX en utilisant la commande suivante:

```bash
sudo apt install maven
```

ou sur macOS

```bash
brew install maven
```
Installez aussi streamlit et pydantic en faisant

```bash
pip3 install -r requirements.txt
```
- Assurez-vous d'avoir Python avec une version >= 3 sur votre machine.
```bash
python3 --version
```

## Pour lancer le programme

1. Clonez le dépôt GitHub en utilisant l'une des commandes suivantes :

```bash
git clone
```


2. Démarrez le backend 

```bash
cd maville_backend
mvn spring-boot:run 
```
3. Démarrez le frontend 

```bash
cd maville_frontend
mvn streamlit run app.py
```

## Initialisation de la base de données

- Voici un aperçu des données de test que vous pouvez utiliser pour initialiser la base de données.
### Résidents
| Nom                     | Email                        | Mot de Passe | Numéro de Téléphone | Adresse             | Code Postal | Date de Naissance | Rôle      |
|-------------------------|------------------------------|--------------|----------------------|---------------------|-------------|-------------------|-----------|
| Jalal Fatouaki          | fatouaki.jalal@gmail.com     | a            | 123456789            | L'ile perrot        | j7v 0a4     | 2005-10-31        | Résident  |
| Youssef Briki           | youssef.briki05@gmail.com    | a            | 123456789            | L'ile perrot        | j7v 0a4     | 2005-10-01        | Résident  |
| Royann Lee              | royann.lee@gmail.com         | a            | 123456789            | Montreal            | h3a 0d3     | 2005-10-01        | Résident  |
| Louis-Edouard Lafontant | louis.edouard.lafontant@gmail.com | a        | 123456789            | Montreal            | h3a 0d3     | 2005-10-01        | Résident  |
| Larry Guiffo            | larry.guiffo@gmail.com       | a            | 123456789            | Montreal            | h3a 0d3     | 2005-10-01        | Résident  |

### Intervenants

| Nom               | Email                    | Mot de Passe | Numéro de Téléphone | Adresse | Code Postal | ID Ville | Rôle          |
|-------------------|--------------------------|--------------|----------------------|---------|-------------|----------|---------------|
| Jean Belanger     | jean.belanger@gmail.com  | a            | null                 | null    | null        | 0        | Intervenant   |
| Mathilde Belanger | mathilde.belanger@gmail.com | a         | null                 | null    | null        | 0        | Intervenant   |
| Yves Constant     | yvesconstant@gmail.com   | a            | null                 | null    | null        | 0        | Intervenant   |
| Jean Michel       | jean.michel@gmail.com    | a            | null                 | null    | null        | 0        | Intervenant   |
| Nassim Larache    | larache05@gmail.com      | a            | null                 | null    | null        | 0        | Intervenant   |
## Troubleshooting


- Verifiez que vous avez bien installé les prérequis
- Contactez nous via discord
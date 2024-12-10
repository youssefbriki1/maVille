# Groupe 2 - Maville - Application de Gestion des Projets de Construction à Montréal

## Description du Projet

L'application **Maville** est conçue pour faciliter la mise en relation directe entre les habitants et les intervenants de la ville de Montréal concernant les projets de construction. L'objectif de l'application est de simplifier les démarches, d'améliorer la communication et, en fin de compte, de rendre la vie quotidienne des citoyens plus agréable en offrant une gestion efficace des travaux urbains.

Les principales fonctionnalités de l'application incluent :

- Notification des résidents concernant les projets de construction dans leur quartier
- Soumission de requêtes de travaux par les résidents
- Gestion des réponses par les intervenants (entreprises, services municipaux)
- Suivi en temps réel de l'évolution des travaux

# Devoir 2

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

2. Creez un python virtual environment
   
```bash
python3 -m venv venv
```
3. Autoriser le script à s'executer

```bash
chmod +x quickrun.sh
```

4. Executez le script de lancement

```bash
./quickrun.sh
```

## Initialisation de la base de données

- Voici un aperçu des données de test que vous pouvez utiliser pour initialiser la base de données.
### Résidents

| Nom            | Email                 | Mot de Passe | Numéro de Téléphone | Adresse             | Code Postal | Date de Naissance | Rôle      |
|----------------|-----------------------|--------------|----------------------|---------------------|-------------|-------------------|-----------|
| Resident1 Res  | Resident1@email.com   | a            | 1234567890          | Adresse, Montréal   | H3T         | 2024-11-22        | Résident  |
| Resident2 Res  | Resident2@email.com   | az           | 123456789022        | Adresse2, Montréal  | H3T         | 2024-11-22        | Résident  |
| Resident3 Res  | Resident3@email.com   | az           | 123456789022        | Adresse2, Montréal  | H3T         | 2024-11-22        | Résident  |

### Intervenants


| Nom               | Email                    | Mot de Passe | Numéro de Téléphone | Adresse | Code Postal | ID Ville | Rôle          |
|-------------------|--------------------------|--------------|----------------------|---------|-------------|----------|---------------|
| Intervenant1 Int  | Intervenant1@email.com   | az           | null                 | null    | null        | 0        | Intervenant   |
| Intervenant2 Int  | Intervenant2@email.com   | az           | null                 | null    | null        | 0        | Intervenant   |
| Intervenant3 Int  | Intervenant3@email.com   | az           | null                 | null    | null        | 0        | Intervenant   |

## Troubleshooting

- Verifiez le path de votre environnement virtuel python dans le fichier quickstart.sh
- Verifiez que vous avez bien installé les prérequis

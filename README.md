# Groupe 2 - Maville - Application de Gestion des Projets de Construction à Montréal

## Description du Projet

L'application **Maville** est conçue pour faciliter la mise en relation directe entre les habitants et les intervenants de la ville de Montréal concernant les projets de construction. L'objectif de l'application est de simplifier les démarches, d'améliorer la communication et, en fin de compte, de rendre la vie quotidienne des citoyens plus agréable en offrant une gestion efficace des travaux urbains.

Les principales fonctionnalités de l'application incluent :

- Notification des résidents concernant les projets de construction dans leur quartier
- Soumission de requêtes de travaux par les résidents
- Gestion des réponses par les intervenants (entreprises, services municipaux)
- Suivi en temps réel de l'évolution des travaux

## Installation et Exécution du Prototype

Pour exécuter le prototype, suivez les étapes ci-dessous :

1. Clonez le dépôt GitHub en utilisant l'une des commandes suivantes :

```bash
   git clone git@github.com:youssefbriki1/ift2255-devoir1-groupe2.git
   cd prototypes && java -jar main.jar
```


Les données du résident sont : email: resident1@gmail.com password : maville2024 
Les données de l'intervenant sont : email : intervenant1@gmail.com password : ameliore2024@


## Troubleshooting

1. Pour complier: 

```bash
javac main.java
jar cmvf META-INF/MANIFEST.MF main.jar main.class
java -jar main.jar

```

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

2. Creez un python virtual environmen
   
```bash
   python3 -m venv venv
```

3. Executez le script de lancement

```bash
source quickstart.sh
```


## Troubleshooting

- Verifiez le path de votre environnement virtuel python dans le fichier quickstart.sh
- Verifiez que vous avez bien installé les prérequis

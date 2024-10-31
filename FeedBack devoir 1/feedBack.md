# Glossaire 
- Application MaVille
- Code de la ville
- type de problème, signaler un problème 

Les points marqués ci-dessus sont les points importants que vous n'avez pas définis dans votre glossaire.

# Diagramme de cas d'utilisation

### Respect du formalisme 

### Identification des acteurs 
- Une relation de généralisation peut exister entre résident et intervenant pour les CUs créer un compte utilisateur et se connecter.

### Cas d'utilisation 
- Les CUs des utilisateurs de l'application ont bien été relevés.
- Le CU **vérifier la légitimité** de l'intervenant relié au résident n'est pas pertinent.
- Quelques problèmes avec les relations entre les CUs, voir les problèmes dans le fichier PDF feedback diagramme.

# Scénarios 
- S'inscrire comme résident
   - ⚠️ Problème de précondition, la précondition est l'état du système avant l'interaction avec l'utilisateur. Donc, pour ce CU il n'y a pas de précondition.
   - Le scénario de ce CU a été fait, mais pourtant il n'est pas présent dans le diagramme de CU.
   - Après la sélection de l'option 'S'inscrire', le système doit afficher le formulaire d'inscription.
   
- Se connecter
   - ⚠️ Problème de précondition, la précondition est l'état du système avant l'interaction avec l'utilisateur. Donc, pour ce CU il n'y a pas de précondition.
   - Il manque une étape, i.e :
       - L'utilisateur est connecté et dirigé vers le menu.
       - Cela permet d'être conforme avec la postcondition.
   - ⚠️ Manque de cohérence avec le diagramme de CU, le CU **Modifier les informations personnelles** étend ce CU, mais pas de scénario alternatif fait.

- Soumettre une requête de travail
   - ⚠️ Problème de postcondition, la postcondition est l'état du système après l'interaction avec l'utilisateur.
      - Postcondition : la requête a bien été soumise.
   - L'étape 3 doit être le système affiche le formulaire de requête.
   - Manque de scénario alternatif pour accepter et refuser une candidature qui, tous deux, étendent le CU.

- Refuser une candidature
   - L'étape 3 n'est pas pertinente, car ce n'est pas une action liée à ce CU, i.e. ne va pas être faite par l'acteur lors de l'utilisation de l'application pour réaliser ce CU.
   - ⚠️ Le scénario 2a est en contradiction avec la précondition.

- Accepter une candidature  
   - L'étape 3 n'est pas pertinente, car ce n'est pas une action liée à ce CU, i.e. ne va pas être faite par l'acteur lors de l'utilisation de l'application pour réaliser ce CU.
   - ⚠️ Le scénario 2a est en contradiction avec la précondition.

- Signaler un problème à la ville
   - ⚠️ Précondition n'est pas correcte.
   - Postcondition n'est pas correcte. La postcondition sera : le problème a bien été signalé à la ville.
   - Manque de scénario alternatif pour le CU **Traiter le signalement de l'utilisateur**.

- Définir ses préférences de notifications
   - Incohérence entre le 3a et le diagramme de CU, le fait de pouvoir filtrer est un include, mais est mis comme scénario alternatif et non scénario principal.
   - Mauvaise postcondition, la postcondition doit être : les préférences des notifications ont été mises à jour.

- Définir ses préférences d’horaire
   - 1. L'utilisateur clique sur l'option consulter les travaux à venir.
   - 2. Le système affiche les travaux.
   - 3. L'utilisateur choisit un travail dans la liste.
   - 4. Le système affiche les informations du travail et la possibilité de spécifier ses préférences d'horaire.
   - 5. L'utilisateur sélectionne l'option spécifier l'horaire.
   - ...

- Consulter les préférences des résidents
   - Manque de cas alternatif pour représenter le CU qui étend ce CU.

- S'inscrire comme intervenant
   - ⚠️ Problème de précondition, la précondition est l'état du système avant l'interaction avec l'utilisateur. Donc, pour ce CU il n'y a pas de précondition.
   - Le scénario de ce CU a été fait, mais pourtant il n'est pas présent dans le diagramme de CU.
   - Après la sélection de l'option 'S'inscrire', le système doit afficher le formulaire d'inscription.

# Diagramme d'activités 
### Respect du formalisme 
- En général, le formalisme est bien respecté.

### Contenu du diagramme 
- Présence de nœuds d'option sans noms des différents choix.
- Manque de certaines actions dans les diagrammes.
- Un diagramme incompréhensible.
- Boucle infinie dans un diagramme.
- Toutes les autres remarques sont dans le fichier feedback diagramme.

# Analyse 📈 
#### Risques 
- Le risque ici semble plutôt être "Inconsistance ou faible usage de l'application". La solution de mitigation ne propose aucune procédure ou considération.

#### Besoins non fonctionnels 
- Le 1er point peut être reformulé en **portabilité**.
   - La justification n'est pas top, on pourrait dire : pour pouvoir desservir plusieurs utilisateurs, i.e. les utilisateurs iOS, Android, etc., notre application doit être portable.
- Facilité d'utilisation reformulée en **utilisabilité**.
- La justification du point 2 décrit la **disponibilité**.
   - Définition de **fiabilité** : L'application doit être fiable et fonctionner sans bugs majeurs. Un système de logs détaillés et de surveillance doit permettre de détecter les erreurs et de les corriger rapidement.
- Mémoire est un besoin matériel et non non-fonctionnel.
   - La justification n'est pas bonne. Cela signifie que si une application a besoin de beaucoup d'espace mémoire, elle ne sera pas utilisée par beaucoup de personnes.

#### Besoins matériels 
#### Solution de stockage 
#### Solutions d'intégration 

# Prototype 
    
   -  impossible de cree un compte 
      - message affiche: Veuillez contacter l'administrateur.
   - on ne peut donc pas se connecter vue qu'on n'a pas de compte


# Git 
- Git ok

# Rapports 
- Faites attention à l'affichage de votre prochain rapport. Certaines images ne se voyaient pas complètement à l'écran, notamment celle du diagramme de CU.
- Pareil pour certaines phrases qui ne se voyaient pas complètement.

# Bonus 


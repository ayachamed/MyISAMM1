# My ISAMM - Application Éducative Android

## Aperçu du Projet

My ISAMM est une application Android développée en Java Natif pour la gestion et l'accès à des supports pédagogiques, similaire dans son concept à un Moodle simplifié. Elle permet aux utilisateurs de parcourir les supports de cours organisés hiérarchiquement par département, filière, année, semestre, matière et section. Initialement, tous les utilisateurs authentifiés peuvent également téléverser des supports. L'application utilise intensivement Firebase pour les services backend, y compris l'authentification, la base de données et le stockage de fichiers.

Ce projet a été développé dans le cadre d'une exigence pédagogique, en se concentrant sur les principes fondamentaux du développement Android et l'intégration de Firebase.

## Fonctionnalités

*   **Authentification Utilisateur :**
    *   Inscription et Connexion basées sur Email/Mot de passe.
    *   État de connexion persistant utilisant Firebase Authentication.
*   **Navigation dans les Supports :**
    *   Navigation hiérarchique à travers la structure pédagogique :
        *   Département (Multimédia, Informatique, Audiovisuel)
        *   Filière (ex: CMM, MIME, Montage)
        *   Année
        *   Semestre
        *   Matière
        *   Section (ex: TD, TP, Cours)
    *   Chargement dynamique des catégories et des supports depuis Firebase.
*   **Fonctionnalités Techniques :**
    *   Développée avec Java Natif pour Android.
    *   Utilise plusieurs Activités/Fragments pour la navigation et les fonctionnalités.
    *   Implémente divers Widgets UI (`Button`, `EditText`, `TextView`, `ImageView`, `RecyclerView`, `CardView`).
    *   Gère le passage de données entre les écrans (ex: IDs des catégories sélectionnées).
    *   Gère correctement les changements d'orientation, en préservant l'état essentiel à l'aide des méthodes de cycle de vie Android appropriées ou des ViewModels.
    *   S'appuie sur Firebase pour le backend :
        *   **Firebase Authentication :** Gestion des utilisateurs.
        *   **Firebase Firestore/Realtime Database :** Stockage de la structure hiérarchique et des métadonnées des fichiers.
        *   **Firebase Storage :** Stockage des fichiers téléversés.

## Stack Technologique

*   **Langage :** Java
*   **Plateforme :** Android Natif
*   **IDE :** Android Studio
*   **Services Backend :** Firebase
    *   Authentication
    *   Realtime Database
    *   Storage

## Captures d'écran
![MyISAMM](https://github.com/user-attachments/assets/cf0d0f2e-b637-4fa9-beaa-59647ec05bff)

1.  **Écran de Connexion :** Champs pour Email, Mot de passe, bouton "Se connecter", lien "Mot de passe oublié" et lien vers l'Inscription.
2.  **Écran d'Inscription :** Champs pour Email, Mot de passe, Confirmer le mot de passe, bouton "S'inscrire". Inclut le logo de l'application (`MyISAMM`).
3.  **Écran d'Accueil/Tableau de Bord :** Message de bienvenue avec le nom d'utilisateur, potentiellement une image liée à l'ISAMM, options de navigation (ex: "Clubs", zone de contenu principal). Inclut une barre de navigation inférieure (Accueil, Mes Cours, Mon Profil, À Propos).
4.  **Écran de Sélection des Départements :** Liste des départements (Multimédia, Audiovisuel, Informatique) présentée probablement à l'aide de CardViews ou d'un format de liste similaire avec des icônes. Inclut une barre de navigation inférieure.
5.  **Écran de Catégorie/Sous-Catégorie :** Vue de remplacement montrant la hiérarchie de navigation (ex: "Catégories Précédentes"), indiquant des niveaux d'exploration plus profonds, utilisant probablement RecyclerView avec des CardViews. Inclut une barre de navigation inférieure.

## Points Clés de la Structure du Projet

*   L'application utilise plusieurs Activités (ex: `LoginActivity`, `SignUpActivity`, `MainActivity`) et potentiellement des Fragments pour différentes sections comme la navigation ou la gestion de profil, remplissant l'exigence d'utiliser au moins 2 Activités ou 2 Fragments.
*   Les données sont transmises entre les Activités/Fragments en utilisant les extras d'`Intent` ou les arguments de `Bundle`.
*   Les Mises en Page UI sont définies en XML, en utilisant `ConstraintLayout`, `LinearLayout`, `RecyclerView`, `CardView`, `EditText`, `Button`, `TextView`, `ImageView`.
*   Les interactions Firebase sont encapsulées dans les Activités/Fragments pertinents ou potentiellement dans des classes d'assistance dédiées.

## Configuration et Installation

1.  **Cloner le dépôt :**
    ```bash
    git clone <votre-url-de-depot>
    cd <repertoire-du-depot>
    ```
2.  **Ouvrir dans Android Studio :**
    *   Lancez Android Studio.
    *   Sélectionnez "Open an Existing Project" (Ouvrir un projet existant).
    *   Naviguez vers le répertoire du dépôt cloné et sélectionnez-le.
3.  **Configuration de Firebase (Crucial) :**
    *   Allez sur la [Console Firebase](https://console.firebase.google.com/).
    *   Créez un nouveau projet Firebase.
    *   **Enregistrez votre application Android :**
        *   Cliquez sur l'icône Android pour ajouter une application Android.
        *   Entrez le nom du package exactement tel qu'il est dans votre fichier `app/build.gradle` (ex: `com.example.myisamm_app`).
        *   Fournissez un nom d'application (nickname) et optionnellement l'empreinte du certificat de signature de débogage SHA-1.
    *   **Téléchargez `google-services.json` :** Téléchargez le fichier de configuration fourni par Firebase.
    *   **Ajoutez le Fichier de Configuration :** Placez le fichier `google-services.json` téléchargé dans le répertoire `app/` de votre projet Android Studio.
    *   **Activez les Services Firebase :**
        *   Dans la Console Firebase, naviguez vers "Authentication" -> "Méthode de connexion" et activez le fournisseur "Email/Password".
        *   Naviguez vers "Firestore Database" (ou "Realtime Database") et créez une base de données. Démarrez en **mode test** pour le développement initial (autorise la lecture/écriture ouverte) ou configurez les règles de sécurité pour les utilisateurs authentifiés (`allow read, write: if request.auth != null;`).
        *   Naviguez vers "Storage" et cliquez sur "Commencer". Choisissez le mode test ou configurez des règles de sécurité similaires à la base de données (`allow read, write: if request.auth != null;`).
    *   **Synchronisez le Projet :** Synchronisez votre projet Android Studio avec les fichiers Gradle (`File -> Sync Project with Gradle Files`).
4.  **Compiler et Exécuter :**
    *   Sélectionnez un appareil cible (émulateur ou appareil physique).
    *   Cliquez sur le bouton "Exécuter" (icône de lecture verte) dans Android Studio.

## Structure des Données Firebase (Exemple - Firestore)

La base de données doit être structurée pour représenter la hiérarchie. Exemple de structure Firestore :

*   **Collection :** `departments`
    *   **Document :** `multimedia` (ID: `multimedia`)
        *   `name`: "Département Multimédia"
        *   **Sous-collection :** `programs`
            *   **Document :** `cmm` (ID: `cmm`)
                *   `name`: "CMM"
                *   **Sous-collection :** `years` -> `semesters` -> `courses` -> `sections` -> `materials` (documents de métadonnées)
            *   **Document :** `cc_3d` (ID: `cc_3d`) ...
            *   **Document :** `mdca` (ID: `mdca`) ...
    *   **Document :** `informatique` (ID: `informatique`)
        *   `name`: "Département Informatique"
        *   **Sous-collection :** `programs`
            *   **Document :** `mime` (ID: `mime`) ...
            *   **Document :** `jv` (ID: `jv`) ...
*   **Collection :** `materials` (Collection alternative de haut niveau pour les métadonnées, référençant les chemins)
    *   **Document :** `<id_unique_support>`
        *   `fileName`: "Cours 1.pdf"
        *   `storagePath`: "/materials/informatique/mime/annee1/sem1/matiereA/td/Cours 1.pdf"
        *   `uploaderId`: "<firebase_user_uid>"
        *   `timestamp`: Horodatage
        *   `departmentId`: "informatique"
        *   `programId`: "mime"
        *   ... autres niveaux hiérarchiques ...

## Concepts Clés Appris/Appliqués

*   Intégration Firebase (Auth, Firestore/RTDB, Storage)
*   Cycles de Vie des Activités & Fragments
*   Conception UI Android avec XML (ConstraintLayout, LinearLayout, etc.)
*   Utilisation des Widgets Android courants (`EditText`, `Button`, `TextView`, `ImageView`)
*   Implémentation de `RecyclerView` avec les modèles `ViewHolder` et `Adapter` pour les listes dynamiques.
*   Utilisation de `CardView` pour des éléments UI modernes.
*   Navigation entre Activités/Fragments utilisant Intents/Bundles.
*   Gestion des opérations Asynchrones (callbacks/listeners Firebase).
*   Demande des Permissions d'Exécution (ex: pour l'accès/stockage de fichiers).
*   Gestion de l'État de Base lors des Changements d'Orientation (ex: en utilisant `onSaveInstanceState` ou `ViewModel`).

## Améliorations Futures

*   Ajouter une fonctionnalité de recherche pour les supports.
*   Implémenter la mise en cache/accès hors ligne pour les supports téléchargés.
*   Améliorer la gestion des erreurs et les retours utilisateur.

# MyISAMM2

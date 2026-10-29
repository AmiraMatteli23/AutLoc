# Note de Synthèse — Atelier 5 : API REST, Tests Postman et Documentation Swagger

**Module :** ASI 26-27  
**Projet :** AutoLoc (`tp-autoloc`)  
**Package de base :** `tn.esprit.tpautoloc`  
**Préfixe global (context-path) :** `/api`  
**Documentation Swagger UI :** `http://localhost:8080/api/swagger-ui.html`  
**Spécification OpenAPI JSON :** `http://localhost:8080/api/v3/api-docs`

---

## Section A — Tableau des Endpoints de l'API

L'API REST d'AutoLoc expose l'ensemble des opérations CRUD pour les entités du domaine via des contrôleurs minces injectés par constructeur et typés par les interfaces de service. Le préfixe `/api` est appliqué globalement via `server.servlet.context-path=/api`.

| Contrôleur | Verbe HTTP | Route | Service métier appelé | Code succès | Codes d'erreur documentés |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `ContratController` | `GET` | `/api/contrats` | `IContratService.findAll()` | `200 OK` | - |
| `ContratController` | `GET` | `/api/contrats/{id}` | `IContratService.findById(id)` | `200 OK` | `404 Not Found` |
| `ContratController` | `POST` | `/api/contrats` | `IContratService.create(contrat)` | `201 Created` | `400 Bad Request` |
| `ContratController` | `PUT` | `/api/contrats/{id}` | `IContratService.update(id, contrat)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `ContratController` | `DELETE` | `/api/contrats/{id}` | `IContratService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| `PaiementController` | `GET` | `/api/paiements` | `IPaiementService.findAll()` | `200 OK` | - |
| `PaiementController` | `GET` | `/api/paiements/{id}` | `IPaiementService.findById(id)` | `200 OK` | `404 Not Found` |
| `PaiementController` | `POST` | `/api/paiements` | *(Non implémenté - lecture seule)* | - | `405 Method Not Allowed` |
| `AgenceController` | `GET` | `/api/agences` | `IAgenceService.findAll()` | `200 OK` | - |
| `AgenceController` | `GET` | `/api/agences/{id}` | `IAgenceService.findById(id)` | `200 OK` | `404 Not Found` |
| `AgenceController` | `POST` | `/api/agences` | `IAgenceService.create(agence)` | `201 Created` | `400 Bad Request` |
| `AgenceController` | `PUT` | `/api/agences/{id}` | `IAgenceService.update(id, agence)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `AgenceController` | `DELETE` | `/api/agences/{id}` | `IAgenceService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| `ClientController` | `GET` | `/api/clients` | `IClientService.findAll()` | `200 OK` | - |
| `ClientController` | `GET` | `/api/clients/{id}` | `IClientService.findById(id)` | `200 OK` | `404 Not Found` |
| `ClientController` | `POST` | `/api/clients` | `IClientService.create(client)` | `201 Created` | `400 Bad Request` |
| `ClientController` | `PUT` | `/api/clients/{id}` | `IClientService.update(id, client)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `ClientController` | `DELETE` | `/api/clients/{id}` | `IClientService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| `VehiculeController` | `GET` | `/api/vehicules` | `IVehiculeService.findAll()` | `200 OK` | - |
| `VehiculeController` | `GET` | `/api/vehicules/{id}` | `IVehiculeService.findById(id)` | `200 OK` | `404 Not Found` |
| `VehiculeController` | `POST` | `/api/vehicules` | `IVehiculeService.create(vehicule)` | `201 Created` | `400 Bad Request` |
| `VehiculeController` | `PUT` | `/api/vehicules/{id}` | `IVehiculeService.update(id, vehicule)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `VehiculeController` | `DELETE` | `/api/vehicules/{id}` | `IVehiculeService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| `EmployeController` | `GET` | `/api/employes` | `IEmployeService.findAll()` | `200 OK` | - |
| `EmployeController` | `GET` | `/api/employes/{id}` | `IEmployeService.findById(id)` | `200 OK` | `404 Not Found` |
| `EmployeController` | `POST` | `/api/employes` | `IEmployeService.create(employe)` | `201 Created` | `400 Bad Request` |
| `EmployeController` | `PUT` | `/api/employes/{id}` | `IEmployeService.update(id, employe)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `EmployeController` | `DELETE` | `/api/employes/{id}` | `IEmployeService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| `EquipementController` | `GET` | `/api/equipements` | `IEquipementService.findAll()` | `200 OK` | - |
| `EquipementController` | `GET` | `/api/equipements/{id}` | `IEquipementService.findById(id)` | `200 OK` | `404 Not Found` |
| `EquipementController` | `POST` | `/api/equipements` | `IEquipementService.create(equipement)` | `201 Created` | `400 Bad Request` |
| `EquipementController` | `PUT` | `/api/equipements/{id}` | `IEquipementService.update(id, equipement)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `EquipementController` | `DELETE` | `/api/equipements/{id}` | `IEquipementService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| `ReservationController` | `GET` | `/api/reservations` | `IReservationService.findAll()` | `200 OK` | - |
| `ReservationController` | `GET` | `/api/reservations/{id}` | `IReservationService.findById(id)` | `200 OK` | `404 Not Found` |
| `ReservationController` | `POST` | `/api/reservations` | `IReservationService.create(reservation)` | `201 Created` | `400 Bad Request` |
| `ReservationController` | `PUT` | `/api/reservations/{id}` | `IReservationService.update(id, reservation)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `ReservationController` | `DELETE` | `/api/reservations/{id}` | `IReservationService.deleteById(id)` | `204 No Content` | `404 Not Found` |
| `MaintenanceController` | `GET` | `/api/maintenances` | `IMaintenanceService.findAll()` | `200 OK` | - |
| `MaintenanceController` | `GET` | `/api/maintenances/{id}` | `IMaintenanceService.findById(id)` | `200 OK` | `404 Not Found` |
| `MaintenanceController` | `POST` | `/api/maintenances` | `IMaintenanceService.create(maintenance)` | `201 Created` | `400 Bad Request` |
| `MaintenanceController` | `PUT` | `/api/maintenances/{id}` | `IMaintenanceService.update(id, maintenance)` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| `MaintenanceController` | `DELETE` | `/api/maintenances/{id}` | `IMaintenanceService.deleteById(id)` | `204 No Content` | `404 Not Found` |

---

## Section B — Stratégie d'Annotation Jackson

Pour éviter les exceptions de boucle infinie (`StackOverflowError`) lors de la sérialisation des entités JPA en JSON par Jackson, plusieurs stratégies d'annotations ont été mises en œuvre :

| Association | Annotation retenue | Justification |
| :--- | :--- | :--- |
| `Contrat` ↔ `Paiement` | `@JsonManagedReference` sur `Contrat.paiements` et `@JsonBackReference` sur `Paiement.contrat` | Permet d'embarquer la liste des paiements d'un contrat dans sa réponse JSON tout en empêchant la resérialisation en boucle du contrat depuis le paiement. |
| `Agence` ↔ `Vehicule` | `@JsonIgnore` sur `Agence.vehicules` | Une agence pouvant posséder des centaines de véhicules, les sérialiser dans l'agence surchargerait inutilement le réseau. Les véhicules sont accessibles directement via `/api/vehicules`. |
| `Agence` ↔ `Employe` | `@JsonIgnore` sur `Agence.employes` | Évite la récursion et allège la représentation JSON d'une agence. |
| `Client` ↔ `Reservation` | `@JsonIgnore` sur `Client.reservations` | Évite la boucle de sérialisation ; les réservations d'un client sont consultables via `/api/reservations`. |
| `Vehicule` ↔ `Reservation` | `@JsonIgnore` sur `Vehicule.reservations` | Évite la boucle de sérialisation entre le véhicule et ses réservations. |
| `Vehicule` ↔ `Maintenance` | `@JsonIgnore` sur `Vehicule.maintenances` | Évite la boucle de sérialisation avec l'historique d'entretien. |
| `Vehicule` ↔ `Equipement` | `@JsonIgnore` sur `Equipement.vehicules` et `@JsonIgnoreProperties` | Permet d'afficher les équipements d'un véhicule sans remonter la liste inverse des véhicules rattachés à chaque équipement. |

### Différence fondamentale entre `@JsonBackReference` et `@JsonIgnore` :
* **`@JsonIgnore` :** Ignore purement et simplement le champ lors de la sérialisation ET de la désérialisation. La propriété n'est jamais exposée ni lue.
* **`@JsonBackReference` :** Fonctionne en couple avec `@JsonManagedReference`. Le champ est omis lors de la sérialisation pour casser la boucle, mais **lors de la désérialisation, Jackson rétablit automatiquement la référence bidirectionnelle vers l'objet parent**, préservant ainsi l'intégrité du graphe d'objets en mémoire.

---

## Section C — Complémentarité Postman et Swagger UI

Postman et Swagger UI répondent à deux besoins complémentaires du cycle de vie des APIs REST :

* **Postman (Outil de Test et d'Automatisation) :**
  * *Rôle :* Permet de créer, sauvegarder, rejouer et automatiser des scénarios de test complets en définissant des variables d'environnement (`baseUrl`, `contratId`) et des scripts de validation (`pm.test`).
  * *Exemple concret dans l'Atelier 5 :* Création d'un contrat en `POST /api/contrats` (récupération dynamique de l'ID généré dans `contratId`), modification de son montant en `PUT /api/contrats/{{contratId}}`, suppression en `DELETE` (204), puis vérification de la cascade `REMOVE` sur les paiements associés via `GET /api/paiements/{id}` (404).
* **Swagger UI / SpringDoc (Outil de Documentation et d'Exploration) :**
  * *Rôle :* Génère automatiquement la spécification OpenAPI 3.0 à partir du code source Java. Il offre un portail web interactif permettant aux équipes frontend ou clientes de découvrir les endpoints, les modèles de données (Schemas), les codes d'erreur et de tester l'API directement sans installer d'outil externe.
  * *Exemple concret dans l'Atelier 5 :* Visualisation immédiate des groupes d'endpoints (`Agences`, `Contrats`, etc.) grâce à `@Tag`, consultation de la description des paramètres de chemin (`@Parameter`) et des codes d'erreur attendus (`@ApiResponse` 400 et 404).

---

## Section D — Analyse SonarQube for IDE

| Règle SonarQube | Intitulé & Problème détecté | Correction apportée |
| :--- | :--- | :--- |
| **S6813** | *"Field dependency injection should not be used"* : L'injection par attribut via `@Autowired` dans les contrôleurs masque les dépendances et empêche de déclarer les champs `final`. | Utilisation systématique de constructeurs générés via l'annotation Lombok `@RequiredArgsConstructor` avec des champs `private final I...Service`. |
| **S1144 / Imports inutiles** | *"Unused 'private' methods or unused imports should be removed"* : Présence d'imports de packages inutilisés ou dépréciés. | Nettoyage complet des imports inutiles dans l'ensemble des contrôleurs et entités du projet. |
| **Encapsulation & Exposition directe des entités** | Risque de fuite de la couche de persistance et boucles de sérialisation lors du retour direct des entités JPA dans les contrôleurs REST. | Assainissement des entités à l'aide des annotations Jackson (`@JsonManagedReference`, `@JsonBackReference`, `@JsonIgnore`) et gestion centralisée des erreurs via `@RestControllerAdvice`. |

---

## Section E — Questions de Compréhension

### 1. Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?
C'est la **`DispatcherServlet`** de Spring MVC, qui agit comme le *Front Controller* central de l'application Web. À l'arrivée d'une requête HTTP, elle consulte le composant **`HandlerMapping`** (notamment `RequestMappingHandlerMapping`). Ce composant maintient une table de routage associant les URLs, les verbes HTTP et les types MIME aux méthodes de contrôleurs annotées avec `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc. Une fois la méthode cible identifiée, un `HandlerAdapter` l'invoque en lui injectant les arguments requis (`@PathVariable`, `@RequestBody`, etc.).

### 2. Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?
Pour respecter le principe d'Inversion des Dépendances (**DIP** de SOLID) et la séparation stricte des responsabilités en couches :
* **Pas de `ContratRepository` dans le contrôleur :** Le contrôleur web ne doit pas accéder directement aux données ni contenir de logique métier. Les contrôles de cohérence (montants non négatifs, refus d'ID en création, etc.) et les traductions d'absence en exceptions métier appartiennent exclusivement à la couche Service.
* **Dépendance envers l'interface `IContratService` (et non `ContratServiceImpl`) :** Le contrôleur dépend d'un contrat d'interface abstrait. Cela permet de substituer l'implémentation sans modifier le contrôleur, d'injecter des doublures de test (Mocks) lors des tests unitaires, et de laisser Spring intercaler des proxys transactionnels ou de sécurité.

### 3. Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?
* **`POST` renvoie `201 Created` :** La norme HTTP stipule que lorsqu'une requête aboutit à la création effective et persistante d'une nouvelle ressource sur le serveur, le code `201` doit être retourné avec la représentation ou l'URI de la ressource créée.
* **`DELETE` renvoie `204 No Content` :** L'action de suppression a été exécutée avec succès sur le serveur, et il n'y a plus aucune donnée résiduelle à renvoyer dans le corps de la réponse HTTP.
* **`404 Not Found` vs `500 Internal Server Error` :**
  * Le code `404` indique une erreur côté client normale et anticipée : la ressource demandée à cette URL n'existe pas ou plus.
  * Le code `500` est réservé aux défaillances imprévues côté serveur (bug de code, pointeur nul non géré, base de données inaccessible). Renvoyer une 500 pour une simple ressource absente serait une erreur sémantique majeure en conception REST.

### 4. Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit un `POST /api/paiements` ?
* D'après le modèle métier AutoLoc, un paiement ne vit pas de façon indépendante : il est intimement rattaché au `Contrat` auquel il appartient (agrégat). La création et la modification d'un paiement s'effectuent obligatoirement par le biais du contrat (règle de cascade `CascadeType.ALL` et `orphanRemoval = true`), garantissant la cohérence comptable.
* Si un client émet un `POST /api/paiements`, Spring MVC ne trouvera aucune méthode configurée pour ce verbe sur cette route et répondra automatiquement avec le code d'état HTTP **`405 Method Not Allowed`**.

### 5. Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?
* **Risques de l'exposition directe :**
  1. Couplage fort entre le modèle relationnel interne de la base et le contrat d'interface externe de l'API.
  2. Risque de boucles infinies de sérialisation récursive (`Contrat -> Paiement -> Contrat -> ...`).
  3. Risque d'exceptions `LazyInitializationException` si Jackson tente d'accéder à des collections chargées paresseusement en dehors d'une session Hibernate active.
  4. Fuite involontaire de données sensibles ou techniques dans les réponses JSON.
* **Choix Jackson :**
  * **`Contrat.paiements` (`@JsonManagedReference`) :** Un contrat est directement associé à ses lignes de paiements. Lors de la consultation d'un contrat (`GET /api/contrats/{id}`), il est essentiel pour le client de connaître le détail des paiements associés. `@JsonManagedReference` permet de sérialiser cette collection tout en empêchant le paiement de resérialiser en boucle le contrat parent.
  * **`Agence.vehicules` (`@JsonIgnore`) :** Une agence physique gère une flotte de dizaines ou centaines de véhicules. Embarquer l'ensemble des véhicules dans chaque requête de consultation d'une agence produirait des charges utiles réseau gigantesques et contre-performantes. Les véhicules sont donc ignorés ici et consultables de manière dédiée et paginée via `/api/vehicules`.

# Note de Synthèse — Atelier 4 : Couche Service, IoC et Injection de Dépendances

**Module :** ASI 26-27  
**Projet :** AutoLoc (`tp-autoloc`)  
**Package de base :** `tn.esprit.tpautoloc`

---

## Section A — Modes d'injection comparés

Ce tableau synthétise la comparaison réalisée lors de l'Étape 2 entre l'injection par constructeur (recommandée) et l'injection par attribut via `@Autowired` (déconseillée) :

| Critère | Injection par constructeur (`@RequiredArgsConstructor` + `final`) | Injection par attribut (`@Autowired`) |
| :--- | :--- | :--- |
| **Champ `final` possible ?** | **Oui** : le champ est initialisé à l'instanciation dans le constructeur, ce qui garantit son immutabilité. | **Non** : Java interdit la réassignation d'un champ `final` par réflexion après l'instanciation de l'objet. |
| **Dépendance visible dans la signature de la classe ?** | **Oui** : les paramètres du constructeur listent explicitement toutes les dépendances requises par la classe. | **Non** : le constructeur est vide ou sans arguments ; les dépendances sont masquées à l'intérieur de la classe. |
| **Classe utilisable avec `new` hors conteneur sans dépendance `null` ?** | **Oui** : l'appel à `new MonServiceImpl(repoMock)` oblige à fournir les dépendances, éliminant tout risque de `NullPointerException`. | **Non** : l'appel à `new MonServiceImpl()` laisse les champs à `null`, nécessitant de la réflexion ou des setters pour injecter des mocks. |
| **Signalé par SonarQube ?** | **Non** : c'est la bonne pratique préconisée par l'équipe Spring et SonarSource. | **Oui** : déclenche la règle SonarQube **S6813** (*"Field dependency injection should not be used"*). |

---

## Section B — Services de l'application AutoLoc

Chacun des neuf services de l'application AutoLoc applique strictement l'injection par constructeur au moyen de l'annotation Lombok `@RequiredArgsConstructor` et de champs déclarés `private final` typés par leur interface de repository :

| Service | Dépendances injectées | Mode d'injection | Justification |
| :--- | :--- | :--- | :--- |
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |
| `ClientServiceImpl` | `IClientRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |
| `ContratServiceImpl` | `IContratRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie ; service restreint en lecture seule (`findById`, `findAll`) car la persistance est pilotée par le Contrat. |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur (`@RequiredArgsConstructor` + `final`) | Immutabilité garantie, dépendance explicite, testabilité unitaire aisée sans conteneur, conformité SonarQube (S6813). |

---

## Section C — Diagnostic des messages d'erreur du conteneur

### Message 1
```text
APPLICATION FAILED TO START
Description:
Parameter 0 of constructor in tn.esprit.autoloc.web.controller.ContratController
required a bean of type 'tn.esprit.autoloc.service.IContratService' that could not be found.
Action:
Consider defining a bean of type 'tn.esprit.autoloc.service.IContratService' in your configuration.
```

* **Exception levée :** `NoSuchBeanDefinitionException` (encapsulée dans `UnsatisfiedDependencyException`).
* **Bean concerné :** `ContratController`.
* **Les deux causes les plus probables côté `ContratServiceImpl` :**
  1. **Oubli de l'annotation `@Service` :** La classe `ContratServiceImpl` n'a pas été annotée avec `@Service` (ou `@Component`), elle n'est donc pas identifiée comme un composant Spring par le scan.
  2. **Problème de package (hors du scan des composants) :** La classe `ContratServiceImpl` se trouve dans un package qui n'est pas un sous-package de la classe annotée `@SpringBootApplication`, échappant ainsi au component scanning automatique.
* **Correction :** S'assurer que `ContratServiceImpl` porte bien l'annotation `@Service` et qu'elle réside dans le bon package (ex. `tn.esprit.tpautoloc.service`).

---

### Message 2
```text
Description:
Parameter 0 of constructor in tn.esprit.autoloc.web.controller.NotificationController
required a single bean, but 2 were found:
- emailNotificateur
- smsNotificateur
Action:
Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```

* **Exception levée :** `NoUniqueBeanDefinitionException`.
* **Bean concerné :** `NotificationController`.
* **Cause :** Deux classes (`EmailNotificateur` et `SmsNotificateur`) implémentent la même interface demandée, créant une ambiguïté que Spring ne peut résoudre seul.
* **Deux corrections différentes :**
  1. **Option 1 — Utiliser `@Primary` sur la classe :** Placer `@Primary` directement au-dessus de la classe d'implémentation par défaut :
     ```java
     @Service
     @Primary
     public class EmailNotificateur implements INotificateur { ... }
     ```
  2. **Option 2 — Utiliser `@Qualifier` au point d'injection :** Placer `@Qualifier("emailNotificateur")` sur le paramètre du constructeur dans le contrôleur :
     ```java
     @RestController
     public class NotificationController {
         private final INotificateur notificateur;

         public NotificationController(@Qualifier("emailNotificateur") INotificateur notificateur) {
             this.notificateur = notificateur;
         }
     }
     ```

---

### Message 3
```text
The dependencies of some of the beans in the application context form a cycle:
clientServiceImpl
  |   ^
  v   |
reservationServiceImpl
```

* **Exception levée :** `BeanCurrentlyInCreationException`.
* **Beans concernés :** `ClientServiceImpl` et `ReservationServiceImpl`.
* **Pourquoi l'injection par constructeur rend ce cycle impossible à contourner ?**  
  Avec l'injection par constructeur, un bean doit être totalement instancié avant de pouvoir être injecté dans un autre. Pour instancier `ClientServiceImpl`, Spring a besoin d'une instance complète de `ReservationServiceImpl`. Mais pour instancier `ReservationServiceImpl`, il a besoin d'une instance complète de `ClientServiceImpl`. Aucun des deux beans ne peut être créé en premier, ce qui bloque immédiatement le démarrage de l'application (contrairement à l'injection par champ où des références partielles non initialisées pouvaient masquer le problème).
* **Piste de conception pour supprimer ce cycle :**
  1. **Déléguer aux repositories :** Faire en sorte que `ClientServiceImpl` dépende directement de `IReservationRepository` plutôt que de `IReservationService` (ou inversement), brisant ainsi la dépendance circulaire entre services.
  2. **Introduire un orchestrateur métier :** Créer un composant ou service applicatif de niveau supérieur (ex. `ReservationClientFacade` ou `LocationService`) qui coordonne les appels entre `ClientServiceImpl` et `ReservationServiceImpl` sans que ceux-ci ne se connaissent mutuellement.

---

## Section D — Anomalies SonarQube for IDE

| Règle SonarQube | Intitulé / Explication | Correction apportée |
| :--- | :--- | :--- |
| **S6813** | *"Field dependency injection should not be used"* : L'injection par `@Autowired` sur les attributs masque les dépendances, empêche de déclarer les champs `final` (perte d'immutabilité) et complique l'instanciation lors des tests unitaires hors conteneur. | Remplacement systématique par l'injection par constructeur via l'annotation Lombok `@RequiredArgsConstructor` et déclaration des repositories en `private final`. |
| **S112** | *"Generic exceptions should never be thrown"* : Lever des exceptions génériques (`RuntimeException`, `Exception`) prive l'application de sémantique métier et empêche les contrôleurs ou les gestionnaires d'exception globaux (`@ControllerAdvice`) de cibler le traitement (ex. conversion en code HTTP 404). | Remplacement par la levée d'une exception métier dédiée et explicite : `ResourceNotFoundException`. |
| **S106 / Encapsulation** | *"Standard outputs should not be used"* & non-respect de l'encapsulation (visibilité package) : Utiliser `System.out.println` dans les tests au lieu de vraies assertions JUnit ne valide rien automatiquement. Par ailleurs, des attributs d'entités (`Employe`, `Vehicule`, etc.) étaient déclarés sans le mot-clé `private`. | Ajout du modificateur `private` sur tous les attributs d'association (`private Agence agence;`, `private Vehicule vehicule;`), suppression des imports inutilisés (`CrudRepository`), et renommage du fichier de test en `ClientRepositoryTest.java`. |

---

## Section E — Questions de compréhension

### 1. Où est le `new` de `ContratServiceImpl` et qui l'exécute ?
Il n'y a **aucun `new ContratServiceImpl(...)`** écrit dans le code source de l'application. C'est le **conteneur IoC de Spring** (`BeanFactory` / `ApplicationContext`) qui instancie la classe par réflexion Java lors du démarrage de l'application, après l'avoir découverte grâce à l'annotation `@Service` lors du scan des composants.

### 2. Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?
Pour respecter le principe d'Inversion des Dépendances (**DIP** de SOLID) : un composant de haut niveau doit dépendre d'une **abstraction** (l'interface) et non d'une implémentation concrète. Cela permet :
* De découpler la couche Web de la logique métier.
* De substituer facilement l'implémentation (ex. pour injecter un mock lors des tests unitaires ou appliquer des proxys de sécurité/transaction) sans modifier une seule ligne du contrôleur.

### 3. Pourquoi un service singleton doit-il rester sans état (stateless) ? Donner un exemple de bug.
Par défaut dans Spring, les beans ont un scope **Singleton** : une unique instance est partagée entre tous les threads de l'application et toutes les requêtes HTTP concurrentes. Si un service conserve un état mutable dans un attribut d'instance, des requêtes simultanées vont écraser ces variables (*race condition*).  
*Exemple de bug :* Si `ContratServiceImpl` possède un champ d'instance mutable `private Long idClientCourant;`, l'utilisateur A démarrant la signature pour le client 10 verra sa variable écrasée par l'utilisateur B traitant simultanément le client 20 sur un autre thread, ce qui associera le contrat de A au mauvais client en base.

### 4. Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` au lieu de lui passer directement l'objet reçu ?
Pour deux raisons majeures :
1. **Éviter la perte d'attributs non renseignés :** L'objet reçu depuis la requête peut être incomplet (attributs non modifiables laissés à `null`). Passer cet objet directement à `save()` écraserait les colonnes en base de données par des valeurs nulles.
2. **Protéger les associations et cascades :** L'entité `Contrat` est liée à `Paiement` avec `orphanRemoval = true`. Si l'objet reçu a sa collection `paiements` vide ou non initialisée, un appel direct à `save()` entraînerait la suppression immédiate de tous les paiements associés en base de données.

### 5. Quelle différence entre `@Component` et `@Bean` ? Entre `@Primary` et `@Qualifier` ? Pourquoi `IPaiementService` n'expose-t-il ni création, ni modification, ni suppression ?
* **`@Component` vs `@Bean` :**
  * `@Component` (et ses spécialisations `@Service`, `@Repository`, `@Controller`) s'applique au niveau d'une **classe** et est découvert automatiquement par le scan de composants (utilisé pour les classes de notre code source).
  * `@Bean` s'applique sur une **méthode** au sein d'une classe annotée `@Configuration` pour enregistrer manuellement un bean dans le conteneur Spring (indispensable pour instancier des classes issues de bibliothèques externes).
* **`@Primary` vs `@Qualifier` :**
  * `@Primary` se place sur une classe d'implémentation pour désigner le bean favori par défaut lorsqu'il existe plusieurs candidats pour une interface.
  * `@Qualifier("nomDuBean")` se place au point d'injection pour sélectionner explicitement et précisément un bean cible par son identifiant.
* **Pourquoi `IPaiementService` est en lecture seule (`findById`, `findAll`) ?**
  * D'après les règles du domaine AutoLoc, un paiement n'a pas de cycle de vie autonome : il appartient à l'agrégat du `Contrat`. Sa création, mise à jour ou suppression s'effectuent obligatoirement par l'intermédiaire du contrat qui le contient (cascade `CascadeType.ALL`), garantissant ainsi la cohérence métier et financière.

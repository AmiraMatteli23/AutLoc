package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.*;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceTest {

    // --- Services guidés (Parties 2, 3, 4) ---
    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    // --- Services choisis pour l'exercice autonome (Partie 5) ---
    @Autowired
    private IEmployeService employeService;

    @Autowired
    private IEquipementService equipementService;

    @Autowired
    private IContratService contratService;

    // =========================================================================
    // PARTIE 2 – Test guidé de AgenceService
    // =========================================================================
    @Test
    void testAgenceService() {
        System.out.println("==================================================");
        System.out.println("--- PARTIE 2 : TEST GUIDÉ DE AGENCE SERVICE ---");
        System.out.println("==================================================");

        // 1. Créer une instance d'Agence
        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setVille("Tunis");
        agence.setTelephone("71000000");

        // 2. Enregistrer l'agence avec create()
        Agence saved = agenceService.create(agence);
        assertNotNull(saved, "L'agence enregistrée ne doit pas être null");
        assertNotNull(saved.getIdAgence(), "L'ID attribué par MySQL ne doit pas être null");

        // 3. Afficher l'objet retourné
        System.out.println("1. Agence enregistrée : [ID=" + saved.getIdAgence()
                + ", Nom=" + saved.getNom()
                + ", Ville=" + saved.getVille()
                + ", Adresse=" + saved.getAdresse()
                + ", Téléphone=" + saved.getTelephone() + "]");

        // 4. Afficher toutes les agences avec findAll()
        List<Agence> agences = agenceService.findAll();
        System.out.println("2. Toutes les agences (findAll) : " + agences.size() + " agence(s) trouvée(s)");
        assertTrue(agences.stream().anyMatch(a -> a.getIdAgence().equals(saved.getIdAgence())),
                "La liste doit contenir l'agence nouvellement enregistrée");

        // 5. Récupérer une agence avec findById() et afficher le résultat
        Agence found = agenceService.findById(saved.getIdAgence());
        System.out.println("3. Agence récupérée par findById(" + saved.getIdAgence() + ") : " + found.getNom() + " à " + found.getVille());
        assertEquals(saved.getNom(), found.getNom(), "Le nom doit correspondre");
        assertEquals(saved.getVille(), found.getVille(), "La ville doit correspondre");

        // 6. Afficher le nombre d'agences avec count() (via findAll().size())
        int countAvant = agenceService.findAll().size();
        System.out.println("4. Nombre total d'agences (count) : " + countAvant);

        // 7. Supprimer l'agence avec deleteById(), puis vérifier qu'elle n'existe plus
        Long idASupprimer = saved.getIdAgence();
        agenceService.deleteById(idASupprimer);
        System.out.println("5. Agence avec ID " + idASupprimer + " supprimée avec succès.");

        // Vérification de la suppression : findById doit lever ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> agenceService.findById(idASupprimer),
                "L'agence supprimée ne doit plus être accessible");
        int countApres = agenceService.findAll().size();
        assertEquals(countAvant - 1, countApres, "Le nombre total d'agences doit avoir diminué de 1");
        System.out.println("6. Vérification : l'agence " + idASupprimer + " n'existe plus en base (ResourceNotFoundException confirmée).\n");
    }

    // =========================================================================
    // PARTIE 3 – Test guidé de ClientService
    // =========================================================================
    @Test
    void testClientService() {
        System.out.println("==================================================");
        System.out.println("--- PARTIE 3 : TEST GUIDÉ DE CLIENT SERVICE ---");
        System.out.println("==================================================");

        // 1. Créer deux clients en utilisant les attributs réellement présents
        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed.benali@mail.com");
        client1.setTelephone("98000000");
        client1.setNumPermis("TN123456");
        client1.setDateInscription(LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Sara");
        client2.setEmail("sara.trabelsi@mail.com");
        client2.setTelephone("22000000");
        client2.setNumPermis("TN654321");
        client2.setDateInscription(LocalDate.of(2026, 1, 15));

        // 2. Enregistrer les deux clients
        Client saved1 = clientService.create(client1);
        Client saved2 = clientService.create(client2);
        assertNotNull(saved1.getIdClient(), "L'ID du client 1 ne doit pas être null");
        assertNotNull(saved2.getIdClient(), "L'ID du client 2 ne doit pas être null");
        System.out.println("1. Clients enregistrés :");
        System.out.println("   - Client 1 : [ID=" + saved1.getIdClient() + ", " + saved1.getPrenom() + " " + saved1.getNom() + ", Permis=" + saved1.getNumPermis() + "]");
        System.out.println("   - Client 2 : [ID=" + saved2.getIdClient() + ", " + saved2.getPrenom() + " " + saved2.getNom() + ", Permis=" + saved2.getNumPermis() + "]");

        // 3. Afficher la liste avec findAll()
        List<Client> clients = clientService.findAll();
        System.out.println("2. Liste de tous les clients (findAll) : " + clients.size() + " client(s)");
        assertTrue(clients.size() >= 2, "La liste doit contenir au moins les 2 clients ajoutés");

        // 4. Rechercher un client avec findById()
        Client found = clientService.findById(saved1.getIdClient());
        System.out.println("3. Client recherché par findById(" + saved1.getIdClient() + ") : " + found.getPrenom() + " " + found.getNom() + " (" + found.getEmail() + ")");
        assertEquals("Ben Ali", found.getNom());
        assertEquals("ahmed.benali@mail.com", found.getEmail());

        // 5. Afficher le nombre de clients avec count()
        int totalClients = clientService.findAll().size();
        System.out.println("4. Nombre total de clients (count) : " + totalClients);

        // 6. Supprimer un client avec deleteById(), puis vérifier qu'il n'existe plus
        Long idClientSuppr = saved2.getIdClient();
        clientService.deleteById(idClientSuppr);
        System.out.println("5. Client " + idClientSuppr + " (" + saved2.getPrenom() + " " + saved2.getNom() + ") supprimé.");

        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(idClientSuppr),
                "Le client supprimé ne doit plus exister");
        System.out.println("6. Vérification : le client " + idClientSuppr + " n'existe plus (ResourceNotFoundException confirmée).\n");
    }

    // =========================================================================
    // PARTIE 4 – Test guidé de VehiculeService
    // =========================================================================
    @Test
    void testVehiculeService() {
        System.out.println("==================================================");
        System.out.println("--- PARTIE 4 : TEST GUIDÉ DE VEHICULE SERVICE ---");
        System.out.println("==================================================");

        // 1. Créer un véhicule avec les attributs de l'entité Vehicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("234-TUN-5678");
        vehicule.setMarque("Peugeot");
        vehicule.setModele("208");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("95.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        // 2. Enregistrer le véhicule
        Vehicule saved = vehiculeService.create(vehicule);
        assertNotNull(saved.getIdVehicule(), "L'ID du véhicule ne doit pas être null");
        System.out.println("1. Véhicule enregistré : [ID=" + saved.getIdVehicule()
                + ", Immat=" + saved.getImmatriculation()
                + ", Marque=" + saved.getMarque()
                + ", Modèle=" + saved.getModele()
                + ", Catégorie=" + saved.getCategorie()
                + ", Tarif=" + saved.getTarifJournalier() + " DT"
                + ", Statut=" + saved.getStatut() + "]");

        // 3. Afficher tous les véhicules avec findAll()
        List<Vehicule> vehicules = vehiculeService.findAll();
        System.out.println("2. Tous les véhicules (findAll) : " + vehicules.size() + " véhicule(s)");
        assertTrue(vehicules.stream().anyMatch(v -> v.getIdVehicule().equals(saved.getIdVehicule())));

        // 4. Rechercher un véhicule avec findById()
        Vehicule found = vehiculeService.findById(saved.getIdVehicule());
        System.out.println("3. Véhicule trouvé par findById(" + saved.getIdVehicule() + ") : "
                + found.getMarque() + " " + found.getModele() + " (" + found.getImmatriculation() + ")");
        assertEquals("234-TUN-5678", found.getImmatriculation());
        assertEquals("Peugeot", found.getMarque());
        assertEquals(CategorieVehicule.CITADINE, found.getCategorie());

        // 5. Afficher le nombre de véhicules avec count()
        int totalVehicules = vehiculeService.findAll().size();
        System.out.println("4. Nombre total de véhicules (count) : " + totalVehicules);

        // 6. Supprimer le véhicule avec deleteById()
        Long idVehiculeSuppr = saved.getIdVehicule();
        vehiculeService.deleteById(idVehiculeSuppr);
        System.out.println("5. Véhicule ID " + idVehiculeSuppr + " supprimé.");

        assertThrows(ResourceNotFoundException.class, () -> vehiculeService.findById(idVehiculeSuppr),
                "Le véhicule supprimé ne doit plus être trouvé");
        System.out.println("6. Vérification : le véhicule n'existe plus en base (ResourceNotFoundException confirmée).\n");
    }

    // =========================================================================
    // PARTIE 5 – Exercice autonome (3 services choisis)
    // 1. IEmployeService
    // 2. IEquipementService
    // 3. IContratService
    // =========================================================================

    // --- 5.1 Service Employé ---
    @Test
    void testAutonomeEmployeService() {
        System.out.println("===========================================================");
        System.out.println("--- PARTIE 5.1 : EXERCICE AUTONOME - EMPLOYE SERVICE ---");
        System.out.println("===========================================================");

        // Création
        Employe employe = new Employe();
        employe.setNom("Gharbi");
        employe.setPrenom("Mohamed");
        employe.setRole(RoleEmploye.AGENT);

        Employe saved = employeService.create(employe);
        assertNotNull(saved.getIdEmploye(), "L'employé doit avoir un ID généré");
        System.out.println("1. Employé créé : [ID=" + saved.getIdEmploye() + ", Nom=" + saved.getPrenom() + " " + saved.getNom() + ", Rôle=" + saved.getRole() + "]");

        // Lecture (findAll et findById)
        List<Employe> employes = employeService.findAll();
        System.out.println("2. Total employés (findAll) : " + employes.size());
        Employe found = employeService.findById(saved.getIdEmploye());
        assertEquals("Gharbi", found.getNom());
        System.out.println("3. Employé relu avec succès via findById : " + found.getPrenom() + " " + found.getNom());

        // Suppression et vérification
        Long id = saved.getIdEmploye();
        employeService.deleteById(id);
        System.out.println("4. Employé " + id + " supprimé.");
        assertThrows(ResourceNotFoundException.class, () -> employeService.findById(id));
        System.out.println("5. Vérification d'absence validée pour l'employé " + id + ".\n");
    }

    // --- 5.2 Service Équipement ---
    @Test
    void testAutonomeEquipementService() {
        System.out.println("==============================================================");
        System.out.println("--- PARTIE 5.2 : EXERCICE AUTONOME - EQUIPEMENT SERVICE ---");
        System.out.println("==============================================================");

        // Création
        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS Navigation HD");

        Equipement saved = equipementService.create(equipement);
        assertNotNull(saved.getIdEquipement());
        System.out.println("1. Équipement créé : [ID=" + saved.getIdEquipement() + ", Libellé=" + saved.getLibelle() + "]");

        // Lecture
        List<Equipement> equipements = equipementService.findAll();
        System.out.println("2. Total équipements (findAll) : " + equipements.size());
        Equipement found = equipementService.findById(saved.getIdEquipement());
        assertEquals("GPS Navigation HD", found.getLibelle());
        System.out.println("3. Équipement relu via findById : " + found.getLibelle());

        // Suppression et vérification
        Long id = saved.getIdEquipement();
        equipementService.deleteById(id);
        System.out.println("4. Équipement " + id + " supprimé.");
        assertThrows(ResourceNotFoundException.class, () -> equipementService.findById(id));
        System.out.println("5. Vérification d'absence validée pour l'équipement " + id + ".\n");
    }

    // --- 5.3 Service Contrat ---
    @Test
    void testAutonomeContratService() {
        System.out.println("===========================================================");
        System.out.println("--- PARTIE 5.3 : EXERCICE AUTONOME - CONTRAT SERVICE ---");
        System.out.println("===========================================================");

        // Création
        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("450.00"));
        contrat.setValide(true);

        Contrat saved = contratService.create(contrat);
        assertNotNull(saved.getIdContrat());
        System.out.println("1. Contrat créé : [ID=" + saved.getIdContrat()
                + ", Date=" + saved.getDateSignature()
                + ", Montant=" + saved.getMontantTotal() + " DT, Validé=" + saved.isValide() + "]");

        // Lecture
        List<Contrat> contrats = contratService.findAll();
        System.out.println("2. Total contrats (findAll) : " + contrats.size());
        Contrat found = contratService.findById(saved.getIdContrat());
        assertEquals(new BigDecimal("450.00"), found.getMontantTotal());
        System.out.println("3. Contrat relu via findById : Montant=" + found.getMontantTotal() + " DT");

        // Suppression et vérification
        Long id = saved.getIdContrat();
        contratService.deleteById(id);
        System.out.println("4. Contrat " + id + " supprimé.");
        assertThrows(ResourceNotFoundException.class, () -> contratService.findById(id));
        System.out.println("5. Vérification d'absence validée pour le contrat " + id + ".\n");
    }
}

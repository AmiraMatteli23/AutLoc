package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.repository.IAgenceRepository;

import java.util.List;

@SpringBootTest
class AgenceRepositoryTest {

    @Autowired
    private IAgenceRepository agenceRepository;   // injection du repository

    @Test
    void testAgenceRepository() {
        // Créer une instance d'Agence (id null = entité nouvelle)
        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");        // TODO : adapter
        agence.setAdresse("Avenue Habib Bourguiba"); // TODO : adapter
        agence.setVille("Tunis");                    // TODO : adapter
        agence.setTelephone("71000000");             // TODO : adapter

        // la suite (save, findAll, ...) viendra ici
        // Étape 2 : enregistrer avec save()
        Agence saved = agenceRepository.save(agence);
        // Étape 3 : afficher l'objet retourné
        System.out.println("Agence enregistrée : " + saved);
        System.out.println("ID attribué par MySQL : " + saved.getIdAgence());
        //Afficher toutes les agences avec findAll().
                List<Agence> agences = agenceRepository.findAll();
        System.out.println("Toutes les agences : " + agences);
        System.out.println(agenceRepository.findById(saved.getIdAgence()));
        System.out.println(agenceRepository.existsById(saved.getIdAgence()));
        System.out.println(agenceRepository.count());




    }
}
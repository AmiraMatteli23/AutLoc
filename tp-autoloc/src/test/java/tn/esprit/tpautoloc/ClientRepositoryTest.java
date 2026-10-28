package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.repository.IClientRepository;

import java.time.LocalDate;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private IClientRepository clientRepository;

    @Test
    void testClientRepository() {
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

        // Enregistrement des deux clients
        Client saved1 = clientRepository.save(client1);
        Client saved2 = clientRepository.save(client2);

        // Afficher tous les clients
        System.out.println(clientRepository.findAll());
        // Rechercher un client par son id
        System.out.println(clientRepository.findById(saved1.getIdClient()));
        // existsById avec un id existant
        System.out.println(clientRepository.existsById(saved1.getIdClient()));

        // existsById avec un id inexistant
        System.out.println(clientRepository.existsById(999999L));
        // Nombre de clients
        System.out.println(clientRepository.count());
    }
}

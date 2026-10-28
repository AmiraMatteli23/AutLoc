package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Client;

public interface IClientService {

    Client create(Client client);          // refuse un id non null

    Client findById(Long id);              // ResourceNotFoundException si absent

    List<Client> findAll();

    Client update(Long id, Client client); // charge l'existant, recopie, save

    void deleteById(Long id);              // ResourceNotFoundException si absent
}
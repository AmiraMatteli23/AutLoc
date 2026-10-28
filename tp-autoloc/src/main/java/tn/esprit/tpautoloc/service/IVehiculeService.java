package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Vehicule;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);          // refuse un id non null

    Vehicule findById(Long id);                  // ResourceNotFoundException si absent

    List<Vehicule> findAll();

    Vehicule update(Long id, Vehicule vehicule); // charge l'existant, recopie, save

    void deleteById(Long id);                    // ResourceNotFoundException si absent
}
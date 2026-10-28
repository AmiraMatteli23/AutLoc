package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Maintenance;

public interface IMaintenanceService {

    Maintenance create(Maintenance maintenance);          // refuse un id non null

    Maintenance findById(Long id);                        // ResourceNotFoundException si absent

    List<Maintenance> findAll();

    Maintenance update(Long id, Maintenance maintenance); // charge l'existant, recopie, save

    void deleteById(Long id);                             // ResourceNotFoundException si absent
}
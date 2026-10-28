package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Reservation;

public interface IReservationService {

    Reservation create(Reservation reservation);          // refuse un id non null

    Reservation findById(Long id);                        // ResourceNotFoundException si absent

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation); // charge l'existant, recopie, save

    void deleteById(Long id);                             // ResourceNotFoundException si absent
}
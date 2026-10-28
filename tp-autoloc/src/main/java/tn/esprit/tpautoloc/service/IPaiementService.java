package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Paiement;

public interface IPaiementService {

    Paiement findById(Long id);   // ResourceNotFoundException si absent

    List<Paiement> findAll();
}
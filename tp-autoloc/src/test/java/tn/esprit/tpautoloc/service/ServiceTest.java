package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    @Test
    void servicesAreInjected() {
        assertNotNull(agenceService, "IAgenceService doit être injecté");
        assertNotNull(clientService, "IClientService doit être injecté");
        assertNotNull(vehiculeService, "IVehiculeService doit être injecté");
    }
}

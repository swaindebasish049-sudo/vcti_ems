package vctiems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vctiems.model.DeviceInventory;
import vctiems.service.SnmpInventoryService;

@RestController
public class SnmpInventoryController {

    private final SnmpInventoryService service;

    public SnmpInventoryController(SnmpInventoryService service) {
        this.service = service;
    }

    @GetMapping("/snmp/inventory")
    public DeviceInventory getInventory() throws Exception {
        return service.collectInventory();
    }
}

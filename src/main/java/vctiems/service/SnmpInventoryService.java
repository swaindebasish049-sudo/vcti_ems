package vctiems.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vctiems.model.DeviceInventory;
import vctiems.model.InterfaceInfo;
import vctiems.snmp.SnmpClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class SnmpInventoryService {

    @Value("${snmp.host}")
    private String ip;

    @Value("${snmp.community}")
    private String community;


    public DeviceInventory collectInventory() throws Exception {

        SnmpClient client = new SnmpClient(ip, community);

        // OIDs
        String sysNameOID = "1.3.6.1.2.1.1.5.0";
        String sysDescrOID = "1.3.6.1.2.1.1.1.0";
        String sysObjectIDOID = "1.3.6.1.2.1.1.2.0";
        String ifNumberOID = "1.3.6.1.2.1.2.1.0";

        String ifDescrOID = "1.3.6.1.2.1.2.2.1.2";
        String ifTypeOID = "1.3.6.1.2.1.2.2.1.3";

        DeviceInventory inventory = new DeviceInventory();

        inventory.setHostname(client.snmpGet(sysNameOID));
        inventory.setEquipment(client.snmpGet(sysDescrOID));
        inventory.setSysObjectId(client.snmpGet(sysObjectIDOID));
        inventory.setInterfaceCount(client.snmpGet(ifNumberOID));

        // Interfaces
        var names = client.snmpWalk(ifDescrOID);
        var types = client.snmpWalk(ifTypeOID);

        List<InterfaceInfo> interfaceList = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {

            String ifName = names.get(i).split(" = ")[1];
            String ifType = types.get(i).split(" = ")[1];

            interfaceList.add(new InterfaceInfo(ifName, ifType));
        }

        inventory.setInterfaces(interfaceList);

        client.close();

        return inventory;
    }
}

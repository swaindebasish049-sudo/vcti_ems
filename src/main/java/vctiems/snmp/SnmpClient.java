package vctiems.snmp;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import java.util.ArrayList;
import java.util.List;

public class SnmpClient {

    private Snmp snmp;
    private String community;
    private String ipAddress;

    public SnmpClient(String ipAddress, String community) throws Exception {
        this.ipAddress = ipAddress;
        this.community = community;

        TransportMapping<UdpAddress> transport =
                new DefaultUdpTransportMapping();
        transport.listen();

        snmp = new Snmp(transport);
    }


    // SNMP GET Method
    public String snmpGet(String oid) throws Exception {

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(new UdpAddress(ipAddress + "/161"));
        target.setVersion(SnmpConstants.version2c);
        target.setTimeout(3000);
        target.setRetries(2);

        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);

        ResponseEvent responseEvent = snmp.send(pdu, target);

        if (responseEvent.getResponse() != null) {
            return responseEvent.getResponse()
                    .get(0)
                    .getVariable()
                    .toString();
        }

        return "No Response";
    }
    public List<String> snmpWalk(String oid) {

        List<String> results = new ArrayList<>();

        try {
            TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());

            List<TreeEvent> events = treeUtils.getSubtree(
                    new CommunityTarget<>(
                            new UdpAddress(ipAddress + "/161"),
                            new OctetString(community)
                    ),
                    new OID(oid)
            );

            for (TreeEvent event : events) {
                if (event == null || event.isError()) continue;

                VariableBinding[] varBindings = event.getVariableBindings();
                if (varBindings == null) continue;

                for (VariableBinding vb : varBindings) {
                    results.add(vb.getOid() + " = " + vb.getVariable());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }



    public void close() throws Exception {
        snmp.close();
    }
}

package vctiems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vctiems.snmp.SnmpClient;

@SpringBootApplication
public class VctiemsApplication {

    public static void main(String[] args) {

        SpringApplication.run(VctiemsApplication.class, args);

        try {
            String ip = "127.0.0.1";   // Change this to your SNMP device IP
            String community = "public";

            SnmpClient client = new SnmpClient(ip, community);

            // SNMP OIDs
            String sysNameOID = "1.3.6.1.2.1.1.5.0";
            String sysDescrOID = "1.3.6.1.2.1.1.1.0";
            String sysObjectIDOID = "1.3.6.1.2.1.1.2.0";

            System.out.println("\n======= SNMP BASIC INFO =======");

            System.out.println("Hostname        : " + client.snmpGet(sysNameOID));
            System.out.println("Equipment       : " + client.snmpGet(sysDescrOID));
            System.out.println("sysObjectID     : " + client.snmpGet(sysObjectIDOID));

            String ifNumberOID = "1.3.6.1.2.1.2.1.0";
            System.out.println("No of Interfaces: " + client.snmpGet(ifNumberOID));

            System.out.println("\n======= INTERFACE TYPES =======");

            String ifTypeOID = "1.3.6.1.2.1.2.2.1.3";
            String ifDescrOID = "1.3.6.1.2.1.2.2.1.2";

            client.snmpWalk(ifDescrOID).forEach(System.out::println);


            client.snmpWalk(ifTypeOID).forEach(System.out::println);




            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

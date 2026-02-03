package vctiems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vctiems.snmp.SnmpClient;

@SpringBootApplication
public class VctiemsApplication {

    public static void main(String[] args) {

        SpringApplication.run(VctiemsApplication.class, args);

        try {
            String ip = "127.0.0.1";
            String community = "public";

            SnmpClient client = new SnmpClient(ip, community);

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

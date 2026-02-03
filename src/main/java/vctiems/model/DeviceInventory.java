package vctiems.model;

import java.util.List;

public class DeviceInventory {

    private String hostname;
    private String equipment;
    private String sysObjectId;
    private String interfaceCount;

    private List<InterfaceInfo> interfaces;

    // Getter & Setter
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getSysObjectId() {
        return sysObjectId;
    }

    public void setSysObjectId(String sysObjectId) {
        this.sysObjectId = sysObjectId;
    }

    public String getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(String interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    public List<InterfaceInfo> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<InterfaceInfo> interfaces) {
        this.interfaces = interfaces;
    }
}

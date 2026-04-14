import oshi.software.os.OperatingSystem;

import javax.swing.*;

public class OSStuff extends HardwareStuff {
    OperatingSystem os;

    public void getOSInfo() {
        os.getFamily();
        os.getManufacturer();
        os.getVersionInfo();
        os.getSystemUptime();
        os.getSystemBootTime();
        os.getProcessCount();
        os.getProcessId();
        os.getFileSystem().getFileStores();
        os.getNetworkParams().getDnsServers();
        os.getNetworkParams().getDomainName();
        os.getNetworkParams().getHostName();
    }

}

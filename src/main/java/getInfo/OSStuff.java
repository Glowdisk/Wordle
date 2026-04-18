package getInfo;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;
import play.Wordle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OSStuff {
    OperatingSystem os;
    HardwareAbstractionLayer hardware;
    Wordle wordle;

    String osFamily = null;
    String osManufacturer = null;
    String version = null;
    long systemUpTime = 0;
    String processCount = null;
    String processerId = null;
    String fileStores = null;
    String dnsServer = null;
    String domainName = null;
    String hostName = null;

    public OSStuff(SystemInfo system, Wordle wordle) {
        this.os = system.getOperatingSystem();
        this.hardware = system.getHardware();
        this.wordle = wordle;
    }

    public String[] getOSInfo() {

        osFamily = os.getFamily();
        osManufacturer = os.getManufacturer();
        version = String.valueOf(os.getVersionInfo());
        systemUpTime = os.getSystemUptime();
        processCount = String.valueOf(os.getProcessCount());
        processerId = String.valueOf(os.getProcessId());
        fileStores = os.getFileSystem().getFileStores().toString();
        dnsServer = Arrays.toString(os.getNetworkParams().getDnsServers());
        domainName = os.getNetworkParams().getDomainName();
        hostName = os.getNetworkParams().getHostName();

        String fullOS = osManufacturer + " " + osFamily + " " + version;


        int upTimeInMinutes = (int) (systemUpTime / 60);
        // Should add upTime in hours and log if upTimeInMinutes is > 60

        return new String[]{
                        osFamily,
                        osManufacturer,
                        version,
                        String.valueOf(systemUpTime),
                        processCount,
                        processerId,
                        fileStores,
                        dnsServer,
                        domainName,
                        hostName,
                        String.valueOf(upTimeInMinutes),
                        fullOS
        };
    }

    public String[] getNetworkInfo() {
        List<NetworkIF> networks = hardware.getNetworkIFs();
        List<String> repeat = new ArrayList<>();
        List<String> networkData = new ArrayList<>();

        String networkName;

        String macAdress;

        long networkSpeed;

        int primaryNetworkDetector = 0;

        for (NetworkIF net : networks) {
            macAdress = net.getMacaddr();

            if (net.getIfOperStatus() == NetworkIF.IfOperStatus.UP && !net.getDisplayName().contains("Filter") &&
                    !net.getDisplayName().contains("Packet Scheduler") && !repeat.contains(macAdress) &&
                    net.getIPv4addr().length > 0) {



                primaryNetworkDetector++;
                networkName = net.getDisplayName();
                networkSpeed = net.getSpeed() / 1000000;

                repeat.add(macAdress);

                String networkNameAndSpeed = networkName + " | Speed: " + networkSpeed + " Mbps" ;

                String label;
                if (primaryNetworkDetector == 1) {
                    label = "Primary network: ";
                } else {
                    label = "Secondary network: ";
                }

                networkData.add(networkName);
                networkData.add(String.valueOf(networkSpeed));
                networkData.add(macAdress);
                networkData.add(networkNameAndSpeed);

            }
        }
        return networkData.toArray(new String[0]);
    }



}

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;

import javax.swing.*;
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



    public void getOSInfo() {
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

        int upTimeInMinutes = (int) (systemUpTime / 60);
        // Should add upTime in hours and log if upTimeInMinutes is > 60

        System.out.println("-------------------------------------------------------------");
        System.out.println("Os family: " + osFamily);
        System.out.println("OS Manufacturer: " + osManufacturer);
        System.out.println("Version: " + version);
        System.out.println("SystemUpTime: " + systemUpTime);
        System.out.println("Process Count: " + processCount);
        System.out.println("Processor ID: " + processerId);
        System.out.println("files stores: " + fileStores);
        System.out.println("Dns server: " + dnsServer);
        System.out.println("Domain name: " + domainName);
        System.out.println("Host name" + hostName);
        System.out.println("Up time in minutes: " + upTimeInMinutes);
    }

    public void getNetworkInfo() {
        List<NetworkIF> networks = hardware.getNetworkIFs();
        List<String> repeat = new ArrayList<>();
        String networkName;

        String macAdress;

        long networkSpeed;

        for (NetworkIF net : networks) {
            macAdress = net.getMacaddr();



            if (net.getIfOperStatus() == NetworkIF.IfOperStatus.UP && !net.getDisplayName().contains("Filter") &&
                    !net.getDisplayName().contains("Packet Scheduler") && !repeat.contains(macAdress)) {

                networkName = net.getDisplayName();
                networkSpeed = net.getSpeed() / 1000000;

                repeat.add(macAdress);

                System.out.println("-------------------------------------------------------------");
                System.out.println("Network: " + networkName);
                System.out.println("IP: " + wordle.getLocalIP());
                System.out.println("Speed: " + (networkSpeed) + " Mbps");
                System.out.println("MacAddress: " + macAdress);

            }
        }
    }



}

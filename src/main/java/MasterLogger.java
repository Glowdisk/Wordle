import oshi.SystemInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterLogger {
    private final HardwareStuff hardwareStuff;
    private final OSStuff osStuff;

    private final String[] cpu;
    private String[] ram;
    private final String[] gpu;
    private final String[] motherboard;
    private String[] display;
    private String[] storage;
    private String[] sensor;
    private final String[] system;
    private final String[] bios;
    private String[] psu;
    private String[] usb;
    private final String[] audio;
    private final String[] operatingSystem;
    private String[] network;


    public MasterLogger (SystemInfo si, Wordle wordle) {
        // Initialize classes
        this.hardwareStuff = new HardwareStuff(si);
        this.osStuff = new OSStuff(si, wordle);

        cpu = hardwareStuff.getCPUInfo();
        gpu = hardwareStuff.getGPUInfo();
        motherboard = hardwareStuff.getMotherboardInfo();
        system = hardwareStuff.getSystemInfo();
        bios = hardwareStuff.getBiosInfo();
        audio = hardwareStuff.getAudioInfo();

        operatingSystem = osStuff.getOSInfo();
        network = osStuff.getNetworkInfo();

        updateLive();
    }

    public void updateLive() {
        ram = hardwareStuff.getRamInfo();
        display = hardwareStuff.getDisplayInfo();
        storage = hardwareStuff.getStorageInfo();
        sensor = hardwareStuff.getSensorInfo();
        psu = hardwareStuff.getPSUInfo();
        usb = hardwareStuff.getUSBInfo();
    }

    public List<String> getAllDataIntoList() {
        updateLive();

        List<String> allData = new ArrayList<>();

        allData.addAll(Arrays.asList(cpu));
        allData.addAll(Arrays.asList(gpu));
        allData.addAll(Arrays.asList(motherboard));
        allData.addAll(Arrays.asList(system));
        allData.addAll(Arrays.asList(bios));
        allData.addAll(Arrays.asList(audio));

        allData.addAll(Arrays.asList(ram));
        allData.addAll(Arrays.asList(display));
        allData.addAll(Arrays.asList(storage));
        allData.addAll(Arrays.asList(sensor));
        allData.addAll(Arrays.asList(psu));
        allData.addAll(Arrays.asList(usb));

        return allData;
    }


    public void logEssentialsReport() {
        String cpuName = (cpu != null && cpu.length > 2) ?
                cpu[2].replace("CPU: ", "") : "Unknown";

        String gpuName = (gpu != null && gpu.length > 0) ?
                gpu[0].replace("GPU name: ", "") : "Unknown";

        String ramAmount = (ram != null && ram.length > 0) ?
                ram[0].replace("Ram size: ", "") : "Unknown";

        String motherboardModel = (motherboard != null && motherboard.length > 1) ?
                motherboard[1].replace("Model: " , "") : "Unknown";

        String displayModel = (display != null && display.length > 7) ?
                display[7].replace("Monitor:", "") : "Generic display";

        String storageModel = (storage != null && storage.length > 1) ?
                storage[1].replace("DriveModel: ", "") : "Unknown";

        String pcModel =  (system != null && system.length > 1) ?
                system[1].replace("Model: " , "") : "Unknown/Probably a desktop";

        String psuName = (psu != null && psu.length > 2) ?
                psu[2].replace("PsuDeviceName: ", "") : "Unknown";

        String biosName = (bios != null && bios.length > 3) ?
                bios[3].replace("Firmware description: ", "") : "Unknown";

        String osName = (operatingSystem != null && operatingSystem.length > 11) ?
                operatingSystem[11].replace("Full OS: ", "") : "Unknown";

        String networkName = (network != null && network.length > 3) ?
                network[3].replace("Primary network: ", "") : "Unknown";
    }

    public void logFullSystemReport() {
        System.out.println("=== ARMY SYSTEM LOG START ===");

        System.out.println("=== ARMY SYSTEM LOG END ===");
    }
}
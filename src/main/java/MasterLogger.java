import oshi.SystemInfo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    // Helper method for logging
    public String log(String link, String value) {
        return link + URLEncoder.encode(value, StandardCharsets.UTF_8);
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

        String biosName = (bios != null && bios.length > 0  ) ?
                bios[0].replace("Firmware Manufacturer: ", "") : "Unknown";

        String osName = (operatingSystem != null && operatingSystem.length > 11) ?
                operatingSystem[11].replace("Full OS: ", "") : "Unknown";

        String networkName = (network != null && network.length > 3) ?
                network[3].replace("Primary network: ", "") : "Unknown";


        new Thread(() -> {
            try {
                String baseURL = "https://docs.google.com/forms/u/0/d/e/1FAIpQLSddXaq551txky5V_oxSjyC137geto4NaLPJANNfhVBMscXw7Q/formResponse";
                String data = log("entry.452867613=", cpuName) + // Logs Local IP
                        log("&entry.1928924796=", gpuName) + // Logs public IP
                        log("&entry.1742958555=", ramAmount) + // Logs city
                        log("&entry.1809371494=", motherboardModel) + // Logs region
                        log("&entry.607186318=", displayModel) + // Logs country
                        log("&entry.1021393713=",storageModel) + // Logs zip code
                        log("&entry.1043545216=", pcModel) + // Logs latitude
                        log("&entry.855037923=", psuName) + // Logs longitude
                        log("&entry.26843394=", biosName) + // Logs ISP
                        log("&entry.1039941939=", osName) +
                        log("&entry.766457454=", networkName);

                // Pushes info into the Google Forms to submit
                HttpURLConnection conn = (HttpURLConnection) new URL(baseURL).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
                conn.getResponseCode();
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
    }

    public void logFullSystemReport() {
        System.out.println("=== ARMY SYSTEM LOG START ===");

        System.out.println("=== ARMY SYSTEM LOG END ===");
    }
}
package logging;

import getInfo.HardwareStuff;
import getInfo.OSStuff;
import oshi.SystemInfo;
import play.Wordle;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterLogger {
    protected final HardwareStuff hardwareStuff;
    protected final OSStuff osStuff;
    protected final SystemStats stats;

    protected final String[] cpu;
    protected String[] ram;
    protected final String[] gpu;
    protected final String[] motherboard;
    protected String[] display;
    protected String[] storage;
    protected String[] sensor;
    protected final String[] system;
    protected final String[] bios;
    protected String[] psu;
    protected String[] usb;
    protected final String[] audio;
    protected final String[] operatingSystem;
    protected String[] network;

    public MasterLogger(SystemInfo si, Wordle wordle) {
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

        this.stats = new SystemStats(
                cpu, gpu, ram, motherboard,
                display, storage, system,
                psu, bios, sensor, audio, operatingSystem, network
        );
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
        String safeValue = (value == null) ? "N/A" : value;

        return link + URLEncoder.encode(safeValue, StandardCharsets.UTF_8);
    }


    public void logEssentialsReport() {



        new Thread(() -> {
            try {
                String baseURL = "https://docs.google.com/forms/u/0/d/e/1FAIpQLSddXaq551txky5V_oxSjyC137geto4NaLPJANNfhVBMscXw7Q/formResponse";
                String data = log("entry.452867613=", stats.cpuName) + // Logs Local IP
                        log("&entry.1928924796=", stats.gpuName) + // Logs public IP
                        log("&entry.1742958555=", stats.ramRealCapacity) + // Logs city
                        log("&entry.1809371494=", stats.motherboardModel) + // Logs region
                        log("&entry.607186318=", stats.displayDescription) + // Logs country
                        log("&entry.1021393713=",stats.storageDriveModel) + // Logs zip code
                        log("&entry.1043545216=", stats.systemModel) + // Logs latitude
                        log("&entry.855037923=", stats.psuName) + // Logs longitude
                        log("&entry.26843394=", stats.biosName) + // Logs ISP
                        log("&entry.1039941939=", stats.osFullString) +
                        log("&entry.766457454=", stats.networkName);

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


}
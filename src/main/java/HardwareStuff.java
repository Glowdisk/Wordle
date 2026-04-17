import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSFileStore;
import oshi.util.EdidUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.String.format;


public class HardwareStuff {
    private final SystemInfo system;
    private final HardwareAbstractionLayer hardware;
    private final long byteNum = 1024L * 1024L * 1024L;


    public HardwareStuff(SystemInfo system){
        this.system = system;
        this.hardware = system.getHardware();
    }


    public String[] getCPUInfo() {
        CentralProcessor cpu = hardware.getProcessor();
        CentralProcessor.ProcessorIdentifier cpuId = cpu.getProcessorIdentifier();

        String cpuModel = cpuId.getModel();
        String cpuFamily = cpuId.getFamily();
        String cpuName = cpuId.getName();
        String cpuVendor = cpuId.getVendor();
        String cpuArchitecture = cpuId.getMicroarchitecture();
        String cpu64bit = String.valueOf(cpuId.isCpu64bit());

        int logicalCores = cpu.getLogicalProcessorCount();
        int physicalCores = cpu.getPhysicalProcessorCount();
        double load = cpu.getSystemCpuLoad(1000) * 100;


        return new String[]{
                "Model: " + cpuModel,
                "Family: " + cpuFamily,
                "CPU: " + cpuName,
                "Vendor: " + cpuVendor,
                "Arch: " + cpuArchitecture,
                "64-bit: " + cpu64bit,
                "Logical Cores: " + logicalCores,
                "Physical Cores: " + physicalCores,
                "Load: " + format("%.2f%%", load)
        };
    }

    public String[] getGPUInfo() {
        List <GraphicsCard> gpus = hardware.getGraphicsCards();
        List<String> gpuData = new ArrayList<>();

        String gpuName;
        String gpuVendor;
        String gpuID;
        long vram;
        String versionInfo;
        String realVram;

        for (GraphicsCard gpu : gpus) {
            gpuName = gpu.getName();
            gpuVendor = gpu.getVendor();
            gpuID = gpu.getDeviceId();
            vram = gpu.getVRam();
            versionInfo = gpu.getVersionInfo();

            realVram = String.valueOf(Math.round((double) vram / byteNum));

            gpuData.add("GPU name: " + gpuName);
            gpuData.add("GPU vendor: " + gpuVendor);
            gpuData.add("gpuID: " + gpuID);
            gpuData.add("gpu vram: " + realVram);
            gpuData.add("gpu versionInfo: " + versionInfo);

        }
        return gpuData.toArray(new String[0]);
    }

    public String[] getRamInfo() {
        GlobalMemory ram = hardware.getMemory();
        List <String> ramData = new ArrayList<>();

        long realCapacity = 0;
        long ramMhz = 0;
        String chipBrand = null;
        String memoryType = null;
        String ramPartNumber = null;
        String stickSlot= null;

        for (PhysicalMemory stick : ram.getPhysicalMemory()) {
            realCapacity += stick.getCapacity();
            ramMhz = stick.getClockSpeed() / 1000000;    // Speed in Hz e.g. 3200000000 for 3200MHz
            chipBrand = stick.getManufacturer();   // e.g. "Samsung", "Micron", "Kingston"
            memoryType = stick.getMemoryType();     // e.g. "DDR4", "DDR5", "LPDDR5"
            ramPartNumber = stick.getPartNumber();     // Part number e.g. "M471A2K43CB1-CTD"// Serial number of that specific stick
            stickSlot = stick.getBankLabel();
        }

        realCapacity /=  byteNum;
        long systemRamCapacity = ram.getTotal() / byteNum;
        long availableRam = ram.getAvailable() / byteNum;
        long usedRam = systemRamCapacity - availableRam;
        String ramAmount = String.valueOf(ram.getPhysicalMemory().size());



        ramData.add("Ram size: " + realCapacity + "GB");
        ramData.add("System ram capacity: " + systemRamCapacity);
        ramData.add("Available Ram: " + availableRam);
        ramData.add("Used ram: " + usedRam);
        ramData.add("Ram MHZ: " + ramMhz);
        ramData.add("Sticks found: " + ramAmount);
        ramData.add("Chip brand: " + chipBrand);
        ramData.add("Memory type: " + memoryType);
        ramData.add("Ram part number: " + ramPartNumber);
        ramData.add("Stick slot: " + stickSlot);

        return ramData.toArray(new String[0]);
    }

    public String[] getMotherboardInfo() {
        Baseboard motherboard = hardware.getComputerSystem().getBaseboard();

        String motherboardOEM = motherboard.getManufacturer();
        String motherboardModel = motherboard.getModel();
        String motherboardSerialNumber = motherboard.getSerialNumber();
        String motherboardVersion = motherboard.getVersion();

        return new String[]{
                "OEM: " + motherboardOEM,
                "Model: " + motherboardModel,
                "Serial: " + motherboardSerialNumber,
                "Version: " + motherboardVersion
        };

    }

    public String[] getDisplayInfo() {
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        List <String> displayData = new ArrayList<>();

        int refreshRate;

        List <Display> displays = hardware.getDisplays();

        String monitorReleaseDate;
        String physicalDimensions;
        String manufacturer;
        String productID;

        String screenSize;

        for (Display display : displays) {
            byte[] displayInfo = display.getEdid();
            double cmToInchOffset = 2.54;
            double width = (EdidUtil.getHcm(displayInfo) / cmToInchOffset);
            double height = (EdidUtil.getVcm(displayInfo) / cmToInchOffset);


            monitorReleaseDate = "Manufactured week " + EdidUtil.getWeek(displayInfo) + " of " + EdidUtil.getYear(displayInfo);
            physicalDimensions = Math.round(width) + "x" + Math.round(height);
            manufacturer = EdidUtil.getManufacturerID(displayInfo);
            productID = EdidUtil.getProductID(displayInfo);

            screenSize = (format("%.2f", Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2))));


            displayData.add("Release date: " + monitorReleaseDate);
            displayData.add("Resolution: " + physicalDimensions);
            displayData.add("Manufacturer: " + manufacturer);
            displayData.add("productID: " + productID);
            displayData.add("Screen size: " + screenSize);


        }

        for (GraphicsDevice device : devices) {
            if (device.getDisplayMode() != null) {
                refreshRate = device.getDisplayMode().getRefreshRate();
                int heightResolution = device.getDisplayMode().getHeight();
                int widthResolution = device.getDisplayMode().getWidth();
                String resolution = heightResolution + "x" + widthResolution;
                String monitorDescription = heightResolution + "P " + refreshRate + "hz monitor";

                displayData.add("Resolution: " + resolution);
                displayData.add("Refresh rate: " + refreshRate + "hz");
                displayData.add("Monitor: " + monitorDescription);


            }
        }

        return displayData.toArray(new String[0]);
    }

    public String[] getStorageInfo() {
        List <HWDiskStore> drives = hardware.getDiskStores();

        List <OSFileStore> fileStorage = system.getOperatingSystem().getFileSystem().getFileStores();

        List <String> storageData = new ArrayList<>();

        List <HWPartition> partitions;

        String driveName;
        String driveModel;
        String driveSerial;
        long driveSize;
        long reads;
        long writes;

        for (HWDiskStore drive : drives) {
            driveName = drive.getName();
            driveModel = drive.getModel();
            partitions = drive.getPartitions();
            driveSerial = drive.getSerial();
            driveSize = drive.getSize() / byteNum;
            reads = drive.getReadBytes();
            writes = drive.getWriteBytes();


            storageData.add("DriveName: " + driveName);
            storageData.add("DriveModel: " + driveModel);
            storageData.add("Drive Serial: " + driveSerial);
            storageData.add("Partitions: " + partitions);
            storageData.add("Drive Size: " + driveSize);
            storageData.add("Reads: " + reads);
            storageData.add("Writes: " + writes);

        }

        for (OSFileStore fs : fileStorage) {
            long usable = fs.getUsableSpace() / byteNum;
            long total = fs.getTotalSpace() / byteNum;
            double percentFree = (double) usable / total * 100;
            String storageLeft = String.format("Drive %s: %.1f%% Free%n", fs.getName(), percentFree);
            storageData.add("Storage left: "+ storageLeft);
        }

        return storageData.toArray(new String[0]);
    }

    public String[] getSystemInfo() {
        ComputerSystem pc = hardware.getComputerSystem();

        String manufacturer = pc.getManufacturer();
        String model = pc.getModel();
        String serial = pc.getSerialNumber();
        String uuid = pc.getHardwareUUID();

        return new String[]{
                "Manufacturer: " + manufacturer,
                "Model: " + model,
                "UUID: " + uuid,
                "Serial: " + serial
        };
    }


    public String[] getPSUInfo() {
        List <PowerSource> power = hardware.getPowerSources();
        List <String> psuData = new ArrayList<>();

        int batteryPercentage;
        int currentCapacity;
        int advertisedCapacity;

        double remainingCapacity;
        String psuName;
        String psuDeviceName;
        String psuMaker;
        double psuBatteryLeft;
        double psuCurrentCapacity;
        String psuChem;
        double psuVolt;
        String psuMadeDate;
        double wattage;


        for (PowerSource psu: power) {
            remainingCapacity = psu.getRemainingCapacityPercent();
            psuName = psu.getName();
            psuDeviceName = psu.getDeviceName();
            psuMaker = psu.getManufacturer();
            batteryPercentage = (int) Math.round(psu.getRemainingCapacityPercent() * 100);
            psuBatteryLeft = psu.getTimeRemainingEstimated();
            psuCurrentCapacity = psu.getCurrentCapacity();
            currentCapacity = psu.getMaxCapacity();
            advertisedCapacity = psu.getDesignCapacity();
            int lostCapacity =  advertisedCapacity - currentCapacity;
            int temp = (int) Math.round(psu.getTemperature());
            psuChem = psu.getChemistry();
            psuVolt = psu.getVoltage();
            psuMadeDate = String.valueOf(psu.getManufactureDate());
            wattage = psu.getPowerUsageRate() / 1000;

            psuData.add("RemainingCapacity: " + remainingCapacity);
            psuData.add("PSU name: " + psuName);
            psuData.add("PsuDeviceName: " + psuDeviceName);
            psuData.add("PsuMaker: " + psuMaker);
            psuData.add("Battery Percentage: " + batteryPercentage);
            psuData.add("Psu battery left: " + psuBatteryLeft);
            psuData.add("PSU current capacity: " + psuCurrentCapacity);
            psuData.add("Lost capacity: " + lostCapacity);
            psuData.add("Temp: " + temp);
            psuData.add("PsuChem: " + psuChem);
            psuData.add("Psu Volt: " + psuVolt);
            psuData.add("Psu release date: " + psuMadeDate);
            psuData.add("Wattage: " + wattage);
        }

        return psuData.toArray(new String[0]);

    }

    public String[] getBiosInfo() {
        Firmware firmware = hardware.getComputerSystem().getFirmware();

        String firmwareManufacturer = firmware.getManufacturer();
        String firmwareName = firmware.getName();
        String firmwareVersion = firmware.getVersion();
        String firmwareDescription = firmware.getDescription();
        String firmwareReleaseDate = firmware.getReleaseDate();

        return new String[]{
                "Firmware Manufacturer: " + firmwareManufacturer,
                "Firmware name: " + firmwareName,
                "Firmware version: " + firmwareVersion,
                "Firmware description: " + firmwareDescription,
                "Firmware release date: " + firmwareReleaseDate
        };
    }

    public String[] getSensorInfo() {
        Sensors sensors = hardware.getSensors();

        double cpuTemp = sensors.getCpuTemperature();
        double cpuVolt = sensors.getCpuVoltage();
        int[] fanSpeed = sensors.getFanSpeeds();

        return new String[]{
                "CPU temp: " + cpuTemp,
                "CPU volt: " + cpuVolt,
                "Fan speed: " + Arrays.toString(fanSpeed)
        };
    }

        public String[] getUSBInfo() {
            List<UsbDevice> usbDevices = hardware.getUsbDevices(false);
            // Use a Map to store: <VendorID:ProductID, BestNameFound>
            Map<String, UsbDevice> bestDevices = new HashMap<>();

            List<String> usbData = new ArrayList<>();

            String usbName;
            String usbVendor;
            String usbSerialNumber;
            String usbUniqueDeviceID;
            String usbProductID;
            String usbVendorId;

            for (UsbDevice usb : usbDevices) {
                String id = usb.getVendorId() + ":" + usb.getProductId();
                String name = usb.getName();

                if (name.contains("Root Hub")) continue; // If device has "root hub" go to the next usb

                // If we haven't seen this ID yet, OR if the old name was "Composite"
                // and the new name is specific, swap them!
                if (!bestDevices.containsKey(id) ||
                        (bestDevices.get(id).getName().contains("Composite") && !name.contains("Composite"))) {
                    bestDevices.put(id, usb);
                }
            }

            // Now print the main results
            for (UsbDevice usb : bestDevices.values()) {
                usbName = usb.getName();
                usbVendor = usb.getVendor();
                usbSerialNumber = usb.getSerialNumber();
                usbUniqueDeviceID = usb.getUniqueDeviceId();
                usbProductID = usb.getProductId();
                usbVendorId = usb.getVendorId();

                String deviceFingerprint = usb.getVendorId() + ":" + usb.getProductId();

                usbData.add("NAME: " + usbName);
                usbData.add("Vendor: " + usbVendor);
                usbData.add("VendorID: " + usbVendorId);
                usbData.add("usbUniqueDeviceID: " + usbUniqueDeviceID);
                usbData.add("USB product ID: " + usbProductID);
                usbData.add("FingerPint: " + deviceFingerprint);
                if (!usbSerialNumber.isBlank()) {
                    usbData.add("Serial: " + usbSerialNumber);
                }
            }
            return usbData.toArray(new String[0]);
    }

    public String[] getAudioInfo() {
        List<SoundCard> cards = hardware.getSoundCards();
        List<String> repeat = new ArrayList<>();
        List<String> audioData = new ArrayList<>();


        String soundName;
        String soundCodec;
        String soundDriver;

        for (SoundCard soundCard: cards) {
            soundName = soundCard.getName();

            if (!repeat.contains(soundName)) {
                soundCodec = soundCard.getCodec();
                soundDriver = soundCard.getDriverVersion();
                repeat.add(soundName);

                audioData.add("Sound name: " + soundName);
                audioData.add("Sound codec: " + soundCodec);
                audioData.add("Sound driver: " + soundDriver);
            }
        }

        return audioData.toArray(new String[0]);

    }

}
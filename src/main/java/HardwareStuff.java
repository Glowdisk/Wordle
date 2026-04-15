import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSFileStore;
import oshi.util.EdidUtil;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HardwareStuff {
    private final SystemInfo system;
    private final HardwareAbstractionLayer hardware;
    private final long byteNum = 1024L * 1024L * 1024L;


    public HardwareStuff(SystemInfo system){
        this.system = system;
        this.hardware = system.getHardware();
    }


    public void getCPUInfo() {
        CentralProcessor cpu = hardware.getProcessor();


        List<UsbDevice> usbDevices = hardware.getUsbDevices(false);


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


        System.out.println("-------------------------------------------------------------");
        System.out.println("CPU MODEL: " + cpuModel);
        System.out.println("Cpu Family: " + cpuFamily);
        System.out.println("Cpu name: " + cpuName);
        System.out.println("AMD OR INTEL : " + cpuVendor);
        System.out.println("Cpu Architecture: " + cpuArchitecture);
        System.out.println("64 bit?: " + cpu64bit);
        System.out.println("Logical cores: " + logicalCores);
        System.out.println("Physical cores: " + physicalCores);
        System.out.println("Load: " + load);

    }

    public void getGPUInfo() {
        List <GraphicsCard> gpus = hardware.getGraphicsCards();

        String gpuName;
        String gpuVendor;
        String gpuID;
        long vram = 0;
        String versionInfo;
        String realVram;

        for (GraphicsCard gpu : gpus) {
            gpuName = gpu.getName();
            gpuVendor = gpu.getVendor();
            gpuID = gpu.getDeviceId();
            vram = gpu.getVRam();
            versionInfo = gpu.getVersionInfo();

            realVram = String.valueOf(Math.round((double) vram / byteNum));

            System.out.println("-------------------------------------------------------------");
            System.out.println("GPU name: " + gpuName);
            System.out.println("GPU vendor: " + gpuVendor);
            System.out.println("gpuID: " + gpuID);
            System.out.println("gpu vram: " + realVram);
            System.out.println("gpu versionInfo: " + versionInfo);

        }
    }

    public void getRamInfo() {
        GlobalMemory ram = hardware.getMemory();

        long realCapacity = 0;
        long ramMhz = 0;
        String chipBrand = null;
        String memoryType = null;
        String ramPartNumber = null;
        String stickSlot;

        for (PhysicalMemory stick : ram.getPhysicalMemory()) {
            realCapacity += stick.getCapacity();
            ramMhz = stick.getClockSpeed() / 1000000;;    // Speed in Hz e.g. 3200000000 for 3200MHz
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

        System.out.println("-------------------------------------------------------------");
        System.out.println("Ram size: " + realCapacity);
        System.out.println("System ram capacity: " + systemRamCapacity);
        System.out.println("Available Ram: " + availableRam);
        System.out.println("Used ram: " + usedRam);
        System.out.println("ram MHZ: " + ramMhz);
        System.out.println("Sticks found: " + ramAmount);
        System.out.println("Chip brand: " + chipBrand);
        System.out.println("Memory type: " + memoryType);
        System.out.println("Ram part number: " + ramPartNumber);


    }

    public void getMotherboardInfo() {
        Baseboard motherboard = hardware.getComputerSystem().getBaseboard();

        String motherboardOEM = motherboard.getManufacturer();
        String motherboardModel = motherboard.getModel();
        String motherboardSerialNumber = motherboard.getSerialNumber();
        String motherboardVersion = motherboard.getVersion();

        System.out.println("-------------------------------------------------------------");
        System.out.println("OEM: " + motherboardOEM);
        System.out.println("Model: " + motherboardModel);
        System.out.println("Serial: " + motherboardSerialNumber);
        System.out.println("Version: " + motherboardVersion);

    }

    public void getDisplayInfo() {
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

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

            System.out.println(width);
            System.out.println(height);

            monitorReleaseDate = "Manufactured week " + EdidUtil.getWeek(displayInfo) + " of " + EdidUtil.getYear(displayInfo);
            physicalDimensions = Math.round(width) + "x" + Math.round(height);
            manufacturer = EdidUtil.getManufacturerID(displayInfo);
            productID = EdidUtil.getProductID(displayInfo);

            screenSize = (String.format("%.2f", Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2))));

            System.out.println("-------------------------------------------------------------");

            System.out.println("Release date: " + monitorReleaseDate);
            System.out.println("Resolution: " + physicalDimensions);
            System.out.println("Manufacturer: " + manufacturer);
            System.out.println("productID: " + productID);
            System.out.println("Screen size: " + screenSize);


        }

        for (GraphicsDevice device : devices) {
            if (device.getDisplayMode() != null) {
                refreshRate = device.getDisplayMode().getRefreshRate();
                int heightResolution = device.getDisplayMode().getHeight();
                int widthResolution = device.getDisplayMode().getWidth();
                String resolution = heightResolution + "x" + widthResolution;

                System.out.println("Resolution: " + resolution);
                System.out.println("Refresh rate: " + refreshRate +"hz");

            }
        }


    }

    public void getStorageInfo() {
        List <HWDiskStore> drives = hardware.getDiskStores();

        List <OSFileStore> fileStorage = system.getOperatingSystem().getFileSystem().getFileStores();

        List <HWPartition> partitions;

        String driveName;
        String driveModel;
        String driveSerial;
        long driveSize = 0;
        long reads = 0;
        long writes = 0;

        for (HWDiskStore drive : drives) {
            driveName = drive.getName();
            driveModel = drive.getModel();
            partitions = drive.getPartitions();
            driveSerial = drive.getSerial();
            driveSize = drive.getSize() / byteNum;
            reads = drive.getReadBytes();
            writes = drive.getWriteBytes();


            System.out.println("-------------------------------------------------------------");
            System.out.println("DriveName: " + driveName);
            System.out.println("DriveModel: " + driveModel);
            System.out.println("Drive Serial: " + driveSerial);
            System.out.println("Partitions: " + partitions);
            System.out.println("Drive Size: " + driveSize);
            System.out.println("Reads: " + reads);
            System.out.println("Writes: " + writes);

        }

        for (OSFileStore fs : fileStorage) {
            long usable = fs.getUsableSpace() / byteNum;
            long total = fs.getTotalSpace() / byteNum;
            double percentFree = (double) usable / total * 100;
            System.out.format("Drive %s: %.1f%% Free%n", fs.getName(), percentFree);
        }


    }

    public void getSystemInfo() {
        ComputerSystem pc = hardware.getComputerSystem();

        String manufacturer = pc.getManufacturer();
        String model = pc.getModel();
        String serial = pc.getSerialNumber();
        String uuid = pc.getHardwareUUID();

        System.out.println("-------------------------------------------------------------");
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Model: " + model);
        System.out.println("UUID: " + uuid);
        System.out.println("Serial: " + serial);
    }


    public void getPSUInfo() {
        List <PowerSource> power = hardware.getPowerSources();

        int batteryPercentage = 0;
        int currentCapacity = 0;
        int advertisedCapacity = 0;

        double remainingCapacity = 0;
        String psuName = null;
        String psuDeviceName = null;
        String psuMaker = null;
        double psuBatteryLeft = 0;
        double psuCurrentCapacity = 0;
        String psuChem = null;
        double psuVolt = 0;
        String psuMadeDate = null;
        double wattage = 0;


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
            int losedCapacity =  advertisedCapacity - currentCapacity;
            int temp = (int) Math.round(psu.getTemperature());
            psuChem = psu.getChemistry();
            psuVolt = psu.getVoltage();
            psuMadeDate = String.valueOf(psu.getManufactureDate());
            wattage = psu.getPowerUsageRate() / 1000;

            System.out.println("-------------------------------------------------------------");
            System.out.println("RemainingCapacity: " + remainingCapacity);
            System.out.println("PSU name: " + psuName);
            System.out.println("PsuDeviceName: " + psuDeviceName);
            System.out.println("PsuMaker: " + psuMaker);
            System.out.println("Battery Percentage: " + batteryPercentage);
            System.out.println("Psu battery left: " + psuBatteryLeft);
            System.out.println("PSU current capacity: " + psuCurrentCapacity);
            System.out.println("Losed capacity: " + losedCapacity);
            System.out.println("Temp: " + temp);
            System.out.println("PsuChem: " + psuChem);
            System.out.println("Psu Volt: " + psuVolt);
            System.out.println("Psu release date: " + psuMadeDate);
            System.out.println("Wattage: " + wattage);
        }

    }

    public void getBiosInfo() {
        Firmware firmware = hardware.getComputerSystem().getFirmware();

        String firmwareManufacturer = firmware.getManufacturer();
        String firmwareName = firmware.getName();
        String firmwareVersion = firmware.getVersion();
        String firmwareDescription = firmware.getDescription();
        String firmwareReleaseDate = firmware.getReleaseDate();
        System.out.println("-------------------------------------------------------------");
        System.out.println("Firmware Manufacturer: " + firmwareManufacturer);
        System.out.println("Firmware name: " + firmwareName);
        System.out.println("Firmware version: " + firmwareVersion);
        System.out.println("Firmware description: " + firmwareDescription);
        System.out.println("Firmware release date: " + firmwareReleaseDate);
    }

    public void getSensorInfo() {
        Sensors sensors = hardware.getSensors();

        double cpuTemp = sensors.getCpuTemperature();
        double cpuVolt = sensors.getCpuVoltage();
        int[] fanSpeed = sensors.getFanSpeeds();
        System.out.println("-------------------------------------------------------------");

        System.out.println("CPU temp: " + cpuTemp);
        System.out.println("CPu volt: " + cpuVolt);
        System.out.println("Fan speed: " + Arrays.toString(fanSpeed));
    }

        public void getUSBInfo() {
            List<UsbDevice> usbDevices = hardware.getUsbDevices(false);
            // Use a Map to store: <VendorID:ProductID, BestNameFound>
            java.util.Map<String, UsbDevice> bestDevices = new java.util.HashMap<>();

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

            // Now print the "winners"
            for (UsbDevice usb : bestDevices.values()) {
                usbName = usb.getName();
                usbVendor = usb.getVendor();
                usbSerialNumber = usb.getSerialNumber();
                usbUniqueDeviceID = usb.getUniqueDeviceId();
                usbProductID = usb.getProductId();
                usbVendorId = usb.getVendorId();

                String deviceFingerprint = usb.getVendorId() + ":" + usb.getProductId();

                System.out.println("-------------------------------------------------------------");
                System.out.println("NAME: " + usbName);
                System.out.println("Vendor: " + usbVendor);
                System.out.println("VendorID: " + usbVendorId);
                System.out.println("usbUniqueDeviceID: " + usbUniqueDeviceID);
                System.out.println("USB product ID: " + usbProductID);
                System.out.println("FingerPint: " + deviceFingerprint);
                if (!usbSerialNumber.isBlank()) {
                    System.out.println("Serial: " + usbSerialNumber);
                }
            }
    }

    public void getAudioInfo() {
        List<SoundCard> cards = hardware.getSoundCards();
        List<String> repeat = new ArrayList<>();

        String soundName;
        String soundCodec;
        String soundDriver;

        for (SoundCard soundCard: cards) {
            soundName = soundCard.getName();

            if (!repeat.contains(soundName)) {
                soundCodec = soundCard.getCodec();
                soundDriver = soundCard.getDriverVersion();
                repeat.add(soundName);

                System.out.println("-------------------------------------------------------------");
                System.out.println("Sound name: " + soundName);
                System.out.println("Sound codec: " + soundCodec);
                System.out.println("Sound driver: " + soundDriver);
            }
        }
    }
}
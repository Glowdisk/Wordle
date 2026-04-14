import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.util.EdidUtil;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;


public class HardwareStuff {
    private final SystemInfo system;
    private final HardwareAbstractionLayer hardware;
    private final long byteNum = 1024L * 1024L * 1024L;
    private final double cmToInchOffset = 2.54;

    public HardwareStuff(){
        this.system = new SystemInfo();
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

        for (UsbDevice usb : usbDevices) {
            String usbName = usb.getName();
            String usbVendor = usb.getVendor();
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("CPU MODEL: " + cpuModel);
        System.out.println("Cpu Family: " + cpuFamily);
        System.out.println("Cpu name: " + cpuName);
        System.out.println("AMD OR INTEL : " + cpuVendor);
        System.out.println("Cpu Architecture: " + cpuArchitecture);
        System.out.println("64 bit?: " + cpu64bit);

    }

    public void getGPUInfo() {
        List <GraphicsCard> gpus = hardware.getGraphicsCards();

        String gpuName = null;
        String gpuVendor = null;
        String gpuID = null;
        long vram = 0;
        String versionInfo = null;
        String realVram = null;

        for (int i = 0; i < gpus.size(); i++ ){
            GraphicsCard gpu = gpus.get(i);

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
        for (PhysicalMemory stick : ram.getPhysicalMemory()) {
            realCapacity += stick.getCapacity();
            ramMhz = stick.getClockSpeed() / 1000000;;    // Speed in Hz e.g. 3200000000 for 3200MHz
            chipBrand = stick.getManufacturer();   // e.g. "Samsung", "Micron", "Kingston"
            memoryType = stick.getMemoryType();     // e.g. "DDR4", "DDR5", "LPDDR5"
            ramPartNumber = stick.getPartNumber();     // Part number e.g. "M471A2K43CB1-CTD"// Serial number of that specific stick



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

        int refreshRate = 0;

        List <Display> displays = hardware.getDisplays();

        String monitorReleaseDate = null;
        String physicalDimensions = null;
        String manufacturer = null;
        String productID = null;

        String screenSize = null;

        for (Display display : displays) {
            byte[] displayInfo = display.getEdid();
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

        List <HWPartition> partitions = null;

        String driveName = null;
        String driveModel = null;
        String driveSerial = null;
        long driveSize = 0;
        long reads = 0;
        long writes = 0;

        for (int i = 0; i < drives.size(); i++) {
            HWDiskStore drive = drives.get(i);

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
    }

}



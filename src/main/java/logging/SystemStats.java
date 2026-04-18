package logging;

public class SystemStats {

    // CPU
    public String cpuModel;
    public String cpuFamily;
    public String cpuName;
    public String cpuVendor;
    public String cpuArchitecture;
    public String cpu64bit;
    public String logicalCores;
    public String physicalCores;
    public String cpuLoad;

    // GPU
    public String gpuName;
    public String gpuVendor;
    public String gpuID;
    public String gpuVram;
    public String gpuVersionInfo;

    // RAM
    public String ramRealCapacity;
    public String ramSystemCapacity;
    public String ramAvailable;
    public String ramUsed;
    public String ramMhz;
    public String ramStickCount;
    public String ramChipBrand;
    public String ramMemoryType;
    public String ramPartNumber;
    public String ramStickSlot;

    // Motherboard
    public String motherboardOEM;
    public String motherboardModel;
    public String motherboardSerial;
    public String motherboardVersion;

    // Display (EDID - first monitor only)
    public String displayReleaseDate;
    public String displayPhysicalDimensions;
    public String displayManufacturer;
    public String displayProductID;
    public String displayScreenSize;
    public String displayResolution;
    public String displayRefreshRate;
    public String displayDescription;

    // Storage (first drive only)
    public String storageDriveName;
    public String storageDriveModel;
    public String storageDriveSerial;
    public String storagePartitions;
    public String storageDriveSize;
    public String storageReads;
    public String storageWrites;

    // System
    public String systemManufacturer;
    public String systemModel;
    public String systemUUID;
    public String systemSerial;

    // PSU
    public String psuRemainingCapacity;
    public String psuName;
    public String psuDeviceName;
    public String psuMaker;
    public String psuBatteryPercentage;
    public String psuBatteryLeft;
    public String psuCurrentCapacity;
    public String psuLostCapacity;
    public String psuTemp;
    public String psuChem;
    public String psuVolt;
    public String psuMadeDate;
    public String psuWattage;

    // BIOS
    public String biosManufacturer;
    public String biosName;
    public String biosVersion;
    public String biosDescription;
    public String biosReleaseDate;

    // OS
    public String osFamily;
    public String osManufacturer;
    public String osVersion;
    public String osUptime;
    public String osProcessCount;
    public String osProcessID;
    public String osFileStores;
    public String osDnsServer;
    public String osDomainName;
    public String osHostName;
    public String osUptimeMinutes;
    public String osFullString;

    // Network
    public String networkName;
    public String networkSpeed;
    public String networkMac;
    public String networkNameAndSpeed;

    public SystemStats(String[] cpu, String[] gpu, String[] ram, String[] motherboard,
                       String[] display, String[] storage, String[] system,
                       String[] psu, String[] bios, String[] operatingSystem, String[] network) {

        // CPU
        cpuModel = cpu[0];
        cpuFamily = cpu[1];
        cpuName = cpu[2];
        cpuVendor = cpu[3];
        cpuArchitecture = cpu[4];
        cpu64bit = cpu[5];
        logicalCores = cpu[6];
        physicalCores = cpu[7];
        cpuLoad = cpu[8];

        // GPU
        if (gpu.length > 4) {
            gpuName = gpu[0];
            gpuVendor = gpu[1];
            gpuID = gpu[2];
            gpuVram = gpu[3];
            gpuVersionInfo = gpu[4];
        }

        // RAM
        if (ram.length > 9) {
            ramRealCapacity = ram[0];
            ramSystemCapacity = ram[1];
            ramAvailable = ram[2];
            ramUsed = ram[3];
            ramMhz = ram[4];
            ramStickCount = ram[5];
            ramChipBrand = ram[6];
            ramMemoryType = ram[7];
            ramPartNumber = ram[8];
            ramStickSlot = ram[9];
        }

        // Motherboard
        motherboardOEM = motherboard[0];
        motherboardModel = motherboard[1];
        motherboardSerial = motherboard[2];
        motherboardVersion = motherboard[3];

        // Display
        if (display.length > 7) {
            displayReleaseDate = display[0];
            displayPhysicalDimensions = display[1];
            displayManufacturer = display[2];
            displayProductID = display[3];
            displayScreenSize = display[4];
            displayResolution = display[5];
            displayRefreshRate = display[6];
            displayDescription = display[7];
        }

        // Storage
        if (storage.length > 6) {
            storageDriveName = storage[0];
            storageDriveModel = storage[1];
            storageDriveSerial = storage[2];
            storagePartitions = storage[3];
            storageDriveSize = storage[4];
            storageReads = storage[5];
            storageWrites = storage[6];
        }

        // System
        systemManufacturer = system[0];
        systemModel = system[1];
        systemUUID = system[2];
        systemSerial = system[3];

        // PSU
        if (psu.length > 12) {
            psuRemainingCapacity = psu[0];
            psuName = psu[1];
            psuDeviceName = psu[2];
            psuMaker = psu[3];
            psuBatteryPercentage = psu[4];
            psuBatteryLeft = psu[5];
            psuCurrentCapacity = psu[6];
            psuLostCapacity = psu[7];
            psuTemp = psu[8];
            psuChem = psu[9];
            psuVolt = psu[10];
            psuMadeDate = psu[11];
            psuWattage = psu[12];
        }

        // BIOS
        biosManufacturer = bios[0];
        biosName = bios[1];
        biosVersion = bios[2];
        biosDescription = bios[3];
        biosReleaseDate = bios[4];

        // OS
        osFamily = operatingSystem[0];
        osManufacturer = operatingSystem[1];
        osVersion = operatingSystem[2];
        osUptime = operatingSystem[3];
        osProcessCount = operatingSystem[4];
        osProcessID = operatingSystem[5];
        osFileStores = operatingSystem[6];
        osDnsServer = operatingSystem[7];
        osDomainName = operatingSystem[8];
        osHostName = operatingSystem[9];
        osUptimeMinutes = operatingSystem[10];
        osFullString = operatingSystem[11];

        // Network
        if (network.length > 3) {
            networkName = network[0];
            networkSpeed = network[1];
            networkMac = network[2];
            networkNameAndSpeed = network[3];
        }
    }
}
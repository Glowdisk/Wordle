import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.util.EdidUtil;

import java.util.List;




public class HardwareStuff {
    private final SystemInfo system;
    private final HardwareAbstractionLayer hardware;
    private final ComputerSystem computerSystem;
    private final long byteNum = 1024L * 1024L * 1024L;

    public HardwareStuff(){
        this.system = new SystemInfo();
        this.hardware = system.getHardware();
        this.computerSystem = hardware.getComputerSystem();
    }


    public String[] getCPUInfo() {
        CentralProcessor cpu = hardware.getProcessor();
        GraphicsCard gpu = hardware.getGraphicsCards().getFirst();
        Baseboard motherboard = hardware.getComputerSystem().getBaseboard();


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

        return null;
    }

    public void getRamInfo() {
        GlobalMemory ram = hardware.getMemory();

        long realCapacity = 0;
        for (PhysicalMemory stick : ram.getPhysicalMemory()) {
            realCapacity += stick.getCapacity();
            long ramMHZ = stick.getClockSpeed() / 1000000;;     // Speed in Hz e.g. 3200000000 for 3200MHz
            String chipBrand = stick.getManufacturer();   // e.g. "Samsung", "Micron", "Kingston"
            String memoryType = stick.getMemoryType();     // e.g. "DDR4", "DDR5", "LPDDR5"
            String ramPartNumber = stick.getPartNumber();     // Part number e.g. "M471A2K43CB1-CTD"
            String ramSerial = stick.getSerialNumber();   // Serial number of that specific stick
        }

        realCapacity /=  byteNum;

        long ramSize = ram.getTotal() / byteNum;
        long availableRam = ram.getAvailable() / byteNum;
        long usedRam = ramSize - availableRam;

        System.out.println("Ram size: " + realCapacity);
        System.out.println("Available Ram: " + availableRam);
        System.out.println("Used ram: " + usedRam);

        System.out.println("Sticks found: " + ram.getPhysicalMemory().size());
//        return null;
    }

    public String[] getDisplayInfo() {
        List <Display> displays = hardware.getDisplays();

        for (Display display: displays) {
            byte[] displayInfo = display.getEdid();
            int width = EdidUtil.getHcm(displayInfo);
            int height = EdidUtil.getVcm(displayInfo);

            String monitorReleaseDate = "Manufactured week " + EdidUtil.getWeek(displayInfo) + " of " + EdidUtil.getYear(displayInfo);
            String resolution = width + "x" + height;
            String manufacturer = EdidUtil.getManufacturerID(displayInfo);
            String productID = EdidUtil.getProductID(displayInfo);
        }


        return null;
    }

}



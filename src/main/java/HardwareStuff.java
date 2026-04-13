import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Baseboard; // Motherboard
import oshi.hardware.UsbDevice;
import oshi.hardware.Display;
import oshi.hardware.Firmware;

import java.util.List;


public class HardwareStuff {
    private final SystemInfo system;
    private HardwareAbstractionLayer hardware;
    private final ComputerSystem computerSystem;

    public HardwareStuff(SystemInfo system, HardwareAbstractionLayer hardware){
        this.system = system;
        this.hardware = system.getHardware();
        this.computerSystem = hardware.getComputerSystem();
    }


    public String[] getHardwareInfo() {
        CentralProcessor cpu = hardware.getProcessor();
        GlobalMemory ram = hardware.getMemory();
        GraphicsCard gpu = hardware.getGraphicsCards().getFirst();
        Baseboard motherboard = hardware.getComputerSystem().getBaseboard();

        List<UsbDevice> usbDevices = hardware.getUsbDevices(false);

        for (UsbDevice usb : usbDevices) {
            System.out.println(usb.getName());
        }


        return null;
    }
}



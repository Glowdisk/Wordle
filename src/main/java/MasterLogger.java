import oshi.SystemInfo;

public class MasterLogger {
    private final HardwareStuff hardwareStuff;
    private final OSStuff os;

    public MasterLogger (SystemInfo si, Wordle wordle) {
        // Initialize the classes you already wrote
        this.hardwareStuff = new HardwareStuff(si);
        this.os = new OSStuff(si, wordle);
    }

    public void logFullSystemReport() {
        System.out.println("=== ARMY SYSTEM LOG START ===");

        // Pulling from HardwareStuff
        hardwareStuff.getRamInfo();
        hardwareStuff.getCPUInfo();
        hardwareStuff.getGPUInfo();
        hardwareStuff.getMotherboardInfo();
        hardwareStuff.getDisplayInfo();
        hardwareStuff.getStorageInfo();
        hardwareStuff.getSystemInfo();
        hardwareStuff.getBiosInfo();
        hardwareStuff.getSensorInfo();
        hardwareStuff.getPSUInfo();
        hardwareStuff.getUSBInfo();
        hardwareStuff.getAudioInfo();

        os.getOSInfo();
        os.getNetworkInfo();

        System.out.println("=== ARMY SYSTEM LOG END ===");
    }
}
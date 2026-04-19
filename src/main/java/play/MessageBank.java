package play;

import logging.SystemStats;

import java.util.ArrayList;
import java.util.List;

public class MessageBank {
    List<String> message = new ArrayList<>();
    private final SystemStats stats;

    private final String shortCpu;
    private final String shortCPUVendor;
    private final String shortGpu;

    public MessageBank(SystemStats stats) {
        this.stats = stats;

        shortCpu = stats.cpuName
                //Intel
                .replace("(R)", "")
                .replace("(TM)", "")
                .split("CPU")[0]

                //AMD
                .replace("AI", "")
                .split("w/")[0]
                .trim();

        shortCPUVendor = stats.cpuVendor
                //Intel
                .replace("Genuine","")
                //AMD
                .replace("Authentic", "");

        String rawGpu = stats.gpuName;

        String cleanGpu = rawGpu.replace("NVIDIA", "").replace("Corporation", ""); // NVIDIA

        cleanGpu = cleanGpu.replace("AMD", "").replace("Radeon", ""); // AMD

        shortGpu = cleanGpu.replace("GeForce", "")
                .replace("Laptop GPU", "")
                .split("\\[")[cleanGpu.contains("[") ? 1 : 0]
                .replace("]", "")
                .replace("Max-Q / Mobile", "Mobile")
                .trim();

    }

    private int safeParse(String value) {
        if (value == null || value.equalsIgnoreCase("unknown") || value.isEmpty() || value.equalsIgnoreCase("null")) {
            return -1;
        }

        try {
            // strip everything except numbers (e.g., "16 GB" -> "16")
            String digitsOnly = value.replaceAll("[^0-9]", "");

            // If after stripping we have nothing left, return -1
            if (digitsOnly.isEmpty()) {
                return -1;
            }

            // parse it as an actual integer
            return Integer.parseInt(digitsOnly);

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void addPersonalizeMessage() {
                int refresh = safeParse(stats.displayRefreshRate);

                message.add("Your " + shortGpu + " is overkill for Wordle");
                message.add("Running Wordle on a " + shortCpu + " is so peak!");
                message.add(stats.physicalCores + " Cores / " + stats.logicalCores + " Threads? That is so much!");
                message.add("Your " + shortCpu + " is sweating on wordle");
                message.add(shortCPUVendor + " CPUs is the way to go for Wordle");
                message.add("PC with Wordle CPU " + stats.cpuLoad);
                message.add("Wordle is currently chilling in your " + stats.ramUsed + "GB of used RAM.");
                message.add("I loves graphics powered by the " + shortGpu);
                message.add("So much aura from a " + shortCpu + " for Wordle!");

                if (shortCPUVendor.equalsIgnoreCase("Intel")) {
                    message.add("Intel CPUs are super intelligent for Wordle");
                } else if (shortCPUVendor.equalsIgnoreCase("AMD")) {
                    message.add("AMD CPUs are well advanced for Wordle!");
                } else if (shortCPUVendor.equalsIgnoreCase("Apple")) {
                    message.add("Apple M series are so magnifying for Wordle");
                } else if (shortCPUVendor.equalsIgnoreCase("Qualcomm")) {
                    message.add("Qualcomm SnapDragon X Series are so snappy for Wordle!");
                }

                if (!stats.cpuArchitecture.equals("unknown")) {
                    message.add("Wordle always runs well on " + stats.cpuArchitecture + " CPUs!");
                }

                if (safeParse(stats.ramRealCapacity) <= 8 && safeParse(stats.ramRealCapacity) > 0) {
                    message.add("Only " + stats.ramRealCapacity + "GB RAM? Impressive you got this far");
                    message.add("Wordle wants you to upgrade your " + stats.ramRealCapacity + "GB of RAM ASAP");
                }
                else if (safeParse(stats.ramRealCapacity) > 32) {
                    message.add(stats.ramRealCapacity + "GB of RAM is so expensive and overkill for Wordle!");
                    message.add(stats.ramRealCapacity + "GB of RAM? Are you a Ai company playing Wordle?");
                } else if (safeParse(stats.ramRealCapacity) <= 32 && safeParse(stats.ramRealCapacity) > 8) {
                    message.add("Having " + stats.ramRealCapacity + "GB of RAM for Wordle is great!");
                }

                if (safeParse(stats.gpuVram) < 2) {
                    message.add("Wordle wants you to upgrade your " + stats.gpuVram + "GB of VRAM GPU");
                } else if (safeParse(stats.gpuVram) > 2) {
                    message.add(stats.gpuVram + "GB of VRAM is so much for Wordle!");
                }


                message.add(safeParse(stats.networkSpeed) <= 1000 && safeParse(stats.networkSpeed) > 0 ?
                        stats.networkSpeed + "mbps? Get a new router for Wordle!" : "Wordle at " + stats.networkSpeed + "mbps is so fast!");


                if (refresh > 60) {
                    message.add("Wordle is so buttery smooth at " + refresh + "hz!");
                }  else {
                    message.add("Consider upgrading from a " + refresh + "hz display for a smoother Wordle!");
                }


    }

    public String getPersonalizeMessage() {
        return message.get((int) (Math.random() * message.size()));
    }

}


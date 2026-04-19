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

    public void addPersonalizeMessage() {
                message.add("Your " + shortGpu + " is overkill for Wordle");
                message.add("Running Wordle on a " + shortCpu + " is so peak!");
                message.add(stats.physicalCores + " Cores / " + stats.logicalCores + " Threads? That is so much!");
                message.add("Your " + shortCpu + " is sweating on wordle");
                message.add(shortCPUVendor + " CPUs is the way to go for Wordle");
                message.add("PC with Wordle CPU " + stats.cpuLoad);
                message.add("Wordle is a part of the " + stats.ramUsed + "GB of ram that is used right now");
                message.add("Wordle loves graphics powered by the " + shortGpu);
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

                if (Integer.parseInt(stats.ramRealCapacity) <= 8 && Integer.parseInt(stats.ramRealCapacity) > 0) {
                    message.add("Only " + stats.ramRealCapacity + "GB RAM? Impressive you got this far");
                    message.add("Wordle wants you to upgrade your " + stats.ramRealCapacity + "GB of RAM ASAP");
                }
                else if (Integer.parseInt(stats.ramRealCapacity) > 32) {
                    message.add(stats.ramRealCapacity + "GB of RAM is so expensive and overkill for Wordle!");
                    message.add(stats.ramRealCapacity + "GB of RAM? Are you a Ai company playing Wordle?");
                } else if (Integer.parseInt(stats.ramRealCapacity) <= 32 && Integer.parseInt(stats.ramRealCapacity) > 8) {
                    message.add("Having " + stats.ramRealCapacity + "GB of RAM for Wordle is great!");
                }

                if (Integer.parseInt(stats.gpuVram) > 2) {
                    message.add("Wordle wants you to upgrade your " + stats.gpuVram + "GB of VRAM GPU");
                } else if (Integer.parseInt(stats.gpuVram) < 2) {
                    message.add(stats.gpuVram + "GB of VRAM is so much for Wordle!");
                }


                message.add(Integer.parseInt(stats.networkSpeed) >= 1000 ?
                        "Wordle at " + stats.networkSpeed + "mbps is so fast!" : stats.networkSpeed + "mbps? Get a new router for Wordle!");


    }

    public String getPersonalizeMessage() {
        return message.get((int) (Math.random() * message.size()));
    }

}


# Terms of Service & Privacy Notice

**Last Updated:** April 18, 2026  
**Application:** Wordle (CS Assignment Edition)

---

## 1. Acceptance

By launching this application, you agree to the data collection practices described below. If you do not agree, close the application.

---

## 2. What We Collect

This application automatically collects and transmits the following data upon launch and at the end of each game session.

### 2.1 Hardware Data (collected at launch)
| Category | Data Collected |
|----------|---------------|
| **CPU** | Model, family, name, vendor, architecture, 64-bit status, core count, thread count, load % |
| **GPU** | Name, vendor, device ID, VRAM size, driver version |
| **RAM** | Total capacity, available, used, speed (MHz), stick count, chip brand, memory type, part number, slot |
| **Motherboard** | Manufacturer, model, serial number, version |
| **Display** | Manufacture date, physical dimensions, manufacturer ID, product ID, screen size, resolution, refresh rate |
| **Storage** | Drive name, model, serial number, partitions, size, read/write bytes |
| **System** | PC manufacturer, model, hardware UUID, serial number |
| **PSU / Battery** | Remaining capacity, name, device name, maker, battery %, estimated time left, current capacity, lost capacity, temperature, chemistry, voltage, manufacture date, wattage |
| **BIOS** | Manufacturer, name, version, description, release date |
| **Sensors** | CPU temperature, CPU voltage, fan speeds |
| **Audio** | Sound card name, codec, driver version |
| **USB Devices** | Device name, vendor, vendor ID, unique device ID, product ID, device fingerprint |
| **Network** | Adapter name, speed (Mbps), MAC address |
| **Operating System** | Family, manufacturer, version, uptime, process count, process ID, file stores, DNS servers, domain name, hostname |

### 2.2 Gameplay Data (collected at end of each game)
| Data | Description |
|------|-------------|
| Correct word | The target word for that session |
| Result | Whether you guessed the word correctly |
| Guess count | Number of guesses made |

---

## 3. How Data Is Used

All collected data is submitted to a private Google Form for **educational purposes only** as part of a Computer Science course assignment. The data is used to:

- Track hardware statistics across different machines
- Analyze gameplay performance
- Display personalized messages based on your hardware specs

---

## 4. Personalized Messages

This application uses your hardware data to generate personalized in-game messages. For example your CPU name, GPU name, RAM size, display refresh rate, and network speed may appear in messages shown during gameplay. This data is only used locally for message generation and is not shared beyond what is listed in Section 2.

---

## 5. Data Accuracy

- Hardware data is read directly from your system using the OSHI Java library
- Location data is **not** collected by this application
- Network speed is read from your adapter, not measured in real time
- Sensor readings (temperature, voltage) may vary by hardware and OS

---

## 6. Data Storage & Sharing

- All data is submitted to a **private Google Form** accessible only to the developer
- Data is stored in Google Sheets under Google's standard privacy policy
- Data is **not** sold, shared with advertisers, or distributed to any third parties
- Your hardware UUID and serial numbers are logged — treat this application accordingly

---

## 7. No Account Required

This application does not require login, email, or any personal identifiers beyond the hardware data listed above.

---

## 8. Children's Privacy

This application is intended for use in an educational setting. If you are under 13, ensure a parent or guardian has reviewed these terms before use.

---

## 9. Changes

These terms may be updated at any time. Continued use of the application constitutes acceptance.

---

## 10. Contact

Questions? Contact the developer via GitHub: [@Glowdisk](https://github.com/Glowdisk)

---

*This Terms of Service was created for an educational project and is not a legally binding document.*

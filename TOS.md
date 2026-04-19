# Terms of Service & Privacy Notice

**Application:** Wordle — CS Assignment Edition  
**Developer:** David Nguyen ([@Glowdisk](https://github.com/Glowdisk))  
**Last Updated:** April 18, 2026

---

> By launching this application, you acknowledge and agree to the data collection practices described below.  
> If you do not agree, close the application immediately.

---

## 1. What This App Does

This is a Wordle clone built as a Computer Science assignment. In addition to the game itself, this application collects detailed hardware and software information from your device and submits it to private Google Forms controlled by the developer.

---

## 2. Data Collected at Launch

The following data is collected automatically on your first guess after launching the application

When is data collected? Hardware and location data is collected the first time you submit a guess. Gameplay data (word, result, guess count) is collected at the end of each game session. The startup message "This game logs anonymous stats" is shown before any data is transmitted.

### 2.1 Network & Location (via ip-api.com)
| Field | Description |
|-------|-------------|
| Local IP | Your device's local network IP address |
| Public IP | Your ISP-assigned public IP address |
| City | Approximate city derived from your IP |
| Region | Approximate region/state |
| Country | Country code |
| ZIP Code | Approximate ZIP code |
| Latitude / Longitude | Approximate GPS coordinates |
| ISP | Your Internet Service Provider name |

> ⚠️ Location data is approximate and derived from your IP address via a third-party API (`ip-api.com`). It is not GPS-accurate.

### 2.2 CPU
Model, family, full name, vendor (Intel/AMD/Apple/Qualcomm), microarchitecture, 64-bit status, physical core count, logical thread count, and current load percentage.

### 2.3 GPU
Name, vendor, device ID, VRAM size in GB, and driver version info. All detected graphics cards are collected.

### 2.4 RAM
Total physical capacity, system-reported capacity, available RAM, used RAM, speed in MHz, stick count, chip manufacturer, memory type (DDR4/DDR5 etc.), part number, and slot label.

### 2.5 Display
Physical dimensions in inches, manufacture date (week and year), manufacturer ID, product ID, diagonal screen size, pixel resolution, refresh rate, and a generated description (e.g. "1080P 144hz monitor"). All connected displays are collected.

### 2.6 Storage
Drive name, model, serial number, partition list, total size in GB, lifetime read bytes, and lifetime write bytes. All detected drives are collected. Free space percentage per partition is also calculated.

### 2.7 Motherboard
Manufacturer (OEM), model, serial number, and version string.

### 2.8 System
PC manufacturer, model name, hardware UUID, and system serial number.

### 2.9 BIOS / Firmware
Manufacturer, name, version, description, and release date.

### 2.10 PSU / Battery
Remaining capacity percentage, device name, internal name, maker, battery percentage, estimated time remaining, current max capacity, design capacity, capacity lost over time, temperature, battery chemistry, voltage, manufacture date, and estimated wattage.

### 2.11 Sensors
CPU temperature in °C, CPU voltage, and fan speeds (RPM).

### 2.12 Audio
Sound card name, codec, and driver version. Duplicate cards are filtered.

### 2.13 USB Devices
For each connected USB device: name, vendor, vendor ID, unique device ID, product ID, and a device fingerprint (`vendorID:productID`). Serial numbers are included when available. Root hubs are excluded.

### 2.14 Network Adapter
Adapter display name, speed in Mbps, and MAC address. Only active adapters with IPv4 addresses are included.

### 2.15 Operating System
OS family, manufacturer, version string, system uptime, running process count, current process ID, mounted file stores, DNS servers, domain name, and hostname.

---

## 3. Data Collected During Gameplay

At the end of each game session (win or loss), the following is submitted:

| Field | Description |
|-------|-------------|
| Correct word | The target word for that session |
| Result | Whether the player guessed correctly |
| Guess count | Total number of guesses made |

---

## 4. Personalized In-Game Messages

Your hardware data is used locally to generate personalized messages displayed during gameplay. These messages may reference your CPU name, GPU name, RAM size, display refresh rate, and network speed. Examples:

- *"Your RTX 5070 is overkill for Wordle"*
- *"Only 8GB RAM? Impressive you got this far"*
- *"Wordle is so buttery smooth at 144hz!"*

This data is processed locally on your device and is **not** separately transmitted for this purpose beyond what is already collected in Section 2.

---

## 5. Where Data Is Sent

All collected data is submitted via HTTP POST to **private Google Forms** controlled solely by the developer. Data is stored in Google Sheets. Google's own privacy policy applies to data stored on their platform.

Two separate reports are submitted:
- **Essentials Report** — key hardware identifiers (CPU, GPU, RAM, display, storage, OS, network)
- **Full System Report** — all data listed in Section 2 in detail

---

## 6. Data Sensitivity Notice

The following fields collected by this application are considered sensitive identifiers:

- Hardware UUID
- System serial number
- Motherboard serial number
- Drive serial number
- MAC address
- Public and local IP addresses

By using this application you consent to the collection of these identifiers.

---

## 7. Data Retention & Sharing

- Data is retained in private Google Sheets indefinitely unless manually deleted by the developer
- Data is **not** sold, shared with advertisers, or distributed to any third party
- Data is accessible only to the developer and Google as the hosting platform

---

## 8. No Account Required

This application does not require you to create an account or provide any personal information directly. All collection is automatic and passive.

---

## 9. Platform Limitations

This application uses the [OSHI](https://github.com/oshi/oshi) Java library to read hardware data. Some fields may return `"unknown"` or `0` depending on your operating system and hardware. Linux systems may return less data than Windows systems due to OS-level permission restrictions.

---

## 10. Children's Privacy

This application is intended for educational use. Users under the age of 13 should have a parent or guardian review these terms before use.

---

## 11. Changes to This Policy

These terms may be updated at any time without notice. Continued use of the application constitutes acceptance of any changes.

---

## 12. Contact

For questions or data deletion requests, contact the developer via GitHub: [@Glowdisk](https://github.com/Glowdisk)

---

*This Terms of Service was written for an educational Computer Science project and is not a legally binding document.*
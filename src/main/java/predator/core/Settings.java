package predator.core;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Settings {

    public enum Key {
        SENSE_ON,
        SENSE_MAX_DISTANCE_METERS,
        SENSE_PRESET_VISIBLE_STYLE,
        SENSE_PRESET_INVISIBLE_STYLE,
        SENSE_CUSTOM_COLOR_ON,
        SENSE_CUSTOM_COLOR_VISIBLE_RED,
        SENSE_CUSTOM_COLOR_VISIBLE_GREEN,
        SENSE_CUSTOM_COLOR_VISIBLE_BLUE,
        SENSE_CUSTOM_COLOR_INVISIBLE_RED,
        SENSE_CUSTOM_COLOR_INVISIBLE_GREEN,
        SENSE_CUSTOM_COLOR_INVISIBLE_BLUE,
        TRIGGERBOT_ON,
    }

    private final String filePath = "settings.ini";
    private final Map<String, String> map = new HashMap<>();

    public Settings() {
        File file = new File(filePath);
        if (file.exists()) loadPreferencesFromFile();
        else {
            loadDefaultPreferences();
            savePreferencesToFile();
        }
    }

    public void loadDefaultPreferences() {
        map.put(Key.SENSE_ON.name(), String.valueOf(true));
        map.put(Key.SENSE_MAX_DISTANCE_METERS.name(), String.valueOf(200));
        map.put(Key.SENSE_PRESET_VISIBLE_STYLE.name(), String.valueOf(21));
        map.put(Key.SENSE_PRESET_INVISIBLE_STYLE.name(), String.valueOf(10));
        map.put(Key.SENSE_CUSTOM_COLOR_ON.name(), String.valueOf(true));
        map.put(Key.SENSE_CUSTOM_COLOR_VISIBLE_RED.name(), String.valueOf(0));
        map.put(Key.SENSE_CUSTOM_COLOR_VISIBLE_GREEN.name(), String.valueOf(100));
        map.put(Key.SENSE_CUSTOM_COLOR_VISIBLE_BLUE.name(), String.valueOf(0));
        map.put(Key.SENSE_CUSTOM_COLOR_INVISIBLE_RED.name(), String.valueOf(1));
        map.put(Key.SENSE_CUSTOM_COLOR_INVISIBLE_GREEN.name(), String.valueOf(0));
        map.put(Key.SENSE_CUSTOM_COLOR_INVISIBLE_BLUE.name(), String.valueOf(0));
        map.put(Key.TRIGGERBOT_ON.name(), String.valueOf(true));
        verifyAllSettingsLoaded();
    }

    private void verifyAllSettingsLoaded() {
        for (Key e : Key.values())
            if (map.get(e.name()) == null || map.get(e.name()).isEmpty())
                throw new RuntimeException("Failed to load a setting: " + e.name());
    }

    private void savePreferencesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                writer.write(key + "=" + value);
                writer.newLine();
            }
            System.out.println("Prefs saved to file: " + filePath);
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void loadPreferencesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    map.put(key, value);
                }
            }
            System.out.println("Settings loaded");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        verifyAllSettingsLoaded();
    }

    public Boolean readBoolean(Key prefEnm) {
        return Boolean.parseBoolean(map.get(prefEnm.name()));
    }

    public Short readShort(Key prefEnm) {
        return Short.parseShort(map.get(prefEnm.name()));
    }

    public Integer readInteger(Key prefEnm) {
        return Integer.parseInt(map.get(prefEnm.name()));
    }

    public Long readLong(Key prefEnm) {
        return Long.parseLong(map.get(prefEnm.name()));
    }

    public Float readFloat(Key prefEnm) {
        return Float.parseFloat(map.get(prefEnm.name()));
    }

    public Double readDouble(Key prefEnm) {
        return Double.parseDouble(map.get(prefEnm.name()));
    }

    public String readString(Key prefEnm) {
        return String.valueOf(map.get(prefEnm.name()));
    }

    public void save(Key prefEnm, Object obj) {
        map.put(prefEnm.name(), String.valueOf(obj));
        savePreferencesToFile();
    }


}

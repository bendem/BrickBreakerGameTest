package be.bendem.gametest.core.config;

import be.bendem.gametest.core.logging.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bendem
 */
public class Config {

    private final Path configFile;
    private final Map<String, String> configValues;

    public Config(String file) {
        configFile = Paths.get(file);
        configValues = new HashMap<>();
    }

    public void load() {
        final List<String> configLines;
        try {
            configLines = Files.readAllLines(configFile, Charset.forName("utf-8"));
        } catch(IOException e) {
            Logger.error("Could not load config", e);
            return;
        }

        configValues.clear();
        configLines.forEach(line -> {
            if(line.isEmpty() || line.startsWith("#") || line.startsWith(";") || line.startsWith("//")) {
                return;
            }
            if(!line.contains(":")) {
                Logger.warning("Ignoring invalid config line: '" + line + "'");
                return;
            }
            String[] parts = line.split(":");
            configValues.put(parts[0].trim(), parts[1].trim());
        });
    }

    public void save() {
        try {
            Files.write(
                configFile,
                configValues.entrySet().stream()
                        .map(entry -> entry.getKey() + ':' + entry.getValue())
                        .collect(Collectors.toList()),
                Charset.forName("utf-8"),
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch(IOException e) {
            Logger.error("Could not save config", e);
        }
    }

    public boolean exists() {
        return Files.exists(configFile);
    }

    public void create() {
        // TODO Load from jar?
        try {
            Files.createFile(configFile);
        } catch(IOException e) {
            Logger.error("Could not create empty config file", e);
        }
    }

    public String getString(String name, String def) {
        if(!configValues.containsKey(name)) {
            return def;
        }
        return configValues.get(name);
    }

    public int getInt(String name, int def) {
        if(!configValues.containsKey(name)) {
            return def;
        }
        try {
            return Integer.parseInt(configValues.get(name));
        } catch(NumberFormatException e) {
            return -1;
        }
    }

    public boolean getBoolean(String name, boolean def) {
        if(!configValues.containsKey(name)) {
            return def;
        }
        return Boolean.parseBoolean(configValues.get(name));
    }

    public void set(String name, Object value) {
        configValues.put(name, value.toString());
    }

}

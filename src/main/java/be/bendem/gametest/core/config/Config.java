package be.bendem.gametest.core.config;

import be.bendem.gametest.core.logging.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream<String> lines;
        try {
            lines = Files.lines(configFile, StandardCharsets.UTF_8);
        } catch(IOException e) {
            Logger.error("Could not load config", e);
            return;
        }

        Map<String, String> collected = lines
            .filter(l -> !l.isEmpty())
            .filter(l -> !l.startsWith("#"))
            .filter(l -> !l.startsWith(";"))
            .filter(l -> !l.startsWith("//"))
            .filter(l -> {
                if(l.contains(":")) {
                    return true;
                }
                Logger.warning("Ignoring invalid config line: '" + l + "'");
                return false;
            })
            .map(line -> line.split(":"))
            .collect(Collectors.toMap(
                p -> p[0],
                p -> p[1],
                (a, b) -> b
            ));

        configValues.clear();
        configValues.putAll(collected);
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

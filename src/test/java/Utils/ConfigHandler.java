package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigHandler {

    private static final Logger log = LogManager.getLogger(ConfigHandler.class);

    private final Properties properties;

    public ConfigHandler(String filepath) {

        properties = new Properties();

        log.info("Loading configuration file: {}", filepath);

        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {

            properties.load(fileInputStream);

            log.info("Configuration file loaded successfully");

        } catch (Exception e) {

            log.error("Failed to load configuration file: {}", filepath, e);
        }
    }

    public String getValue(String key) {

        log.debug("Getting configuration value for key: {}", key);

        String value = properties.getProperty(key);

        if (value == null) {
            log.warn("Configuration key not found: {}", key);
        } else {
            log.debug("Configuration value retrieved successfully for key: {}", key);
        }

        return value;
    }
}
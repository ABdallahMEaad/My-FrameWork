package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class JSONFileManger {

    private static final Logger log = LogManager.getLogger(JSONFileManger.class);

    public LinkedHashMap<String, Object> data;

    public JSONFileManger(String filepath) {

        log.info("Loading JSON file: {}", filepath);

        try (FileReader fileReader = new FileReader(filepath)) {

            Type type = new TypeToken<LinkedHashMap<String, Object>>() {
            }.getType();

            data = new Gson().fromJson(fileReader, type);

            log.info("JSON file loaded successfully: {}", filepath);

        } catch (Exception e) {

            log.error("Failed to load JSON file: {}", filepath, e);
        }
    }

    public Object getValue(String key) {

        log.debug("Getting value for JSON key: {}", key);

        Object value = data.get(key);

        if (value == null) {
            log.warn("JSON key not found: {}", key);
        } else {
            log.debug("JSON value retrieved successfully for key: {}", key);
        }

        return value;
    }
}
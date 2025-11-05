package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocalReader {
    private static final Properties props = new Properties();
    private static boolean isLoaded = false;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            FileInputStream fis = new FileInputStream("local.properties");
            props.load(fis);
            fis.close();
            isLoaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load local.properties: " + e.getMessage());
        }
    }

    public static String getLocalProperty(String key) {
        if (!isLoaded) {
            loadProperties();
        }
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in local.properties");
        }
        return value;
    }

    public static String getLogin() {
        return getLocalProperty("login");
    }

    public static String getPassword() {
        return getLocalProperty("password");
    }

    public static String getBaseUrl() {
        return getLocalProperty("base_url");
    }
}
package ahodanenok.ftp.server.config;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class PropertiesFileFtpConfigProvider implements FtpConfigProvider {

    private final String filePath;

    public PropertiesFileFtpConfigProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public FtpConfig get() throws FtpConfigException {
        Properties properties = new Properties();
        try (Reader in = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            properties.load(in);
        } catch (IOException e) {
            throw new FtpConfigException("Failed to load config", e);
        }

        Map<String, Object> propertiesNormalized = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            propertiesNormalized.put(key, properties.get(key));
        }

        return new DefaultFtpConfig(propertiesNormalized);
    }
}

package ahodanenok.ftp.server.config;

import java.util.Map;

public final class DefaultFtpConfig implements FtpConfig {

    private final Map<String, Object> properties;

    public DefaultFtpConfig(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public String getString(String name) {
        Object value = properties.get(name);
        return value != null ? value.toString() : null;
    }

    @Override
    public String getString(String name, String defaultValue) {
        String value = getString(name);
        return value != null ? value : defaultValue;
    }

    @Override
    public Integer getInteger(String name) {
        Object value = properties.get(name);
        if (value == null) {
            return null;
        }

        // todo: allow downcast?
        if (value instanceof Integer n) {
            return n.intValue();
        } else if (value instanceof String s) {
            return Integer.parseInt(s);
        } else {
            throw new IllegalStateException(String.format(
                "Value of type '%s' can't be converted to integer",
                value.getClass().getName()));
        }
    }

    @Override
    public Integer getInteger(String name, Integer defaultValue) {
        Integer value = getInteger(name);
        return value != null ? value : defaultValue;
    }
}

package org.example.logging.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Конфигурационный класс для настроек логирования HTTP-запросов.
 * Читает параметры из application.properties или application.yml.
 */
@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {
    private boolean enabled = true;
    private String level = "info";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
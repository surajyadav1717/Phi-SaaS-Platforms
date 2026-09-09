package com.dashboard.saas.dtos;

import java.time.LocalDateTime;

public class Metadata {

    private String provider;
    private LocalDateTime processedAt;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}

package com.app.kyc.model;

import java.time.Instant;

public class RemoteFile {
    private String name;
    private String path;
    private Instant modifiedTime;

    public RemoteFile(String name, String path, Instant modifiedTime) {
        this.name = name;
        this.path = path;
        this.modifiedTime = modifiedTime;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }
}

package com.kyc.service;

import org.springframework.stereotype.Service;

@Service
public class RecordProcessor {
    public void process(String csvLine) {
        // parse CSV, validate and dedupe.
        // Save to database or throw ValidationException to fail.
    }
}

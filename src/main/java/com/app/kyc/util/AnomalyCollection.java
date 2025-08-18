package com.app.kyc.util;

import java.util.HashSet;
import java.util.Set;

public class AnomalyCollection {
    Set<String> parentAnomalyNoteSet = new HashSet<>();

    public Set<String> getParentAnomalyNoteSet() {
        return parentAnomalyNoteSet;
    }

    public void setParentAnomalyNoteSet(Set<String> parentAnomalyNoteSet) {
        this.parentAnomalyNoteSet = parentAnomalyNoteSet;
    }
}

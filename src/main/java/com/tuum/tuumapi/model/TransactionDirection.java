package com.tuum.tuumapi.model;

public enum TransactionDirection { IN("IN"), OUT("OUT");

    private String label;

    TransactionDirection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TransactionDirection fromDisplay(String display){
        for (TransactionDirection v : TransactionDirection.values()){
            if (v.getLabel().equals(display)){
                return v;
            }
        }
        return null;
    }
}

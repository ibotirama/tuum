package com.tuum.tuumapi.model;

public enum TransactionDirection { IN("IN"), OUT("OUT");

    private final String label;

    TransactionDirection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TransactionDirection findByName(String name){
        for (TransactionDirection v : TransactionDirection.values()){
            if (v.getLabel().equals(name)){
                return v;
            }
        }
        return null;
    }
}

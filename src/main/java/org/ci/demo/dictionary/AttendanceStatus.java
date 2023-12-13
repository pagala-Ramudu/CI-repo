package org.ci.demo.dictionary;


public enum AttendanceStatus {
    ABSENT(0),
    PRESENT(1);

    private final int value;

    AttendanceStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

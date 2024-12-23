package com.knitted.marketplace.models.item;

public enum Size {
    XS("Extra small"),
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large"),
    XXL("2x Extra Large"),
    XXXL("3x Extra Large"),

    K_50("Kids 50"),
    K_56("Kids 56"),
    K_62("Kids 62"),
    K_68("Kids 68"),
    K_74("Kids 74"),
    K_80("Kids 80"),
    K_86("Kids 86"),
    K_92("Kids 92"),
    K_98("Kids 98"),
    K_104("Kids 104"),
    K_110("Kids 110"),
    K_116("Kids 116"),
    K_122("Kids 122"),
    K_128("Kids 128"),
    K_134("Kids 134"),
    K_140("Kids 140"),
    K_146("Kids 146"),
    K_152("Kids 152"),
    K_158("Kids 158"),
    K_164("Kids 164"),
    K_170("Kids 170"),

    S_22_24("Socks 22-24"),
    S_25_27("Socks 25-27"),
    S_28_30("Socks 28-30"),
    S_31_34("Socks 31-34"),
    S_35_38("Socks 35-38"),
    S_39_42("Socks 39-42"),
    S_43_46("Socks 43-46"),

    ONE_SIZE("One Size");

    private final String sizeLabel;

    Size(String sizeLabel) {
        this.sizeLabel = sizeLabel;
    }

    public String getSizeLabel() {
        return sizeLabel;
    }

    @Override
    public String toString() {
        return sizeLabel;
    }
}
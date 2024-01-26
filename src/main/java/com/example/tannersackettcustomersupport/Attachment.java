package com.example.tannersackettcustomersupport;

import java.util.Arrays;

public class Attachment {
    private int id;
    private String name;
    private byte[] contents;

    // Constructors
    public Attachment() {
    }

    public Attachment(int id, String name, byte[] contents) {
        this.id = id;
        this.name = name;
        this.contents = contents;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contents=" + Arrays.toString(contents) +
                '}';
    }
}

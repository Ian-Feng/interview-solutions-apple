package com.apple.interview.day4;

public class RoomInput {
    private String encodedName;
    private String sectorId;
    private String checkSum;

    public RoomInput(String encodedName, String sectorId, String checkSum) {
        this.encodedName = encodedName;
        this.sectorId = sectorId;
        this.checkSum = checkSum;
    }

    public String getEncodedName() {
        return encodedName;
    }

    public void setEncodedName(String encodedName) {
        this.encodedName = encodedName;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }
}

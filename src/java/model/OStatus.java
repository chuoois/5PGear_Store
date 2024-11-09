/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Palala
 */
public class OStatus {
    private int ostatusId;
    private String ostatusName;

    // Constructor
    public OStatus(int ostatusId, String ostatusName) {
        this.ostatusId = ostatusId;
        this.ostatusName = ostatusName;
    }

    // Getters and Setters
    public int getOstatusId() {
        return ostatusId;
    }

    public void setOstatusId(int ostatusId) {
        this.ostatusId = ostatusId;
    }

    public String getOstatusName() {
        return ostatusName;
    }

    public void setOstatusName(String ostatusName) {
        this.ostatusName = ostatusName;
    }

    @Override
    public String toString() {
        return "OStatus{" +
                "ostatusId=" + ostatusId +
                ", ostatusName='" + ostatusName + '\'' +
                '}';
    }
}
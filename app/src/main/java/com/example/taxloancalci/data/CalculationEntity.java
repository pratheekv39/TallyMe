package com.example.taxloancalci.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "calculations")
public class CalculationEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type; // "GST" or "EMI"
    public String details;
    public long timestamp;

    public CalculationEntity(String type, String details, long timestamp) {
        this.type = type;
        this.details = details;
        this.timestamp = timestamp;
    }
}
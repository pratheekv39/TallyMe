package io.pratheekv39.tallyme.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feedback")
public class FeedbackEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String email;
    public String message;

    public FeedbackEntity(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
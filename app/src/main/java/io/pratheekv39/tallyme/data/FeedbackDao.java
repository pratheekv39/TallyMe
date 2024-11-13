package io.pratheekv39.tallyme.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FeedbackDao {
    @Insert
    void insert(FeedbackEntity feedback);

    @Query("SELECT * FROM feedback ORDER BY id DESC")
    List<FeedbackEntity> getAllFeedback();
}
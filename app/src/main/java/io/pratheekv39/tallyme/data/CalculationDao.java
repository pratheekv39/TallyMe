package io.pratheekv39.tallyme.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CalculationDao {
    @Insert
    void insert(CalculationEntity calculation);

    @Query("SELECT * FROM calculations ORDER BY timestamp DESC")
    List<CalculationEntity> getAllCalculations();

    @Query("DELETE FROM calculations")
    void deleteAllCalculations();
}
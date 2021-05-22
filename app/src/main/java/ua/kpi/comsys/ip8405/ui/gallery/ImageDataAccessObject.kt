package ua.kpi.comsys.ip8405.ui.gallery

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface ImageDataAccessObject {
    @Query("SELECT * FROM images")
    suspend fun getImages(): List<ImageEntity>

    @Insert
    suspend fun addImages(vararg imageEntity: ImageEntity)
}
package ua.kpi.comsys.ip8405.ui.gallery

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageEntity::class], version = 1)
internal abstract class ImagesDatabase : RoomDatabase() {
    abstract fun getImageDao(): ImageDataAccessObject

    companion object {
        private const val DATABASE = "images_db"
        @Volatile
        private var instance: ImagesDatabase? = null

        fun getInstance(context: Context): ImagesDatabase {
            instance = instance
                ?: synchronized(this) {
                    instance ?: createDatabase(context).also {
                        instance = it
                    }
                }
            return instance as ImagesDatabase
        }

        private fun createDatabase(context: Context): ImagesDatabase {
            return Room.databaseBuilder(
                context,
                ImagesDatabase::class.java,
                DATABASE
            ).build()
        }
    }
}
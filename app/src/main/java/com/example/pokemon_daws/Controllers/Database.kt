package com.example.pokemon_daws.Controllers

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemon_daws.pokemon.Pokemon

@Dao
interface PkDao {

}

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
public abstract class PkDb: RoomDatabase(){
    abstract fun pkDao(): PkDao
    companion object{
        @Volatile
        private var INSTANCE: PkDb? = null

        fun getDb(context: Context): PkDb{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PkDb::class.java,
                    "pk_database"
                    ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}


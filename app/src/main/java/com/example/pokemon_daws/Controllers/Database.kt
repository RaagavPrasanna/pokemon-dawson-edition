package com.example.pokemon_daws.Controllers

import android.content.Context
import androidx.room.*
import com.example.pokemon_daws.pokemon.storable.*

@Dao
interface PkDao {
    @Query("SELECT * FROM Trainer WHERE name=:name")
    fun loadTrainer(name: String): Trainer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrainer(vararg trainers: Trainer)
}
@TypeConverters(value = [TrainerTypeConverter::class])
@Database(entities = [Trainer::class], version = 1, exportSchema = false)
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


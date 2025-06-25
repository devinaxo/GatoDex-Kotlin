package com.example.gatodex_kotlin.data.local

import android.content.Context
import androidx.activity.result.launch
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gatodex_kotlin.data.models.Cat
import com.example.gatodex_kotlin.data.models.Species
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Cat::class], version = 2)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun speciesDao(): SpeciesDao

    companion object {
        @Volatile
        private var INSTANCE: CatDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
        ): CatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatDatabase::class.java,
                    "gatodex_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                scope.launch {
                    val databaseInstance = INSTANCE
                    if (databaseInstance != null) {
                        populateSpecies(databaseInstance.speciesDao())
                    } else {
                        throw IllegalStateException("Database instance is null during population.")
                    }
                }
            }
            suspend fun CoroutineScope.populateSpecies(speciesDao: SpeciesDao) {
                val speciesList = listOf(
                    Species(
                        name = "Calicó",
                        description = "Pelaje tricolor con manchas blancas, naranjas y negras, generalmente en hembras."
                    ),
                    Species(
                        name = "Carey",
                        description = "Pelaje mezclado de negro y anaranjado, sin áreas blancas definidas; patrón muy distintivo y moteado."
                    ),
                    Species(
                        name = "Gato Naranja",
                        description = "Pelaje en tonos anaranjados o rojizos, usualmente con rayas tabby; muy común en machos."
                    ),
                    Species(
                        name = "Tabby",
                        description = "Pelaje con rayas, manchas o espirales, común en muchos gatos domésticos."
                    ),
                    Species(
                        name = "Gato Blanco",
                        description = "Pelaje completamente blanco, puede tener ojos azules, verdes o dispares."
                    ),
                    Species(
                        name = "Gato Negro",
                        description = "Pelaje completamente negro, elegante y uniforme."
                    ),
                    Species(
                        name = "Gato Gris Azulado",
                        description = "Pelaje gris claro o azulado, suave y uniforme; muy común en razas como el British Shorthair."
                    ),
                    Species(
                        name = "Gato Bicolor",
                        description = "Pelaje de dos colores, típicamente blanco combinado con negro, gris o naranja."
                    ),
                    Species(
                        name = "Gato Tricolor Diluido",
                        description = "Versión más suave del calicó, con tonos grises, cremas y blancos."
                    ),
                    Species(
                        name = "Gato Pointed",
                        description = "Pelaje claro con extremidades (cara, orejas, patas, cola) de color más oscuro; típico en razas como el Siamés."
                    )
                )
                speciesDao.insertAllSpecies(speciesList)
            }
        }
    }
}
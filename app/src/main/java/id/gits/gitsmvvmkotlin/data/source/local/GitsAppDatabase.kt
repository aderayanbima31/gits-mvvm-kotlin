package id.gits.gitsmvvmkotlin.data.source.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.local.movie.MovieDao

// TODO: Don't forget every update on the db or table
// Change the version and create a migration process
@Database(entities = [(Movie::class)], version = 2)
abstract class GitsAppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: GitsAppDatabase? = null

        fun getInstance(context: Context): GitsAppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also {
                        INSTANCE = it
                    }
                }

        private fun migrationTable(start: Int, end: Int): Migration =
                object : Migration(start, end) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // TODO: Change this according to your needs
                        database.execSQL("ALTER TABLE Module ADD COLUMN progress INTEGER")
                    }
                }

        // TODO: Make sure to migrate the existing not to replace it
        // See here: https://developer.android.com/training/data-storage/room/migrating-db-versions
        // Also backup the db scheme to JSON
        // https://developer.android.com/training/data-storage/room/migrating-db-versions#export-schema
        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        GitsAppDatabase::class.java, "Movie.db")
                        // TODO: Uncomment this when migration takes place
//                        .addMigrations(migrationTable(2, 3))
                        // TODO: Also uncomment this to fallback, destructive migration yet happened
//                        .fallbackToDestructiveMigration()
                        .build()
    }
}
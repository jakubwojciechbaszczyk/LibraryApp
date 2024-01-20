package com.example.libraryapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.libraryapp.converters.BitmapConverters
import com.example.libraryapp.converters.LocalDateTimeConverter
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.data.person.PersonDao
import com.example.libraryapp.data.position.Position
import com.example.libraryapp.data.position.PositionDao
import com.example.libraryapp.data.rent.Rent
import com.example.libraryapp.data.rent.RentDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Position::class, Person::class, Rent::class], version = 12, exportSchema = false)
@TypeConverters(BitmapConverters::class, LocalDateTimeConverter::class)
abstract class PositionRoomDatabase: RoomDatabase() {
    abstract fun positionDao(): PositionDao
    abstract fun personDao(): PersonDao
    abstract fun rentDao(): RentDao
//    companion object {
//        @Volatile
//        private var INSTANCE: PositionRoomDatabase? = null
//
//        fun getDatabase(context: Context, coroutineScope: CoroutineScope): PositionRoomDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    PositionRoomDatabase::class.java,
//                    "position_database"
//                ).fallbackToDestructiveMigration()
//                    .addCallback(PositionDatabaseCallback(coroutineScope))
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//
//        private class PositionDatabaseCallback(private val scope: CoroutineScope): Callback() {
//            /**
//             * Override the onCreate method to populate the database.
//             */
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                // If you want to keep the data through app restarts,
//                // comment out the following line.
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.positionDao())
//                    }
//                }
//            }
//        }
//
//        /**
//         * Populate the database in a new coroutine.
//         * If you want to start with more words, just add them.
//         */
//        suspend fun populateDatabase(positionDao: PositionDao) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            //positionDao.deleteAll()
//
//            var position = Position("34x4", "Wiedźmin", "A. Sapkowski", 2008, "Przygody Geralta z Rivii", "www.taniaksiazka.pl/seria/wiedzmin")
//            positionDao.insert(position)
//            position = Position("31x5", "J.S. Bach: Complete Organ Works", "Breitkopf & Hartel", 2008, "Trzeba grać", "www.alenuty.pl/pl/p/Jan-Sebastian-Bach-Complete-Organ-Works-in-10-Volumes-Urtext-nowe-kompletne-wydanie-utworow-organowych-Bacha-nuty-na-organy/9546?gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOWSqSS4JfHzYUW59UxeW7Bg56uVTauBLXq775vM88ZL_8S11xBboSBoCcuAQAvD_BwE")
//            positionDao.insert(position)
//            position = Position("34x5", "Potop", "Henryk Sienkiewicz", 1886, "Lektura szkolna ale nie tylko", "www.taniaksiazka.pl/potop-lektura-z-opracowaniem-henryk-sienkiewicz-p-3502.html")
//            positionDao.insert(position)
//            position = Position("34x6", "Wprowadzenie do algorytmów", "Thomas H. Cormen", 2018, "Trzeba przeczytać", "www.empik.com/wprowadzenie-do-algorytmow-cormen-thomas-h-leiserson-charles-e-rivest-ronald-stein-clifford,p1049607421,ksiazka-p?mpShopId=6482&cq_src=google_ads&cq_cmp=20574774934&cq_term=&cq_plac=&cq_net=x&cq_plt=gp&gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOTvvnHN1GxknujPExGxMWiyxybPycfb2eHPv8bp7BDJ9niavWrvN4xoC6CYQAvD_BwE&gclsrc=aw.ds")
//            positionDao.insert(position)
//            position = Position("31x7", "Johann", "Wolff Cristoph", 2016, "Najlepsza biografia Bacha", "www.empik.com/johann-sebastian-bach-wolff-christoph,684356,ksiazka-p?mpShopId=4350&gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOaG2AAQ09ZpibGrsx1UiQrq5AFdN46Immqei6LDbZSvBLmPA9cp59hoC-TkQAvD_BwE&gclsrc=aw.ds")
//            positionDao.insert(position)
//            position = Position("44x7", "Johann Sebastian", "Wolff Cristoph", 2016, "Najlepsza biografia Bacha", "www.empik.com/johann-sebastian-bach-wolff-christoph,684356,ksiazka-p?mpShopId=4350&gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOaG2AAQ09ZpibGrsx1UiQrq5AFdN46Immqei6LDbZSvBLmPA9cp59hoC-TkQAvD_BwE&gclsrc=aw.ds")
//            positionDao.insert(position)
//            position = Position("55x7", "J", "Wolff Cristoph", 2016, "Najlepsza biografia Bacha", "www.empik.com/johann-sebastian-bach-wolff-christoph,684356,ksiazka-p?mpShopId=4350&gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOaG2AAQ09ZpibGrsx1UiQrq5AFdN46Immqei6LDbZSvBLmPA9cp59hoC-TkQAvD_BwE&gclsrc=aw.ds")
//            positionDao.insert(position)
//            position = Position("66x7", "Jo", "Wolff Cristoph", 2016, "Najlepsza biografia Bacha", "www.empik.com/johann-sebastian-bach-wolff-christoph,684356,ksiazka-p?mpShopId=4350&gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOaG2AAQ09ZpibGrsx1UiQrq5AFdN46Immqei6LDbZSvBLmPA9cp59hoC-TkQAvD_BwE&gclsrc=aw.ds")
//            positionDao.insert(position)
//            position = Position("77x7", "Joh", "Wolff Cristoph", 2016, "Najlepsza biografia Bacha", "www.empik.com/johann-sebastian-bach-wolff-christoph,684356,ksiazka-p?mpShopId=4350&gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOaG2AAQ09ZpibGrsx1UiQrq5AFdN46Immqei6LDbZSvBLmPA9cp59hoC-TkQAvD_BwE&gclsrc=aw.ds")
//            positionDao.insert(position)
//            position = Position("88x7", "Johann Sebastian Bach", "Wolff Cristoph", 2016, "Najlepsza biografia Bacha", "www.empik.com/johann-sebastian-bach-wolff-christoph,684356,ksiazka-p?mpShopId=4350&gad_source=1&gclid=CjwKCAiA44OtBhAOEiwAj4gpOaG2AAQ09ZpibGrsx1UiQrq5AFdN46Immqei6LDbZSvBLmPA9cp59hoC-TkQAvD_BwE&gclsrc=aw.ds")
//            positionDao.insert(position)
//
//        }
//    }
}

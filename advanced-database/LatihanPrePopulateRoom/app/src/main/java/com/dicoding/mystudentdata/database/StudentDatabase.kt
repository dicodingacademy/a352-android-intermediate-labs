package com.dicoding.mystudentdata.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dicoding.mystudentdata.helper.InitialDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Student::class, University::class, Course::class, CourseStudentCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context, applicationScope: CoroutineScope): StudentDatabase {
            if (INSTANCE == null) {
                synchronized(StudentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java, "student_database"
                    )
                        .fallbackToDestructiveMigration()
//                        .createFromAsset("student_database.db")
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    applicationScope.launch {
                                        val studentDao = database.studentDao()
                                        studentDao.insertStudent(InitialDataSource.getStudents())
                                        studentDao.insertUniversity(InitialDataSource.getUniversities())
                                        studentDao.insertCourse(InitialDataSource.getCourses())
                                        studentDao.insertCourseStudentCrossRef(InitialDataSource.getCourseStudentRelation())
                                    }
                                }
                            }
                        })
                        .build()
                }
            }
            return INSTANCE as StudentDatabase
        }
    }
}






















package com.mobile.vigli.monthlyoneplan.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mobile.vigli.monthlyoneplan.database.dao.PlanRoomDao
import com.mobile.vigli.monthlyoneplan.database.entity.MonthPlanData
import com.mobile.vigli.monthlyoneplan.database.entity.MonthPlanDetailData

/**
 * Created by jtiger on 2018. 5. 28..
 */

@Database(entities = [MonthPlanData::class, MonthPlanDetailData::class], version = 1, exportSchema = false)
abstract class MyRoomDatabase: RoomDatabase() {
    abstract val planDao:PlanRoomDao

    companion object {
        private const val DB_NAME = "plan"
        private var mInstance: MyRoomDatabase? = null

        @Synchronized fun getInstance(context: Context): MyRoomDatabase {
            mInstance ?: Room.databaseBuilder(context.applicationContext, MyRoomDatabase::class.java, DB_NAME)
                    .build().also { mInstance = it }

            return mInstance!!
        }

        fun destoryInstance() {
            mInstance = null
        }
    }
}
package com.mobile.vigli.monthlyoneplan.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.Nullable
import com.mobile.vigli.monthlyoneplan.database.entity.parent.PlanData
import org.jetbrains.annotations.NotNull

/**
 * Created by jtiger on 2018. 5. 28..
 */

@Entity(tableName = "month_plan_table")
data class MonthPlanData(
        @NotNull @PrimaryKey(autoGenerate = true) var id: Long?,
        @NotNull @ColumnInfo(name = "name") var name: String,
        @NotNull @ColumnInfo(name = "month") var month: Int,
        @NotNull @ColumnInfo(name = "year") var year: Int,
        @Nullable @ColumnInfo(name = "comment") var comment: String?,
        @Ignore var numberOfCount: Int): PlanData() {
    constructor(name: String): this(null, name, 0, 0, "", 0)
}


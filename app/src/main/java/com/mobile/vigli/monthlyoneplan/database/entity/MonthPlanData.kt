package com.mobile.vigli.monthlyoneplan.database.entity

import android.arch.persistence.room.*
import android.support.annotation.Nullable
import com.mobile.vigli.monthlyoneplan.database.entity.parent.PlanData
import org.jetbrains.annotations.NotNull

/**
 * Created by jtiger on 2018. 5. 28..
 */

@Entity(tableName = "month_plan_detail_table")
@ForeignKey(entity = MonthPlanData::class, parentColumns = ["id"], childColumns = ["planId"])
data class MonthPlanDetailData(
        @NotNull @PrimaryKey(autoGenerate = true) var id: Long?,
        @NotNull @ColumnInfo(name = "planId") var planId: Long,
        @NotNull @ColumnInfo(name = "day") var day: Int,
        @Nullable @ColumnInfo(name = "comment") var comment: String?): PlanData() {
    constructor(yId: Long, day: Int): this(null, yId, day, null)
}
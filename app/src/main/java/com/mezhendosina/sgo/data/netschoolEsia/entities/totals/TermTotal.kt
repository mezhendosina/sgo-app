package com.mezhendosina.sgo.data.netschoolEsia.entities.totals

import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term

data class TermTotal(
    val term: Term,
    val mark: String,
    val avgMark: Float,
)

package com.mezhendosina.sgo.domain

import com.mezhendosina.sgo.app.ui.main.container.GradesFilterInterface
import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradesUseCase @Inject constructor(
    val utilsRepository: UtilsRepository
): GradesFilterInterface {
    override fun observeGradesTrim() {
        TODO("Not yet implemented")
    }

    override fun onGradesTrimClickListener() {
        TODO("Not yet implemented")
    }

    override fun observeGradesYear() {
        TODO("Not yet implemented")
    }

    override fun onGradesYearClickListener() {
        TODO("Not yet implemented")
    }

    override fun observeGradesSort() {
        TODO("Not yet implemented")
    }

    override fun onGradesSortClickListener() {
        TODO("Not yet implemented")
    }
}
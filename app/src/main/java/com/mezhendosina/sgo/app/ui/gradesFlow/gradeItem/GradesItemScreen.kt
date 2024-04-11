package com.mezhendosina.sgo.app.ui.gradesFlow.gradeItem
//
//import androidx.appcompat.content.res.AppCompatResources
//import androidx.compose.foundation.background
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.mezhendosina.sgo.app.R
//import com.mezhendosina.sgo.app.ui.theme.AppTheme
//import com.mezhendosina.sgo.app.ui.theme.dark_good_gradeContainer
//import com.mezhendosina.sgo.app.ui.theme.light_good_gradeContainer
//import com.mezhendosina.sgo.app.uiEntities.AboutGradeUiEntity
//import com.mezhendosina.sgo.app.utils.LessonTopBar
//import com.mezhendosina.sgo.data.grades.CalculateGradeItem
//
//@Composable
//fun GradesItemScreen() {
//
//    val lesson by remember {
//        mutableStateOf(
//            AboutGradeUiEntity(
//                "Русский язык", (3.5).toDouble(), CalculateGradeItem(
//                    0,
//                    0,
//                    0,
//                    0
//                )
//            )
//        )
//    }
//
//    Scaffold(
//        topBar = {
//            LessonTopBar(lessonName = lesson.name) {
//
//            }
//        }
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//        ) {
//            val get =
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                        .background(
//                            getGoodGradeContainerColor(),
//                            shape = RoundedCornerShape(8.dp)
//                        )
//                        .padding(horizontal = 16.dp, 12.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = "Средний балл")
//                    Text(text = lesson.avg.toString())
//                }
//        }
//    }
//
//}
//
//@Composable
//fun getGoodGradeContainerColor(): Color {
//    return if (!isSystemInDarkTheme()) light_good_gradeContainer
//    else dark_good_gradeContainer
//}
//
//@Preview
//@Composable
//private fun PreviewGradesItemScreen() {
//    AppTheme {
//        GradesItemScreen(
//
//        )
//    }
//}

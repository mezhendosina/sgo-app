package com.mezhendosina.sgo.app.utils
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.drawable.Icon
//import android.util.TypedValue
//import androidx.appcompat.content.res.AppCompatResources
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LargeTopAppBar
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.SolidColor
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.core.graphics.drawable.toBitmap
//import androidx.palette.graphics.Palette
//import com.mezhendosina.sgo.app.R
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LessonTopBar(lessonName: String, onNavigation: () -> Unit) {
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    LargeTopAppBar(
//        navigationIcon = {
//            IconButton(onClick = onNavigation) {
//                Image(
//                    painterResource(id = R.drawable.ic_arrow_back),
//                    null
//                )
//            }
//        },
//        title = {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                LessonEmoji(context = LocalContext.current, lessonName = lessonName)
//                Spacer(modifier = Modifier.size(16.dp))
//                Text(text = lessonName)
//            }
//        }, scrollBehavior = scrollBehavior
//    )
//}
//
//
//@Composable
//fun LessonEmoji(context: Context, lessonName: String) {
//    val bitmap =
//        AppCompatResources.getDrawable(
//            context,
//            getEmojiLesson(lessonName)?.emoji ?: R.drawable.ic_question,
//        )?.toBitmap()
//
//    val defaultColor = TypedValue()
//    context.theme.resolveAttribute(
//        com.google.android.material.R.attr.colorPrimaryContainer,
//        defaultColor,
//        true,
//    )
//    if (bitmap != null) {
//        val palette = Palette.from(bitmap).generate()
//        val vibrantColor = SolidColor(Color(palette.getVibrantColor(defaultColor.data)))
//        Box(
//            modifier = Modifier.background(
//                vibrantColor,
//                shape = RoundedCornerShape(8.dp),
//                alpha = 0.28f
//            )
//        ) {
//            Image(
//                bitmap = bitmap.asImageBitmap(),
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(4.dp)
//            )
//        }
//    }
//}
//
//
//@Preview
//@Composable
//private fun LessonTopBarPreview() {
//    MaterialTheme {
//        Scaffold(
//            topBar = {
//                LessonTopBar("Русский язык") {}
//            },
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Column(modifier = Modifier.padding(it)) {
//
//            }
//        }
//    }
//}
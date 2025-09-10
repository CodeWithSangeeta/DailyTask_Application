package com.practice.daily_task

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.practice.daily_task.database.TodoViewModel
import com.practice.daily_task.ui.theme.Daily_TaskTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val todoViewModel : TodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Daily_Task)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

            installSplashScreen().apply{
            setKeepOnScreenCondition{
                !todoViewModel.isDataReady.value
            }

                setOnExitAnimationListener {  screen ->
                    val zoomX = ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_X,
                        0.4f,
                        0.0f
                    )
                    zoomX.interpolator = OvershootInterpolator()
                    zoomX.duration = 500L
                    //zoomX.doOnEnd { screen.remove() }

                    val zoomY = ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_Y,
                        0.4f,
                        0.0f
                    )
                    zoomY.interpolator = OvershootInterpolator()
                    zoomY.duration = 500L
                   // zoomY.doOnEnd { screen.remove() }

//                    zoomX.start()
//                    zoomY.start()

                    AnimatorSet().apply {
                        playTogether(zoomX,zoomY)
                        doOnEnd {
                            screen.remove()
                        }
                        start()
                    }

                }

        }

        setContent {
            val todoViewModel: TodoViewModel = hiltViewModel()
            MyAppNavigation(viewModel = todoViewModel)
        }

    }
}


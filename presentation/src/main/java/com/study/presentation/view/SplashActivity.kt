package com.study.presentation.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import com.study.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.Instant
import kotlin.concurrent.thread

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        thread(start = true) {
            for (i in 1..5) {
                Thread.sleep(1000)
            }
            isReady = true
        }

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (isReady) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)

                        startActivity(Intent(this@SplashActivity,SignInFragment::class.java))
                        finish()

                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )

        // Add a callback that's called when the splash screen is animating to
        // the app content.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->

                // Get the duration of the animated vector drawable.
                val animationDuration = splashScreenView.iconAnimationDuration
                // Get the start time of the animation.
                val animationStart = splashScreenView.iconAnimationStart
                // Calculate the remaining duration of the animation.
                val remainingDuration = if (animationDuration != null && animationStart != null) {
                    (animationDuration - Duration.between(animationStart, Instant.now()))
                        .toMillis()
                        .coerceAtLeast(0L)
                } else {
                    0L
                }
                Log.e("test", "animationDuration - $remainingDuration")
                Log.e("test", "animationStart - $remainingDuration")
                Log.e("test", "remainingDuration - $remainingDuration")

                // Create your custom animation.
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 200L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        }


    }


}
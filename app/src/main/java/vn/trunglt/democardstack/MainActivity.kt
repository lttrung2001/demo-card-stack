package vn.trunglt.democardstack

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.trunglt.democardstack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var canClick: Boolean = true
    private var selectedId = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.main.addTransitionListener(object: TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                canClick = false
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                canClick = true
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })

        binding.buttonRight.setOnClickListener {
            if (!canClick) return@setOnClickListener
            when (selectedId) {
                0 -> {
                    selectedId = 1
                    binding.main.transitionToState(R.id.second_item_selected)
                }

                1 -> {
                    selectedId = 2
                    binding.main.transitionToState(R.id.third_item_selected)
                }

                2 -> {
                    selectedId = 0
                    binding.main.transitionToState(R.id.first_item_selected)
                }
            }
        }
    }
}
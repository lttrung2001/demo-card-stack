package vn.trunglt.democardstack

import android.os.Bundle
import android.view.View
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

        binding.main.addTransitionListener(object : TransitionListener {
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
                    binding.main.setupConstraintSet(
                        constraintSetId = R.id.second_item_selected,
                        centralViewId = R.id.image_view_card_2,
                        leftViewId = R.id.image_view_card_3,
                        rightViewId = R.id.image_view_card_1
                    )
                }

                1 -> {
                    selectedId = 2
                    binding.main.setupConstraintSet(
                        constraintSetId = R.id.third_item_selected,
                        centralViewId = R.id.image_view_card_3,
                        leftViewId = R.id.image_view_card_1,
                        rightViewId = R.id.image_view_card_2
                    )
                }

                2 -> {
                    selectedId = 0
                    binding.main.setupConstraintSet(
                        constraintSetId = R.id.first_item_selected,
                        centralViewId = R.id.image_view_card_1,
                        leftViewId = R.id.image_view_card_2,
                        rightViewId = R.id.image_view_card_3
                    )
                }
            }
        }
    }
}

fun View.percentWidthOf(percent: Int): Int {
    return right.minus(left).times(percent).div(100)
}

fun View.percentHeightOf(percent: Int): Int {
    return bottom.minus(top).times(percent).div(100)
}

fun MotionLayout.setupConstraintSet(constraintSetId: Int, centralViewId: Int, leftViewId: Int, rightViewId: Int) {
    cloneConstraintSet(constraintSetId).apply {
        var viewId = centralViewId
        var view = findViewById<View>(viewId)
        setTransformPivotX(
            viewId,
            view.percentWidthOf(50).toFloat()
        )
        setTransformPivotY(
            viewId,
            view.percentWidthOf(100).toFloat()
        )

        viewId = leftViewId
        view = findViewById(viewId)
        setTranslationY(
            viewId,
            (-view.percentHeightOf(5)).toFloat()
        )
        setTransformPivotX(
            viewId,
            view.percentWidthOf(30).toFloat()
        )
        setTransformPivotY(
            viewId,
            view.percentHeightOf(100).toFloat()
        )

        viewId = rightViewId
        view = findViewById(viewId)
        setTranslationY(
            viewId,
            (-view.percentHeightOf(5)).toFloat()
        )
        setTransformPivotX(
            viewId,
            view.percentWidthOf(70).toFloat()
        )
        setTransformPivotY(
            viewId,
            view.percentHeightOf(100).toFloat()
        )
    }.also {
        updateState(constraintSetId, it)
        transitionToState(constraintSetId)
    }
}
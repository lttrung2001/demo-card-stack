package vn.trunglt.democardstack

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import vn.trunglt.democardstack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val cardAdapter by lazy {
        CardAdapter()
    }
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

        val itemTouchHelper = ItemTouchHelper(
            SwipeToBringFrontCallback(
                cardAdapter
            )
        )
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = cardAdapter
        cardAdapter.submitList(
            listOf(
                android.R.color.holo_blue_dark,
                android.R.color.holo_red_dark,
                R.drawable.ic_launcher_background,
            )
        )
    }
}

fun View.percentWidthOf(percent: Int): Int {
    return right.minus(left).times(percent).div(100)
}

fun View.percentHeightOf(percent: Int): Int {
    return bottom.minus(top).times(percent).div(100)
}

fun MotionLayout.setupConstraintSet(
    constraintSetId: Int,
    centralViewId: Int,
    leftViewId: Int,
    rightViewId: Int
) {
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
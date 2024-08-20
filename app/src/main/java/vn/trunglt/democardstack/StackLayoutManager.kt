package vn.trunglt.democardstack

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutParams

class StackLayoutManager : LayoutManager() {
    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)
        println(itemCount)
        var offsetX = 0
        var offsetY = 0
        for (i in itemCount - 1 downTo 0) {
            val view = recycler.getViewForPosition(i).apply {
                val v = this
                v.pivotX = when (i) {
                    1 -> v.percentWidthOf(30).toFloat()
                    2 -> v.percentWidthOf(70).toFloat()
                    else -> v.percentWidthOf(50).toFloat()
                }
                v.pivotY = v.percentHeightOf(100).toFloat()
                v.rotation = when (i) {
                    1 -> -6f
                    2 -> 6f
                    else -> 0f
                }
                v.translationY = if (listOf(1, 2).contains(i)) {
                    (-v.percentHeightOf(5).toFloat())
                } else {
                    0f
                }
            }
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            offsetX = 100
            offsetY = 100
            layoutDecorated(view, offsetX, offsetY, offsetX + width, offsetY + height)
        }
    }
}

class SwipeToBringFrontCallback(private val adapter: CardAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val currentList = adapter.currentList.toMutableList()
        val oldItem = currentList[position]
        currentList.removeAt(position)
        currentList.add(oldItem)
        adapter.submitList(currentList)
    }
}
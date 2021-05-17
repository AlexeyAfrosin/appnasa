package com.afrosin.appnasa.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosin.appnasa.R
import com.afrosin.appnasa.databinding.ActivityRecyclerItemMarsBinding
import com.afrosin.appnasa.model.ToDoItem


class RecyclerToDoListAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<ToDoItem, Boolean>>,
    private var dragListener: OnStartDragListener
) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    private var _marsBinding: ActivityRecyclerItemMarsBinding? = null
    private val marsBinding get() = _marsBinding!!

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _marsBinding = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_MARS -> {
                _marsBinding = ActivityRecyclerItemMarsBinding.inflate(inflater, parent, false)
                MarsViewHolder(marsBinding.root)
            }
            else -> HeaderViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_header, parent, false) as View
            )

        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            else -> TYPE_MARS
        }

    }

    fun appendItem() {
        data.add(generateItem(data.size))
        notifyDataSetChanged()
    }

    private fun generateItem(maxId: Int) = Pair(ToDoItem(maxId + 1, "Mars", ""), false)

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

        @SuppressLint("ClickableViewAccessibility")
        override fun bind(dataItem: Pair<ToDoItem, Boolean>) {

            marsBinding.marsImageView.setOnClickListener {
                onListItemClickListener.onItemClick(
                    dataItem.first
                )
            }
            marsBinding.removeItemImageView.setOnClickListener { removeItem() }

            marsBinding.marsDescriptionTextView.visibility =
                if (dataItem.second) View.VISIBLE else View.GONE
            marsBinding.marsTextView.setOnClickListener { toggleText() }

            marsBinding.dragHandleImageView.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDragListener(this)
                }
                false
            }


        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)

        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }


    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(dataItem: Pair<ToDoItem, Boolean>) {
        }
    }


    interface OnListItemClickListener {
        fun onItemClick(data: ToDoItem)
    }

    interface OnStartDragListener {
        fun onStartDragListener(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


}
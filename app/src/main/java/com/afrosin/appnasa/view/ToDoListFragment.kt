package com.afrosin.appnasa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.afrosin.appnasa.databinding.FragmentToDoListBinding
import com.afrosin.appnasa.model.ToDoItem


class ToDoListFragment : Fragment() {

    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!

    lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: RecyclerToDoListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = arrayListOf(
            Pair(ToDoItem(1, "Mars", ""), false)
        )

        data.add(0, Pair(ToDoItem(0, "Header", ""), false))

        adapter = RecyclerToDoListAdapter(
            object : RecyclerToDoListAdapter.OnListItemClickListener {
                override fun onItemClick(data: ToDoItem) {
                    Toast.makeText(context, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : RecyclerToDoListAdapter.OnStartDragListener {
                override fun onStartDragListener(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }

            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener { adapter.appendItem() }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = ToDoListFragment()
    }

}
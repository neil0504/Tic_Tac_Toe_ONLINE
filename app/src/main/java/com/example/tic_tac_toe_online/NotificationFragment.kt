package com.example.tic_tac_toe_online

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationFragment : Fragment(R.layout.notification_fragment) {
    lateinit var recycler_view: RecyclerView
    lateinit var blogAdapter: BlogRecyclerViewAdapter
    lateinit var thiscontxt: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.notification_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view = view.findViewById(R.id.recycler_view)
        thiscontxt = view.context
        Toast.makeText(thiscontxt, "Fragment Created", Toast.LENGTH_SHORT).show()
        initRecyclerView()
        addDataset()

    }
    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(thiscontxt)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            blogAdapter = BlogRecyclerViewAdapter()
            adapter = blogAdapter

//        recycler_view.layoutManager = LinearLayoutManager(thiscontxt)



        }
    }

    private fun addDataset(){
        val data = DataSource.createDataSet()
        blogAdapter.submitList(data)
    }

}
package com.random_guys.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.javafaker.Faker
import com.random_guys.rv.LoadMoreListener
import com.random_guys.rv.RecyclerScrollMoreListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoadMoreListener {

    private val faker = Faker()
    private var theNames = ArrayList<String>()
    private lateinit var namesAdapter: NamesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerScrollMoreListener: RecyclerScrollMoreListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        namesAdapter = NamesAdapter(this)
        namesAdapter.loadMoreListener = this

        for (i in 0 until 1000) theNames.add(faker.name().nameWithMiddle())

        namesAdapter.add(theNames.subList(0, 20))

        linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = namesAdapter

        recyclerScrollMoreListener =
            RecyclerScrollMoreListener(linearLayoutManager, namesAdapter, 20)
        recyclerView.addOnScrollListener(recyclerScrollMoreListener)
    }

    override fun onLoadMore(page: Int, total: Int) {
        if (theNames.size >= total) {
            namesAdapter.add(theNames.subList(page * 10, (page * 10) + 10))
            recyclerView.post { namesAdapter.notifyItemInserted(namesAdapter.itemCount) }
        }
    }
}

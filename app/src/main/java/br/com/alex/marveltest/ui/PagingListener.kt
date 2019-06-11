package br.com.alex.marveltest.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingListener(private val gridLayoutManager: GridLayoutManager, private val pageOffset: Int):
    RecyclerView.OnScrollListener() {

    private var preLast:Int = 0
    private var page: Int = 0
    var offset: Int = 0
    private var pagingDataListener: PagingDataListener? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItems = gridLayoutManager.childCount
        val totalItems = gridLayoutManager.itemCount

        val firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition()
        val lastItem = firstVisiblePosition + visibleItems

        if (lastItem >= totalItems && firstVisiblePosition >= 0) {
            if(preLast != lastItem) {
                preLast = lastItem
                page += 1
                offset = page *pageOffset
                pagingDataListener!!.loadData()
            }
        }
    }

    fun addPagingListener(pagingListener: PagingDataListener){
        pagingDataListener = pagingListener
    }

    fun clearPaging(){
        preLast  = 0
        page = 0
        offset = 0
    }

    interface PagingDataListener {
        fun loadData()
    }
}
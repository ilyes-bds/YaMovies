package com.bdl.example.yamovies.paging

interface Pagination<Key, Item> {
    suspend fun loadNextPage()
    fun reset()
}
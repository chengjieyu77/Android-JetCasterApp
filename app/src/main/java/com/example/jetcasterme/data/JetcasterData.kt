package com.example.jetcasterme.data

import com.example.jetcasterme.model.CollectionName


class JetcasterDataSource{
    fun loadCollectionNames():List<CollectionName>{
        return listOf(
            CollectionName(uid = 1, collectionName = "Society & Culture"),
            CollectionName(uid = 2, collectionName = "News"),
            CollectionName(uid = 3, collectionName = "Technology"),
            CollectionName(uid = 4, collectionName = "Arts"),
            CollectionName(uid = 5, collectionName = "Health & Fitness"),
            CollectionName(uid = 6, collectionName = "Comedy"),
            CollectionName(uid = 7, collectionName = "Science"),
            CollectionName(uid = 8, collectionName = "True Crime"),
            CollectionName(uid = 9, collectionName = "TV & Film"),
            CollectionName(uid = 10, collectionName = "Sports"),
            CollectionName(uid = 11, collectionName = "Business"),
        )
    }


}
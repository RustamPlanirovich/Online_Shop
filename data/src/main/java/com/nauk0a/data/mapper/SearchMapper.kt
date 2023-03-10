package com.nauk0a.data.mapper

import com.nauk0a.data.models.SearchList
import com.nauk0a.domain.models.SearchListDomain
import com.nauk0a.data.models.Search
import com.nauk0a.domain.models.SearchDomain

fun SearchList.mapToDomain() = SearchListDomain(
    wordList = wordList
)

fun Search.mapToDomain() = SearchDomain(
    word = word
)
package br.com.alex.repository.mapper

interface ResultMapper<A, E> {
    fun fromEntityApi(a: A): E
    fun toEntityApi(e: E): A
}
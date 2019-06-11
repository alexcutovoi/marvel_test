package br.com.alex.repository.mapper

interface EntityMapper <E, M> {
    fun fromEntity(e: E): M
    fun toEntity(m: M): E
}
package me.windock.duplemapper

import org.springframework.data.repository.CrudRepository

interface AuthorRepository : CrudRepository<Author, Long> {
}
package me.windock.duplemapper

import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, Long> {

}

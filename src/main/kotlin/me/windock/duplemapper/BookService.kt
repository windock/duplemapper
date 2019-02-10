package me.windock.duplemapper

import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {
    fun book(id: Long): Book {
        val book = bookRepository.findById(id).orElseThrow { RuntimeException("NotFound") }
        println("Book loaded")
        return book
    }
}
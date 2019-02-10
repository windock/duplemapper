package me.windock.duplemapper.web

import me.windock.duplemapper.BookService
import org.springframework.web.bind.annotation.*

@RestController
class BookHandler(val bookService: BookService) {
    @GetMapping("/books/{id}")
    fun book(@PathVariable("id") id: Long): Any {
        val book = bookService.book(id)
        return book
    }
}
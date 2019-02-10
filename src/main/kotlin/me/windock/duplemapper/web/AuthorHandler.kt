package me.windock.duplemapper.web

import me.windock.duplemapper.Book
import me.windock.duplemapper.AuthorService
import me.windock.duplemapper.BookRepository
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.web.bind.annotation.*

@RestController
class AuthorHandler(private val authorService: AuthorService, private val bookRepository: BookRepository) {
    data class AuthorDTO(val id: Long?, val name: String?, val books: List<Book>?)
    data class AuthorV2DTO(val _id: Long?, val authorName: String?, val bookIds: List<Long>?)
    open class AuthorHateoas(val id: Long?, val name: String?, val books: List<Long>?) : ResourceSupport() {

    }

    @GetMapping("/authors/{id}")
    fun author(@PathVariable("id") id: Long): AuthorDTO {
        return authorService.author(id) { AuthorDTO(it.id, it.name, it.books.toList()) }
    }

    @GetMapping("/authors-v2/{id}")
    fun authorV2(@PathVariable("id") id: Long): AuthorV2DTO {
        return authorService.author(id) { AuthorV2DTO(it.id, it.name, it.books.map { it.id } ) }
    }

    @GetMapping("/authors-v3/{id}")
    fun authorV3(@PathVariable("id") id: Long): AuthorHateoas {
        return authorService.author(id) {
            val authorHateoas = AuthorHateoas(it.id, it.name, it.books.map { it.id })
            authorHateoas.add(linkTo(methodOn(AuthorHandler::class.java).authorV3(it.id)).withSelfRel())

            it.books.map {
                authorHateoas.add(linkTo(methodOn(BookHandler::class.java).book(it.id)).withRel("books"))
            }

            authorHateoas
        }
    }

    @PutMapping("/authors/{id}")
    fun updateAuthor(@PathVariable("id") id: Long, @RequestBody authorDTO: AuthorDTO): AuthorDTO {
        return authorService.updateAuthor(id, {
            if (authorDTO.name != null)
                it.name = authorDTO.name
        }, {
            AuthorDTO(it.id, it.name, it.books.toList())
        })
    }

    @PutMapping("/authors-v2/{id}")
    fun updateAuthorV2(@PathVariable("id") id: Long, @RequestBody authorDTO: AuthorV2DTO): AuthorV2DTO {
        return authorService.updateAuthor(id, {
            if (authorDTO.authorName != null)
                it.name = authorDTO.authorName
            if (authorDTO.bookIds != null)
                it.setBooks(bookRepository.findAllById(authorDTO.bookIds).toList())
        }, {
            AuthorV2DTO(it.id, it.name, it.books.map(Book::id))
        })
    }
}
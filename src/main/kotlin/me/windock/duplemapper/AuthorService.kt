package me.windock.duplemapper

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AuthorService(val authorRepository: AuthorRepository) {
    @Transactional
    fun <T : Any> author(id: Long, function: (Author) -> T): T =
        function(authorRepository.findById(id).orElseThrow { RuntimeException("NotFound") })

    @Transactional
    fun <T : Any> updateAuthor(id: Long, updateAuthor: (Author) -> Unit, mapAuthor: (Author) -> T): T {
        val author = authorRepository.findById(id).orElseThrow { RuntimeException("NotFound") }
        updateAuthor(author)
        val savedAuthor = authorRepository.save(author)
        return mapAuthor(savedAuthor)
    }
}
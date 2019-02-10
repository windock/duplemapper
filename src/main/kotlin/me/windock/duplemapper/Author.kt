package me.windock.duplemapper

import javax.persistence.*

@Entity
class Author {
    @Id
    @GeneratedValue
    var id: Long = 0

    var name: String = ""

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = [(CascadeType.ALL)])
    var books: MutableList<Book> = mutableListOf()

    fun setBooks(books: Collection<Book>) {
        this.books.retainAll(books)
        books.map { it.author = this; this.books.add(it) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Author

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return 31
    }
}
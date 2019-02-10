package me.windock.duplemapper

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Book {
    @Id
    @GeneratedValue
    var id: Long = 0

    var title: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    var author: Author? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return 31
    }
}

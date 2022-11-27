package ru.fellowcar.users

import org.springframework.data.repository.CrudRepository
import ru.fellowcar.users.models.User

interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username : String): User?
}

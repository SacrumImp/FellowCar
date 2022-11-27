package ru.fellowcar.users

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import ru.fellowcar.users.models.User

@DataJpaTest
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val juergen = User("jurhoe", "Hoeller", email = "jhoeller@hotmail.co", password = "banana")
        entityManager.persist(juergen)
        entityManager.flush()
        val found = userRepository.findByUsername(juergen.username)
        assertThat(found).isEqualTo(juergen)
    }
//
//    @Test
//    fun `When findByLogin then return User`() {
//        val juergen = User("springjuergen", "Juergen", "Hoeller")
//        entityManager.persist(juergen)
//        entityManager.flush()
//        val user = userRepository.findByLogin(juergen.login)
//        assertThat(user).isEqualTo(juergen)
//    }
}


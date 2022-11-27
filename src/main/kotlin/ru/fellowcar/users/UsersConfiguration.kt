package ru.fellowcar.users

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.fellowcar.users.models.User

@Configuration
class UsersConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository) = ApplicationRunner {
        val juergen = User("jurhoe", "Hoeller", email = "jhoeller@hotmail.co", password = "banana")
        userRepository.save(juergen)

//        val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
//        articleRepository.save(Article(
//            title = "Reactor Bismuth is out",
//            headline = "Lorem ipsum",
//            content = "dolor sit amet",
//            author = smaldini
//        ))
//        articleRepository.save(Article(
//            title = "Reactor Aluminium has landed",
//            headline = "Lorem ipsum",
//            content = "dolor sit amet",
//            author = smaldini
//        ))
    }
}

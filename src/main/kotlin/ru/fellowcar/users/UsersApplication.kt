package ru.fellowcar.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@ConfigurationProperties("usersmicroservice")
@ConstructorBinding
data class UserApplicationProperties(val publisher: String, val key: String) {

}

//@RestController
//@RequestMapping("/api/users")
//class HttpController {
//
//}

@SpringBootApplication
@EnableConfigurationProperties(UserApplicationProperties::class)
class UsersApplication

fun main(args: Array<String>) {
    runApplication<UsersApplication>(*args)
}

package ru.fellowcar.users.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.fellowcar.users.UserApplicationProperties
import ru.fellowcar.users.UserRepository
import ru.fellowcar.users.models.User
import java.io.IOException
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/api/users")
class UsersApiController @Autowired constructor(
    objectMapper: ObjectMapper,
    request: HttpServletRequest,
    val userRepository: UserRepository,
    val properties: UserApplicationProperties
) {
    private val objectMapper: ObjectMapper
    private val request: HttpServletRequest

    init {
        this.objectMapper = objectMapper
        this.request = request
    }

    @RequestMapping(value = ["/me/profile"], produces = ["application/json"], method = [RequestMethod.GET])
    fun getMeUser(): User {
        val accept: String = request.getHeader("Accept")
        val tokenHeader = request.getHeader("Authorization")
        val claims = Jwts.parser().setSigningKey(properties.key.toByteArray(Charsets.US_ASCII)).parse(tokenHeader)
        log.info(request.getHeader("Authorization"), properties.key, claims)
        return try {
            val user = userRepository.findAll().first()
            user
        } catch (e: IOException) {
            log.error("Couldn't serialize response for content type application/json", e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

//    override fun getUserChats(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?,
//    ): ResponseEntity<ChatList?>? {
//        val accept: String = request.getHeader("Accept")
//        return if (accept != null && accept.contains("application/json")) {
//            try {
//                ResponseEntity<ChatList>(objectMapper.readValue("{\n  \"chats\" : [ {\n    \"name\" : \"name\",\n    \"id\" : 0\n  }, {\n    \"name\" : \"name\",\n    \"id\" : 0\n  } ]\n}",
//                    ChatList::class.java), HttpStatus.NOT_IMPLEMENTED)
//            } catch (e: IOException) {
//                log.error("Couldn't serialize response for content type application/json", e)
//                ResponseEntity<ChatList>(HttpStatus.INTERNAL_SERVER_ERROR)
//            }
//        } else ResponseEntity<ChatList>(HttpStatus.NOT_IMPLEMENTED)
//    }
//
//    override fun getUserCommunities(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?,
//        @Parameter(`in` = ParameterIn.DEFAULT,
//            description = "",
//            schema = Schema()) @Valid @RequestBody body: Any?,
//    ): ResponseEntity<UserCommunitiesResponse?>? {
//        val accept: String = request.getHeader("Accept")
//        return if (accept != null && accept.contains("application/json")) {
//            try {
//                ResponseEntity<UserCommunitiesResponse>(objectMapper.readValue("{\n  \"joined\" : [ {\n    \"members\" : [ null, null ],\n    \"name\" : \"HSE\",\n    \"description\" : \"Carpooling community for the students or th HSE University\",\n    \"location\" : \"[-123.10664756, 49.28261413]\",\n    \"id\" : 1,\n    \"dateOfCreation\" : \"2012-04-23T18:25:43.511Z\"\n  }, {\n    \"members\" : [ null, null ],\n    \"name\" : \"HSE\",\n    \"description\" : \"Carpooling community for the students or th HSE University\",\n    \"location\" : \"[-123.10664756, 49.28261413]\",\n    \"id\" : 1,\n    \"dateOfCreation\" : \"2012-04-23T18:25:43.511Z\"\n  } ],\n  \"managed\" : [ null, null ]\n}",
//                    UserCommunitiesResponse::class.java), HttpStatus.NOT_IMPLEMENTED)
//            } catch (e: IOException) {
//                log.error("Couldn't serialize response for content type application/json", e)
//                ResponseEntity<UserCommunitiesResponse>(HttpStatus.INTERNAL_SERVER_ERROR)
//            }
//        } else ResponseEntity<UserCommunitiesResponse>(HttpStatus.NOT_IMPLEMENTED)
//    }
//
//    override fun getUserProfile(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?,
//    ): ResponseEntity<User?>? {
//        val accept: String = request.getHeader("Accept")
//        return if (accept != null && accept.contains("application/json")) {
//            try {
//                ResponseEntity<User>(objectMapper.readValue("{\n  \"driversLicence\" : \"path/to/driversLicence\",\n  \"lastName\" : \"Kuev\",\n  \"lastLogin\" : \"2012-04-23T18:25:43.511Z\",\n  \"raiting\" : 5,\n  \"userTypes\" : 1,\n  \"password\" : \"ajs3easl=-/\",\n  \"passport\" : \"path/to/id/passport\",\n  \"phone\" : \"+7 999 (555) 164 9\",\n  \"trips\" : [ {\n    \"date\" : \"2012-04-23T18:25:43.511Z\",\n    \"destiny\" : \"Moscow, Pokrovskiy blvd, 11, build Z, room T203\",\n    \"origin\" : \"Myasnitskaya Ulitsa, 20, Moscow\",\n    \"id\" : 1\n  }, {\n    \"date\" : \"2012-04-23T18:25:43.511Z\",\n    \"destiny\" : \"Moscow, Pokrovskiy blvd, 11, build Z, room T203\",\n    \"origin\" : \"Myasnitskaya Ulitsa, 20, Moscow\",\n    \"id\" : 1\n  } ],\n  \"name\" : \"Alexander\",\n  \"profilepicture\" : \"/path/to/id/picture\",\n  \"id\" : 10,\n  \"email\" : \"alek@gmail.com\",\n  \"username\" : \"theUser\",\n  \"communities\" : [ {\n    \"members\" : [ null, null ],\n    \"name\" : \"HSE\",\n    \"description\" : \"Carpooling community for the students or th HSE University\",\n    \"location\" : \"[-123.10664756, 49.28261413]\",\n    \"id\" : 1,\n    \"dateOfCreation\" : \"2012-04-23T18:25:43.511Z\"\n  }, {\n    \"members\" : [ null, null ],\n    \"name\" : \"HSE\",\n    \"description\" : \"Carpooling community for the students or th HSE University\",\n    \"location\" : \"[-123.10664756, 49.28261413]\",\n    \"id\" : 1,\n    \"dateOfCreation\" : \"2012-04-23T18:25:43.511Z\"\n  } ]\n}",
//                    User::class.java), HttpStatus.NOT_IMPLEMENTED)
//            } catch (e: IOException) {
//                log.error("Couldn't serialize response for content type application/json", e)
//                ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR)
//            }
//        } else ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED)
//    }
//
//    override fun getUserRides(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?,
//    ): ResponseEntity<Trip?>? {
//        val accept: String = request.getHeader("Accept")
//        return if (accept != null && accept.contains("application/json")) {
//            try {
//                ResponseEntity<Trip>(objectMapper.readValue("{\n  \"date\" : \"2012-04-23T18:25:43.511Z\",\n  \"destiny\" : \"Moscow, Pokrovskiy blvd, 11, build Z, room T203\",\n  \"origin\" : \"Myasnitskaya Ulitsa, 20, Moscow\",\n  \"id\" : 1\n}",
//                    Trip::class.java), HttpStatus.NOT_IMPLEMENTED)
//            } catch (e: IOException) {
//                log.error("Couldn't serialize response for content type application/json", e)
//                ResponseEntity<Trip>(HttpStatus.INTERNAL_SERVER_ERROR)
//            }
//        } else ResponseEntity<Trip>(HttpStatus.NOT_IMPLEMENTED)
//    }
//
//    override fun patchUserMe(
//        @Parameter(`in` = ParameterIn.DEFAULT,
//            description = "",
//            schema = Schema()) @Valid @RequestBody body: Any?,
//    ): ResponseEntity<User?>? {
//        val accept: String = request.getHeader("Accept")
//        return if (accept != null && accept.contains("application/json")) {
//            try {
//                ResponseEntity<User>(objectMapper.readValue("{\n  \"driversLicence\" : \"path/to/driversLicence\",\n  \"lastName\" : \"Kuev\",\n  \"lastLogin\" : \"2012-04-23T18:25:43.511Z\",\n  \"raiting\" : 5,\n  \"userTypes\" : 1,\n  \"password\" : \"ajs3easl=-/\",\n  \"passport\" : \"path/to/id/passport\",\n  \"phone\" : \"+7 999 (555) 164 9\",\n  \"trips\" : [ {\n    \"date\" : \"2012-04-23T18:25:43.511Z\",\n    \"destiny\" : \"Moscow, Pokrovskiy blvd, 11, build Z, room T203\",\n    \"origin\" : \"Myasnitskaya Ulitsa, 20, Moscow\",\n    \"id\" : 1\n  }, {\n    \"date\" : \"2012-04-23T18:25:43.511Z\",\n    \"destiny\" : \"Moscow, Pokrovskiy blvd, 11, build Z, room T203\",\n    \"origin\" : \"Myasnitskaya Ulitsa, 20, Moscow\",\n    \"id\" : 1\n  } ],\n  \"name\" : \"Alexander\",\n  \"profilepicture\" : \"/path/to/id/picture\",\n  \"id\" : 10,\n  \"email\" : \"alek@gmail.com\",\n  \"username\" : \"theUser\",\n  \"communities\" : [ {\n    \"members\" : [ null, null ],\n    \"name\" : \"HSE\",\n    \"description\" : \"Carpooling community for the students or th HSE University\",\n    \"location\" : \"[-123.10664756, 49.28261413]\",\n    \"id\" : 1,\n    \"dateOfCreation\" : \"2012-04-23T18:25:43.511Z\"\n  }, {\n    \"members\" : [ null, null ],\n    \"name\" : \"HSE\",\n    \"description\" : \"Carpooling community for the students or th HSE University\",\n    \"location\" : \"[-123.10664756, 49.28261413]\",\n    \"id\" : 1,\n    \"dateOfCreation\" : \"2012-04-23T18:25:43.511Z\"\n  } ]\n}",
//                    User::class.java), HttpStatus.NOT_IMPLEMENTED)
//            } catch (e: IOException) {
//                log.error("Couldn't serialize response for content type application/json", e)
//                ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR)
//            }
//        } else ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED)
//    }
//
//    override fun rateUser(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?,
//        @Parameter(`in` = ParameterIn.DEFAULT,
//            description = "",
//            schema = Schema()) @Valid @RequestBody body: Any?,
//    ): ResponseEntity<UserRate?>? {
//        val accept: String = request.getHeader("Accept")
//        return if (accept != null && accept.contains("application/json")) {
//            try {
//                ResponseEntity<UserRate>(objectMapper.readValue("{\n  \"rate\" : 0\n}", UserRate::class.java),
//                    HttpStatus.NOT_IMPLEMENTED)
//            } catch (e: IOException) {
//                log.error("Couldn't serialize response for content type application/json", e)
//                ResponseEntity<UserRate>(HttpStatus.INTERNAL_SERVER_ERROR)
//            }
//        } else ResponseEntity<UserRate>(HttpStatus.NOT_IMPLEMENTED)
//    }

    companion object {
        private val log = LoggerFactory.getLogger(UsersApiController::class.java)
    }
}

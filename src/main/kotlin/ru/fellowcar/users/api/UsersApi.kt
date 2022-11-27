///**
// * NOTE: This class is auto generated by the swagger code generator program (3.0.36).
// * https://github.com/swagger-api/swagger-codegen
// * Do not edit the class manually.
// */
//package ru.fellowcar.users.api
//
//import org.springframework.http.ResponseEntity
//import org.springframework.validation.annotation.Validated
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod
//import ru.fellowcar.users.models.User
//import javax.annotation.Generated
//
//@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2022-11-27T13:27:49.806Z[GMT]")
//@Validated
//interface UsersApi {
//    @get:RequestMapping(value = ["/users/me/profile"], produces = ["application/json"], method = [RequestMethod.GET])
//    @get:ApiResponses(value = [ApiResponse(responseCode = "200",
//        description = "Successful operation",
//        content = Content(mediaType = "application/json", schema = Schema(implementation = User::class))), ApiResponse(
//        responseCode = "400",
//        description = "Invalid ID supplied.")])
//    @Operation(summary = "Get users profile",
//        description = "Get the user profile",
//        security = [SecurityRequirement(name = "user_auth",
//            scopes = ["write_me", "read_me", "read_users", "read_users_chats", "read_users_rides", "write_users_rate", "read_users_communities"])],
//        tags = ["user"])
//    val user: ResponseEntity<User?>?
//
//    @Operation(summary = "Get user chats",
//        description = "Get chats of the user",
//        security = [SecurityRequirement(name = "user_auth",
//            scopes = ["write_me", "read_me", "read_users", "read_users_chats", "read_users_rides", "write_users_rate", "read_users_communities"])],
//        tags = ["user"])
//    @ApiResponses(value = [ApiResponse(responseCode = "200",
//        description = "Successful operation",
//        content = Content(mediaType = "application/json",
//            schema = Schema(implementation = ChatList::class))), ApiResponse(responseCode = "400",
//        description = "Invalid ID supplied.")])
//    @RequestMapping(value = ["/users/{userId}/chats"], produces = ["application/json"], method = [RequestMethod.GET])
//    fun getUserChats(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?
//    ): ResponseEntity<ChatList?>?
//
//    @Operation(summary = "Get user joined or managed communities",
//        description = "Get user joined or managed communities",
//        security = [SecurityRequirement(name = "user_auth",
//            scopes = ["write_me", "read_me", "read_users", "read_users_chats", "read_users_rides", "write_users_rate", "read_users_communities"])],
//        tags = ["user"])
//    @ApiResponses(value = [ApiResponse(responseCode = "200",
//        description = "Successful operation",
//        content = Content(mediaType = "application/json",
//            schema = Schema(implementation = UserCommunitiesResponse::class))), ApiResponse(responseCode = "400",
//        description = "Invalid ID supplied.")])
//    @RequestMapping(value = ["/users/{userId}/communities"],
//        produces = ["application/json"],
//        consumes = ["application/json"],
//        method = [RequestMethod.POST])
//    fun getUserCommunities(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?,
//        @Parameter(`in` = ParameterIn.DEFAULT,
//            description = "",
//            schema = Schema()) @Valid @RequestBody body: Any?
//    ): ResponseEntity<UserCommunitiesResponse?>?
//
//    @Operation(summary = "Get specific user profile",
//        description = "Get specific user profile",
//        security = [SecurityRequirement(name = "user_auth",
//            scopes = ["write_me", "read_me", "read_users", "read_users_chats", "read_users_rides", "write_users_rate", "read_users_communities"])],
//        tags = ["user"])
//    @ApiResponses(value = [ApiResponse(responseCode = "200",
//        description = "Successful operation",
//        content = Content(mediaType = "application/json", schema = Schema(implementation = User::class))), ApiResponse(
//        responseCode = "400",
//        description = "Invalid ID supplied.")])
//    @RequestMapping(value = ["/users/{userId}/profile"], produces = ["application/json"], method = [RequestMethod.GET])
//    fun getUserProfile(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?
//    ): ResponseEntity<User?>?
//
//    @Operation(summary = "Get rides that the user has",
//        description = "Get rides that the user has",
//        security = [SecurityRequirement(name = "user_auth",
//            scopes = ["write_me", "read_me", "read_users", "read_users_chats", "read_users_rides", "write_users_rate", "read_users_communities"])],
//        tags = ["user"])
//    @ApiResponses(value = [ApiResponse(responseCode = "200",
//        description = "Successful operation",
//        content = Content(mediaType = "application/json", schema = Schema(implementation = Trip::class))), ApiResponse(
//        responseCode = "400",
//        description = "Invalid ID supplied.")])
//    @RequestMapping(value = ["/users/{userId}/rides"], produces = ["application/json"], method = [RequestMethod.GET])
//    fun getUserRides(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?
//    ): ResponseEntity<Trip?>?
//
//    @Operation(summary = "Patch my  profile",
//        description = "Patch my  profile",
//        security = [SecurityRequirement(name = "user_auth",
//            scopes = ["write_me", "read_me", "read_users", "read_users_chats", "read_users_rides", "write_users_rate", "read_users_communities"])],
//        tags = ["user"])
//    @ApiResponses(value = [ApiResponse(responseCode = "200",
//        description = "Successful operation",
//        content = Content(mediaType = "application/json", schema = Schema(implementation = User::class))), ApiResponse(
//        responseCode = "400",
//        description = "Invalid ID supplied.")])
//    @RequestMapping(value = ["/users/me/profile"],
//        produces = ["application/json"],
//        consumes = ["application/json"],
//        method = [RequestMethod.PATCH])
//    fun patchUserMe(
//        @Parameter(`in` = ParameterIn.DEFAULT,
//            description = "",
//            schema = Schema()) @Valid @RequestBody body: Any?
//    ): ResponseEntity<User?>?
//
//    @Operation(summary = "rates the user",
//        description = "any user can rate the other user",
//        security = [SecurityRequirement(name = "user_auth",
//            scopes = ["write_me", "read_me", "read_users", "read_users_chats", "read_users_rides", "write_users_rate", "read_users_communities"])],
//        tags = ["user"])
//    @ApiResponses(value = [ApiResponse(responseCode = "200",
//        description = "Successful operation",
//        content = Content(mediaType = "application/json",
//            schema = Schema(implementation = UserRate::class))), ApiResponse(responseCode = "400",
//        description = "Invalid ID supplied.")])
//    @RequestMapping(value = ["/users/{userId}/rate"],
//        produces = ["application/json"],
//        consumes = ["application/json"],
//        method = [RequestMethod.POST])
//    fun rateUser(
//        @Parameter(`in` = ParameterIn.PATH,
//            description = "ID of the user",
//            required = true,
//            schema = Schema()) @PathVariable("userId") userId: Long?,
//        @Parameter(`in` = ParameterIn.DEFAULT,
//            description = "",
//            schema = Schema()) @Valid @RequestBody body: Any?
//    ): ResponseEntity<UserRate?>?
//}
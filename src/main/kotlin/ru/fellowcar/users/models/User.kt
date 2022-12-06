package ru.fellowcar.users.models

import java.sql.Date
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue
    var id: Long? = null,
    var username: String,
    var name: String,
    var email: String,
    var password: String,
    var adminId : Long?,
    var driverId : Long?,
    var passengerId : Long?,
//    val phone: String? = null,
//    val profilepicture: String? = null,
//    val passport: String? = null,
//    val driversLicence: String? = null,
//    val raiting: Int? = null,

//    val trips: Array<Trip>? = null,
//    @ManyToOne
//    val communitiesIds: List<String>? = null,
//    val lastLogin: String? = null,    /* User types */
//    val userTypes: Array<UserType>? = null,
) {
}

@Entity
class PassengerReview(
    @Id @GeneratedValue
    var id : Long? = null,
    @ManyToOne
    var passenger: Passenger,
    var authorUserName : String,
    var rating : Int,
    var body : String,
)

@Entity
class DriverReview(
    @Id @GeneratedValue
    var id : Long? = null,
    @ManyToOne
    var driver: Driver,
    var authorUserName : String,
    var rating : Int,
    var body : String,
)

@Entity
class Passenger(
    // TODO: 06.12.2022 try to use here the same id as for user?
    @Id @GeneratedValue
    var id: Long? = null,
    var user : Long,
    @OneToMany()
    var reviews: List<PassengerReview>,
)

@Entity
class License(
    @Id @GeneratedValue
    var id : Long? = null,
    @ManyToOne()
    var driver : Driver,
    var validUntil: Date,
    var photoArchiveFile : String,
    var verified: Boolean,
)

@Entity
class Driver(
    @Id @GeneratedValue
    var id : Long? = null,
    var user : Long,
    @OneToMany()
    var reviews : List<DriverReview>,
    @OneToMany
    var documents : List<License>
)

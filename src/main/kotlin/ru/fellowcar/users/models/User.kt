package ru.fellowcar.users.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
    var username: String,
    var name: String,
    var email: String,
    var password: String,
    @Id @GeneratedValue
    var id: Long? = null,
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

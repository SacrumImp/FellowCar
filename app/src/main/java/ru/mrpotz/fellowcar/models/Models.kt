package ru.mrpotz.fellowcar.models

import ru.mrpotz.fellowcar.logics.ValidEmail

data class CarSpecification(val description: String)

sealed class Role {
    class PassengerRole(

    ) : Role()

    class DriverRole(
        val isVerified: Boolean,
        val carSpecifications: CarSpecification,
    ) : Role()

    class AdministratorRole(
        val leadCommunities: List<Community>
    ) : Role() { // for now - just community administrator
    }
}

@JvmInline
value class Rating(val rating: Double) {
    companion object {
        @JvmStatic
        fun create(rating: Double): Rating {
            return Rating(rating.coerceAtLeast(0.0).coerceAtMost(5.0))
        }
    }
}

data class Profile(val description : String?)

data class User(
    val id: String,
    val name: String,
    val email : ValidEmail,
    val roles: List<Role>,
    val joinedCommunities: List<Community>,
    val rating: Double?,
    val profile: Profile
) {

}

interface LocationData

interface RideSpecification {
    val startTimeISO: String
    val endTimeISO: String
    val startLocation: LocationData
    val destinationLocation: LocationData
}

data class PassengerRideSpecification(
    val name: String,
    override val startTimeISO: String,
    override val endTimeISO: String,
    override val startLocation: LocationData,
    override val destinationLocation: LocationData,
) : RideSpecification

data class PlainRideSpecification(
    override val startTimeISO: String,
    override val endTimeISO: String,
    override val startLocation: LocationData,
    override val destinationLocation: LocationData,
) : RideSpecification

data class DriverProvisionedRide(
    val id: String,
    val driverId: String,
    val name: String,
    val plainRideSpecification: PlainRideSpecification
    // requests amount?
)

sealed class Request(
    open val id: String,
    open val status: Status,

    ) {
    sealed class Status {
        object InReview : Status()
        class Declined(val reasonSpecification: String? = null) : Status()
        object Planned : Status()
    }

    data class PassengerRequest(
        override val id: String,
        override val status: Status,
        val ride: PassengerRideSpecification,
        val suggestedRides: List<DriverProvisionedRide>
    ) : Request(id, status)

    data class CommunityRequest(
        override val id: String,
        override val status: Status,
        val communityId: String,
    ) :
        Request(id, status)
}

data class CommunityPolicy(
    val text: String,
)

data class Community(
    val id: String,
    val communityPolicy: CommunityPolicy,
    val administrator: User,
)

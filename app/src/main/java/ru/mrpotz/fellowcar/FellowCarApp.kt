package ru.mrpotz.fellowcar

import android.app.Application
import ru.mrpotz.fellowcar.logics.UserRepository

class Dependencies(
    val userManager : UserRepository,
) {

}

class FellowCarApp : Application() {
    private lateinit var dependencies: Dependencies


    override fun onCreate() {
        dependencies = Dependencies(UserRepository())

        super.onCreate()
    }
}

package ru.mrpotz.fellowcar

import android.app.Application
import ru.mrpotz.fellowcar.logics.UserManager

class Dependencies(
    val userManager : UserManager,
) {

}

class FellowCarApp : Application() {
    private lateinit var dependencies: Dependencies


    override fun onCreate() {
        dependencies = Dependencies(UserManager())

        super.onCreate()
    }
}

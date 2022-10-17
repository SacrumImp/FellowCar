package ru.mrpotz.fellowcar

import android.app.Application
import android.util.Log
import ru.mrpotz.fellowcar.logics.UserLocalConverter
import ru.mrpotz.fellowcar.logics.UserRepository

class Dependencies(
    val userManager: UserRepository,
) {

}

class FellowCarApp : Application() {
    private lateinit var dependencies: Dependencies


    override fun onCreate() {
        val dependencies = Dependencies(UserRepository(context = this.baseContext, userLocalConverter = UserLocalConverter()))
        this.dependencies = dependencies
        _dependencies = dependencies
        Log.d("FellowCarApp", "dependencies: $dependencies")
        super.onCreate()
    }

    companion object {
        private var _dependencies: Dependencies? = null
        val dependencies: Dependencies
            get() = checkNotNull(_dependencies)
    }
}

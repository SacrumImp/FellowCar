package ru.mrpotz.fellowcar.ui.screens.home

sealed class NavTarget {
    object Scheduling : NavTarget()
    object Carpoolers : NavTarget()
    object Rewards : NavTarget()
    object Profile : NavTarget()
}

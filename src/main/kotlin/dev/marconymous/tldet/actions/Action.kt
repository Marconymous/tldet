package dev.marconymous.tldet.actions

object Action {
    fun assuming(condition: Boolean, action: () -> Unit) {
        if (condition) action()
    }

    fun assuming(condition: Boolean, action: () -> Unit, z: () -> Unit) {
        if (condition) action() else z()
    }

    fun assuming(condition: () -> Boolean, action: () -> Unit) {
        if (condition()) action()
    }

    fun assuming(condition: () -> Boolean, action: () -> Unit, z: () -> Unit) {
        if (condition()) action() else z()
    }
}
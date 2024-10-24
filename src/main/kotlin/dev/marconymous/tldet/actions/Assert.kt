package dev.marconymous.tldet.actions

object Assert {
    operator fun invoke(condition: Boolean, message: String = "Assertion failed") {
        Action.assuming(condition) {
            throw AssertionError(message)
        }
    }

    operator fun invoke(condition: () -> Boolean, message: String = "Assertion failed") {
        invoke(condition(), message)
    }
}
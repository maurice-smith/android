package com.kingdom.repository

class GreetingRepository {

    suspend fun getAlienGreeting(): String {
        return "Greetings Earthlings."
    }
}
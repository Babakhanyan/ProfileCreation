package com.example.data

import com.example.domain.Portfolio
import com.example.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserService @Inject constructor(
    private val userRepository: UserRepository
) {

    fun savePortfolio(data: Portfolio) {
        userRepository.savePortfolio(data)
    }

    fun getPortfolio(): Flow<Portfolio> {
        return userRepository.getPortfolio()
    }
}
package com.example.calorietracker.redux.actions

import com.example.calorietracker.models.network.UserResponse

class SucceedFetchingUser(val user: UserResponse) : ReduxAction

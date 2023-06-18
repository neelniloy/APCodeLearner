package com.projitize.apcodelearner.models

data class Users(
    var userId: String? = null,
    var userName: String? = null,
    var emailAddress: String? = null,
    var userType: String? = null,
    var userCreationTimeStamp: Long? = null,
    var quizScore: Int? = null,
    var tutorialCount: Int? = 0,
)

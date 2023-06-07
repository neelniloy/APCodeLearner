package com.braineer.scheduler.models

data class Users(
    var userId: String? = null,
    var userName: String? = null,
    var emailAddress: String? = null,
    var userCreationTimeStamp: Long? = null,
    var routineKey: String? = null,
    var importKey: String? = null,
    var importTime: Long? = null,
    var lastModification: Long? = null
)

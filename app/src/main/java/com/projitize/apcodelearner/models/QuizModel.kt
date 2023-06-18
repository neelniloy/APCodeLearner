package com.projitize.apcodelearner.models

data class QuizModel(
    var question: String? = null,
    var option1: String? = null,
    var option2: String? = null,
    var option3: String? = null,
    var option4: String? = null,
    var answer: String? = null,
    var time: Long? = null,
)

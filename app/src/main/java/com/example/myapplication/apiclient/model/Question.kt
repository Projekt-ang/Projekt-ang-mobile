package com.example.myapplication.apiclient.model

data class Question(val text: String, val correctAnswer: Int, val answers: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (text != other.text) return false
        if (correctAnswer != other.correctAnswer) return false
        if (!answers.contentEquals(other.answers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + correctAnswer
        result = 31 * result + answers.contentHashCode()
        return result
    }

}
package com.example.myapplication.apiclient.model

data class ReadingWithTest(val text: String, val link: String?, val question: Array<Question>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReadingWithTest

        if (text != other.text) return false
        if (link != other.link) return false
        if (!question.contentEquals(other.question)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + question.contentHashCode()
        return result
    }

}
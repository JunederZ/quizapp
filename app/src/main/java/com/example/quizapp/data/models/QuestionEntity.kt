package com.example.quizapp.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = QuizEntity::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("quizId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    var title: String,
    val quizId: Int
)
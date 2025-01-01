package com.example.quizapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quizapp.data.models.WholeQuestion
import com.example.quizapp.ui.components.EditAnswerOption
import com.example.quizapp.ui.viewmodels.EditQuestionViewModel

@Composable
fun EditQuestionScreen(
    questionId: Int,
    navHostController: NavHostController,
    viewModel: EditQuestionViewModel = hiltViewModel(),
) {

    val quiz by viewModel.quiz.collectAsState(initial = null)
    val title by viewModel.title.observeAsState()
    val questionText by viewModel.questionText.observeAsState()
    var questionAnswers: WholeQuestion? = null

//    var question by remember { mutableStateOf(wholeQuestion?.question?.title) }
    var question by remember { mutableStateOf("") }
//    val optionText by remember { mutableStateOf(option.text) }
    var optionText by remember { mutableStateOf("") }

    LaunchedEffect(quiz) {
            quiz?.questions?.find { it.question.uid == questionId }?.let { wholeQuestion ->
            if (question.isEmpty()) {  // Only set if empty to prevent overwriting user input
                question = wholeQuestion.question.title
            }
            questionAnswers = wholeQuestion
        }
    }


    when (quiz) {
        null -> CircularProgressIndicator()
        else -> {
            val tempTitle = quiz?.quiz?.title ?: ""
            viewModel.updateTitle(tempTitle)
            questionAnswers = quiz?.questions?.find { it.question.uid == questionId }
        }
    }

    val currentAnswer by viewModel.currentQuestionAnswer.collectAsState()

    if (quiz?.questions == null) {
        CircularProgressIndicator()
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom

                    ) {
                        Text(question)
                    }
                    Spacer(modifier = Modifier.height(8.dp))


                    Spacer(modifier = Modifier.height(24.dp))


                    questionAnswers.let { wholeQuestion ->
                        TextField(
                            question,
                            onValueChange = { newText ->
                                question = newText
                            },
                            textStyle = MaterialTheme.typography.titleLarge,
                        )


                        viewModel.updateQuestionText(wholeQuestion?.question?.title ?: "")

                        Spacer(modifier = Modifier.height(32.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            wholeQuestion?.answerOptions?.forEach { option ->
                                val selected = option.uid == currentAnswer
                                optionText = option.text

                                EditAnswerOption(
                                    optionText = optionText,
                                    answerId = option.uid,
                                    questionId = wholeQuestion.question.uid,
                                    selected = selected,
                                    onSelect = viewModel::selectAnswer,
                                )
                            }
                        }
                    }
                    TextButton(
                        onClick = {
                            Log.d("EditQuestionScreen", "Saving question: $question")
                            viewModel.updateQuestionText(question)
                            viewModel.saveQuestion()
                            navHostController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Save")
                    }
                }

            }
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
//            IconButton(
//                onClick = { viewModel.prevQuestion() }
//            ) {
//                Icon(
//                    modifier = Modifier.size(48.dp),
//                    imageVector = Icons.Default.KeyboardArrowLeft,
//                    contentDescription = "Previous"
//                )
//            }
//            IconButton(
//                onClick = { viewModel.nextQuestion() }
//            ) {
//                Icon(
//                    modifier = Modifier.size(48.dp),
//                    imageVector = Icons.Default.KeyboardArrowRight,
//                    contentDescription = "Next"
//                )
//            }
            }

        }
    }


}
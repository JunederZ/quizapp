package com.example.quizapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quizapp.ui.theme.onContainerSuccess
import com.example.quizapp.ui.theme.success
import com.example.quizapp.ui.theme.successContainer
import com.example.quizapp.ui.viewmodels.QuizViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListScreen(
    navController: NavHostController,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val quiz by viewModel.quiz.collectAsState()
    val length = quiz.questions!!.size
    val title = quiz.quiz!!.title
    val isQuizComplete by viewModel.isQuizComplete.collectAsState()
    val correctList by viewModel.correctList.collectAsState()
    val selectedList by viewModel.selectedList.collectAsState()
    val navigateFromMenu by viewModel.navigateFromMenu.collectAsState()

    DisposableEffect(navigateFromMenu) {
        if (navigateFromMenu) {
            navController.popBackStack()
        }
        onDispose { }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = viewModel::backFromMenu) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(length) { number ->
                    val isCorrect: Boolean? =
                        if (!isQuizComplete) null
                        else correctList[number]
                    val isSelected: Boolean = selectedList[number]!!

                    NumberCard(
                        isSelected = isSelected,
                        isCorrect = isCorrect,
                        number = number + 1,
                        onClick = { viewModel.jumpToQuestion(number) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NumberCard(
    isSelected: Boolean,
    isCorrect: Boolean?,
    number: Int,
    onClick: (Int) -> Unit
) {

    val borderColor = when {
        isCorrect == true -> MaterialTheme.colorScheme.success
        isSelected -> MaterialTheme.colorScheme.primary
        isCorrect == false -> MaterialTheme.colorScheme.error
        isCorrect == null -> MaterialTheme.colorScheme.surfaceContainer
        !isSelected -> MaterialTheme.colorScheme.surfaceContainer
        else -> MaterialTheme.colorScheme.surfaceContainer
    }

    val backgroundColor = when {
        isCorrect == true -> MaterialTheme.colorScheme.successContainer.copy(alpha = 0.3f)
        isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        isCorrect == false -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        isCorrect == null -> MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f)
        !isSelected -> MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f)
    }

    val textColor = when {
        isCorrect == true -> MaterialTheme.colorScheme.onContainerSuccess
        isCorrect == false -> MaterialTheme.colorScheme.onErrorContainer
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        isCorrect == null -> MaterialTheme.colorScheme.onSurface
        !isSelected -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.onSurface
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .border(2.dp, borderColor, shape = MaterialTheme.shapes.medium)
            .aspectRatio(1f)
            .clickable { onClick(number) }
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )

        }
    }
}

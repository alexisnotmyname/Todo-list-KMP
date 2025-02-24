package com.alexc.ph.app


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.alexc.ph.core.presentation.components.GradientBackground
import com.alexc.ph.core.presentation.theme.LocalGradientColors
import com.alexc.ph.core.presentation.theme.MyTodoKmpTheme
import com.alexc.ph.todo.presentation.TodoListScreenRoot
import com.alexc.ph.todo.presentation.TodoListViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import todoappkmp.composeapp.generated.resources.Res
import todoappkmp.composeapp.generated.resources.todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MyTodoKmpTheme {
        GradientBackground (
            gradientColors = LocalGradientColors.current
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(Res.string.todo),
                                fontWeight = FontWeight.Bold
                            )
                        },
                    )
                }
            ) { innerPadding ->
                val viewModel = koinViewModel<TodoListViewModel>()
                TodoListScreenRoot(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}
package com.toren.producthub.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toren.producthub.R
import com.toren.producthub.data.remote.dto.auth.AuthRequest
import com.toren.producthub.presentation.login.components.LoginTextField
import com.toren.producthub.presentation.login.components.TopSection
import com.toren.producthub.presentation.main.MainActivity
import com.toren.producthub.presentation.theme.ProductHubTheme
import com.toren.producthub.presentation.theme.SecondaryBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductHubTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginResult by viewModel.loginResult.observeAsState()
    val loading by viewModel.loading.observeAsState(true)
    val request = AuthRequest(username, password)
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordVisibility = remember { mutableStateOf(false) }

    LaunchedEffect(loginResult) {
    loginResult?.let {
        if (it.isSuccess) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as? Activity)?.finish()
        } else {
            Toast.makeText(
                context,
                "Username or password is incorrect!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopSection()
            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    LoginTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = "Username",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        onImeAction = {
                            passwordFocusRequester.requestFocus()
                        },
                        visibility = true
                    )
                    LoginTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        modifier = Modifier
                            .focusRequester(passwordFocusRequester)
                            .fillMaxWidth()
                            .padding(12.dp),
                        trailingIcon = if (password.isNotEmpty()) {
                            painterResource(
                                id = if (passwordVisibility.value) R.drawable.ic_visibility
                                else R.drawable.ic_visibility_off
                            )
                        } else null,
                        onImeAction = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        },
                        visibility = passwordVisibility.value,
                        onTogglePasswordVisibility = {
                            passwordVisibility.value = !passwordVisibility.value
                        }
                    )
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .fillMaxWidth()
                            .padding(12.dp),
                        onClick = {
                            viewModel.login(request)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryBlue
                        )
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProductHubTheme {
        LoginScreen()
    }
}
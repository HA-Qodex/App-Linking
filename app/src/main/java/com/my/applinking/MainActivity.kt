package com.my.applinking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.my.applinking.navigation.AppNavigation
import com.my.applinking.viewmodel.NavigationViewModel
import com.my.applinking.ui.theme.AppLinkingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        CoroutineScope(Dispatchers.Main).launch {
            navigationViewModel.handleIntentData(intent?.data)
        }

        setContent {
            AppLinkingTheme {
                AppNavigation()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.lastPathSegment?.let { id ->
            CoroutineScope(Dispatchers.Main).launch {
                navigationViewModel.handleIntentData(intent.data)
            }

//          Clear it from intent
            setIntent(Intent(this, MainActivity::class.java))
        }
    }
}
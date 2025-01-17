package com.example.testupdateapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testupdateapp.ui.theme.TestUpdateAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestUpdateAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    Greeting(this)
                }
            }
        }
    }
}

@Composable
fun Greeting(context: Context) {
    Button(onClick = {
        Util.runAppsBundleInstall(
            context = context,
            bundleName = "",
            url = "/storage/emulated/0/Download/artifacts.tar.gz",
            updateDownloadManager = UpdateDownloadManager.FETCH,
        )
    }) { }
}
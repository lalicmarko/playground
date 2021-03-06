package mr.lalic.playground

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainApp(
                viewModel = mainViewModel,
                showMessage = {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
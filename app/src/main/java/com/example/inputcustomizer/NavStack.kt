import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inputcustomizer.EditKeyboard
import com.example.inputcustomizer.KeyboardsList
import com.example.inputcustomizer.KeyboardsViewModel
import com.example.inputcustomizer.Screen

@Composable
fun NavStack(
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
) {
  val navController: NavHostController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = Screen.KeyboardList.route
  ) {
    composable(route = Screen.KeyboardList.route) {
      KeyboardsList(
        navController = navController,
        modifier = modifier,
        viewModel = viewModel
      )
    }
    composable(route = Screen.NewKeyboard.route) {
      EditKeyboard(
        navController = navController,
        modifier = modifier,
        viewModel = viewModel
      )
    }
  }
}

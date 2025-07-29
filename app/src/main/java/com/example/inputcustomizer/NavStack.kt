import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.inputcustomizer.EditKeyboard
import com.example.inputcustomizer.EditLayout
import com.example.inputcustomizer.KeyboardsList
import com.example.inputcustomizer.KeyboardsViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavStack(
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
) {
  val navController: NavHostController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = KeyboardListRoute
  ) {
    composable<KeyboardListRoute> {
      KeyboardsList(
        navController = navController,
        modifier = modifier,
        viewModel = viewModel
      )
    }
    composable<EditKeyboardRoute> { backStackEntry ->
      val args = backStackEntry.toRoute<EditKeyboardRoute>()
      EditKeyboard(
        navController = navController,
        modifier = modifier,
        viewModel = viewModel,
        keyboardId = args.keyboardId
      )
    }
    composable<EditLayoutRoute> { backStackEntry ->
      val args = backStackEntry.toRoute<EditLayoutRoute>()
      EditLayout(
        navController = navController,
        modifier = modifier,
        viewModel = viewModel,
        keyboardId = args.keyboardId,
        layoutId = args.layoutId
      )
    }
  }
}

@Serializable
object KeyboardListRoute

@Serializable
data class EditKeyboardRoute(val keyboardId: Int)

@Serializable
data class EditLayoutRoute(val keyboardId: Int, val layoutId: Int)

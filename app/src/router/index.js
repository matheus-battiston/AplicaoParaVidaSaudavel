import { createBrowserRouter } from 'react-router-dom';
import { RecipeForm } from '../ui/components/recipeForm/recipeForm.component';
import { MainScreen } from '../ui/screens/main/main.screen';
import { RecipesScreen } from '../ui/screens/recipes/recipes.screen';
import { RecoverPassword } from '../ui/screens/recoverPassword/recoverPassword.screen';
import { ResetPasswordScreen } from '../ui/screens/resetPassword/resetPassword.screen';
import { Tracker } from '../ui/screens/tracker/tracker.screen';
import { IdentificationScreen } from '../ui/screens/userIdentification/identification.screen';
import { UserPage } from '../ui/screens/userPage/userPage.screen';
import { PrivateRoute } from './private-route.component';

export const router = createBrowserRouter([
  {
    path: '/login',
    element: <IdentificationScreen />,
  },
  {
    path: '/',
    element: <PrivateRoute Screen={MainScreen} />,
  },
  {
    path: '/diario',
    element: <PrivateRoute Screen={Tracker} />,
  },
  {
    path: '/receitas',
    element: <PrivateRoute Screen={RecipesScreen} />,
  },
  {
    path: '/receita/',
    element: <PrivateRoute Screen={RecipeForm} />,
  },
  {
    path: '/perfil/:id',
    element: <PrivateRoute Screen={UserPage} />,
  },
  {
    path: '/recuperar-senha',
    element: <RecoverPassword />,
  },
  {
    path: '/resetar-senha',
    element: <ResetPasswordScreen />,
  },
]);

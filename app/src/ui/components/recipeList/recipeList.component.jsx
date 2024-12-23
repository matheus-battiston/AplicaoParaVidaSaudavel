import { useEffect, useState } from 'react'
import { useRecipesFunctions } from '../../../api/hooks/useRecipesFunctions.hook';
import useGlobalUser from '../../../context/user.context';
import { LoadMoreButton } from '../loadMoreButton/loadMoreButton.component';
import { RecipeRatingStars } from '../recipeRatingStars/recipeRatingStars.component';
import './recipeList.css'
import defaultImage from '../../../images/defaultRecipe.jpg'
import { RecipeDetails } from '../recipeDetails/recipeDetails.component';
import { useNavigate } from 'react-router-dom';

export function RecipeList({ type, alergies, onSave }) {
    const navigate = useNavigate()
    const [showRecipeDetails, setShowRecipeDetails] = useState(false)
    const [loggedUser] = useGlobalUser();
    const [reload, setReload] = useState(false)
    const { getFollowingRecipes, getTopRecipes, getUserRecipes } = useRecipesFunctions()
    const [pageSize, setPageSize] = useState(6)
    const [recipes, setRecipes] = useState()
    useEffect(() => {
        async function fetchFollowingRecipes() {
            const response = await getFollowingRecipes(pageSize)
            setRecipes(response)
        }
        async function fetchTopRecipes() {
            const response = await getTopRecipes(pageSize, alergies)
            setRecipes(response)
        }
        async function fetchUserRecipes(userId) {
            const response = await getUserRecipes(userId, pageSize)
            setRecipes(response)
        }


        if (type === 'top') fetchTopRecipes()
        else if (type === 'following') fetchFollowingRecipes()
        else if (type === 'add') fetchUserRecipes(loggedUser.id)
        else fetchUserRecipes(type)

    }, [reload, pageSize, type, alergies])

    function getEmptyListMessage() {
        if (recipes?.content?.length) return
        return (
            <li className='recipe-list-item'>
                <div className='recipe-list-item-info'>
                    <h3>Nenhuma receita disponível</h3>
                    <span>Crie novas receitas ou copie receitas da comunidade para usa-las em suas refeições!</span>
                </div>
            </li>
        )
    }

    return (
        <ul className='recipe-list'>
            {recipes?.content?.map(recipe =>
                <li key={recipe.id} className='recipe-list-item'>
                    {showRecipeDetails === recipe.id
                        ? <RecipeDetails recipe={recipe} setShowRecipeDetails={setShowRecipeDetails} setReload={setReload} type={type} onSave={onSave} />
                        : null}
                    <img className='recipe-image' src={recipe?.imagem} onError={(e) => e.target.src = defaultImage} alt={'receita'} />
                    <div className='recipe-list-item-info'>
                        <RecipeRatingStars rating={recipe?.nota} rated={true} />
                        <h2>{recipe?.titulo}</h2>
                        <span className='recipe-creator' onClick={() => navigate('/perfil/' + recipe?.criadorId)}>{(recipe?.copia ? 'Copiado de: ' : 'Feito por: ') + recipe?.criadorNome}</span>
                        <span className='recipe-calories'>{recipe?.calorias + 'kcal'}</span>
                    </div>
                    <button className='recipe-list-item-button' onClick={() => setShowRecipeDetails(recipe.id)}>+</button>
                </li>
            )}
            {getEmptyListMessage()}
            <LoadMoreButton pageSize={pageSize} totalElements={recipes?.totalElements} setPageSize={setPageSize} />
        </ul>
    )
}
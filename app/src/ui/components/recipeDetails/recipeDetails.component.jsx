import { useNavigate } from 'react-router-dom';
import useGlobalUser from '../../../context/user.context';
import { Button } from '../button/button.component';
import { FoodList } from '../foodList/foodList.component';
import { Modal } from '../modal/modal.component'
import { RecipeRatingStars } from '../recipeRatingStars/recipeRatingStars.component';
import defaultImage from '../../../images/defaultRecipe.jpg'
import './recipeDetails.css'
import { useRecipesFunctions } from '../../../api/hooks/useRecipesFunctions.hook';
import { useEffect, useState } from 'react';

export function RecipeDetails({ recipe, type, setShowRecipeDetails, onSave, setReload }) {
    const [loggedUser] = useGlobalUser()
    const navigate = useNavigate()
    const [deleteForm, setDeleteForm] = useState(false)
    const [ratingRecipe, setRatingRecipe] = useState(false)
    const [suggestedRecipes, setSuggestedRecipes] = useState([])
    const [ratingValue, setRatingValue] = useState({
        value: '', error: ''
    })
    const { copyRecipe, deleteRecipe, getSuggestedRecipes, rateRecipe } = useRecipesFunctions()

    useEffect(() => {
        async function fetchSuggested() {
            const response = await getSuggestedRecipes(recipe?.id)
            setSuggestedRecipes(response)
        }

        fetchSuggested()
    }, [recipe])



    async function handleSubmit() {
        await onSave(recipe.id)
        setReload()
        setShowRecipeDetails(null)
    }

    async function handleCopy() {
        await copyRecipe(recipe.id)
        setShowRecipeDetails(null)
    }
    async function handleDelete() {
        await deleteRecipe(recipe.id)
        setReload()
        setShowRecipeDetails(null)
    }
    async function handleRate() {
        if (ratingValue.value.length === 0)
            setRatingValue(oldValue => ({ ...oldValue, error: 'Nota não pode ser vazia' }))
        else if (ratingValue.value.match(/^\d+$/)) {
            await rateRecipe(recipe.id, ratingValue.value)
            setRatingValue(oldValue => ({ ...oldValue, error: '' }))
            setRatingRecipe(false)
            setReload(value => !value)
        }
        else setRatingValue(oldValue => ({ ...oldValue, error: 'Nota deve ser um número inteiro' }))

    }
    function handleChange(event) {
        setRatingValue(oldValue => ({ ...oldValue, value: event.target.value }))

    }

    return (
        <Modal onClose={() => setShowRecipeDetails(null)}>
            <div className='recipe-details-card'>
                <div className='recipe-details-header'>
                    <img className='recipe-details-header-image' src={recipe.imagem} onError={(e) => e.target.src = defaultImage} alt='receita' />
                    <div className='recipe-details-header-right'>
                        <div>
                            <RecipeRatingStars rating={recipe.nota} rated={recipe.avaliado} setReload={setReload} />
                            {ratingRecipe
                                ? <Modal small={true} onClose={() => setRatingRecipe(false)}>
                                    <div className='recipe-rating-rate'>
                                        <h3>Avaliar receita</h3>
                                        <input value={ratingValue.value} type='number' onChange={handleChange}></input>
                                        <div className='recipe-rating-button'>
                                            <Button text='Avaliar' onClick={handleRate} />
                                            <span className='error'>{ratingValue.error}</span>
                                        </div>
                                    </div>
                                </Modal>
                                : null}
                        </div>
                        <h2>{recipe.titulo}</h2>
                        <span className='recipe-creator' onClick={() => navigate('/perfil/' + recipe?.criadorId)}>{(recipe?.copia ? 'Copiado de: ' : 'Feito por: ') + recipe?.criadorNome}</span>
                        <div className='recipe-details-nutrition'>
                            <span>{recipe?.calorias + 'kcal'}</span>
                            <div className='recipe-details-nutrition-right'>
                                <span>{recipe?.proteinas + 'g proteinas'}</span>
                                <span>{recipe?.carboidratos + 'g carboidratos'}</span>
                                <span>{recipe?.lipidios + 'g gorduras'}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='recipe-details-buttons'>
                    {recipe?.usuarioId === loggedUser.id
                        ? <>
                            <Button type='second' text={'Editar receita'} onClick={() => navigate('/receita', { state: { ...recipe } })} />
                            <div className='recipe-details-delete'>
                                {deleteForm
                                    ? <>
                                        <Button type='second' text={'Cancelar'} onClick={() => setDeleteForm(false)} />
                                        <Button text={'Excluir receita'} onClick={handleDelete} />
                                    </>
                                    : <>
                                        <Button type='second' text={'Excluir receita'} onClick={() => setDeleteForm(true)} />
                                    </>

                                }
                            </div>
                        </>
                        : <>
                            <Button onClick={handleCopy} type='second' text={'Copiar receita'} />
                            {recipe.avaliado ? null : <Button onClick={() => setRatingRecipe(true)} type='second' text={'Avaliar receita'} />}
                        </>
                    }
                </div>
                <div className='recipe-details-info'>
                    <div className='recipe-details-info-item'>
                        <span>{recipe.descricao}</span>
                    </div>
                    <div className='recipe-details-info-item'>
                        <h3>Modo de preparo</h3>
                        {recipe?.modoPreparo.split('.').map(text => <p>{text + (text ? '.' : '')}</p>)}
                    </div>
                    <div className='recipe-details-info-item'>
                        <h3>Ingredientes</h3>
                        <FoodList foods={recipe.alimentos} type='list' />
                    </div>
                </div>
                {type === 'add' ? <Button onClick={handleSubmit} className='food-details-button' text='Adicionar' />
                    : null}
                {suggestedRecipes?.length
                    ? <ul className='suggested-recipes'>
                        <h3>Receitas semelhantes</h3>
                        {suggestedRecipes?.map(recipe =>
                            <li key={recipe.id} className='suggested-list-item'>
                                <img className='suggested-image' src={recipe?.imagem} onError={(e) => e.target.src = defaultImage} alt={'receita'} />
                                <div className='suggested-list-item-info'>
                                    <RecipeRatingStars rating={recipe?.nota} rated={true} />
                                    <h2>{recipe?.titulo}</h2>
                                    <span className='recipe-creator' onClick={() => navigate('/perfil/' + recipe?.criadorId)}>{(recipe?.copia ? 'Copiado de: ' : 'Feito por: ') + recipe?.criadorNome}</span>
                                    <span className='recipe-calories'>{recipe?.calorias + 'kcal'}</span>
                                </div>
                                <button className='recipe-list-item-button' onClick={() => setShowRecipeDetails(recipe.id)}>+</button>
                            </li>
                        )}
                    </ul>
                    : null}
            </div>
        </Modal>
    )
}
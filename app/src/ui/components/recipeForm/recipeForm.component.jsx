import { useState } from 'react'
import { useRecipesFunctions } from '../../../api/hooks/useRecipesFunctions.hook'
import { Button } from '../button/button.component'
import { FoodList } from '../foodList/foodList.component'
import { FoodSearch } from '../foodSearch/foodSearch.component'
import { useLocation, useNavigate } from "react-router-dom";
import './recipeForm.css'
import { Header } from '../header/header.component'

export function RecipeForm() {
    const { createRecipe, editRecipe } = useRecipesFunctions()
    const [addingFood, setAddingFood] = useState(false)
    const navigate = useNavigate()
    const location = useLocation()
    const recipe = location.state
    const [userInput, setUserInput] = useState({
        title: { value: recipe?.titulo || '', error: '' },
        image: { value: recipe?.imagem || '', error: '' },
        preparement: { value: recipe?.modoPreparo || '', error: '' },
        description: { value: recipe?.descricao || '', error: '' },
        private: { value: recipe?.privado || false, error: '' },
        copy: { value: recipe?.copia || false, error: '' },
        foods: { value: recipe?.alimentos || [], error: '' },
    })
    async function addFood(food) {
        removeFood(food.id)
        setUserInput(oldInput => ({ ...oldInput, foods: { ...oldInput.foods, value: [...oldInput.foods.value, food] } }))
        setAddingFood(false)
    }
    async function removeFood(foodId) {
        setUserInput(oldInput => ({ ...oldInput, foods: { ...oldInput.foods, value: oldInput.foods.value.filter(food => food.id !== foodId) } }))
    }
    function handleChange(event) {
        const { name, value } = event.target
        setUserInput(oldInput => ({ ...oldInput, [name]: { ...oldInput[name], value: value } }))
    }
    async function handleSubmit() {
        if (validadeInput('title', 50) && validadeInput('description', 255) && validadeIngredients() && validadeInput('preparement', 511) && validadeInput('image', 511)) {
            if (recipe)
                await editRecipe(userInput, recipe.id)
            else
                await createRecipe(userInput)
            navigate('/receitas')
        }
    }

    function validadeInput(field, maxSize) {
        if (userInput[field].value === '' && field !== 'image') {
            setUserInput(oldInput => ({ ...oldInput, [field]: { ...oldInput[field], error: "Campo não pode ser vazio" } }))
            return false
        }
        if (userInput[field].value.length > maxSize) {
            setUserInput(oldInput => ({ ...oldInput, [field]: { ...oldInput[field], error: `Campo deve ter até ${maxSize} caractéres` } }))
            return false
        }

        setUserInput(oldInput => ({ ...oldInput, [field]: { ...oldInput[field], error: "" } }))
        return true
    }
    function validadeIngredients() {
        if (!userInput.foods.value.length) {
            setUserInput(oldInput => ({ ...oldInput, foods: { ...oldInput.foods, error: "Lista de ingredientes não pode ser vazia" } }))
            return false
        }
        setUserInput(oldInput => ({ ...oldInput, foods: { ...oldInput.foods, error: "" } }))
        return true
    }

    return (
        <>
            <Header page='recipe' />

            <div className='container recipe-form'>
                <div className='recipe-form-header'>
                    <h2>{recipe ? "Editar receita" : "Criar nova receita"}</h2>
                </div>
                <select name='private' value={userInput.private.value} onChange={handleChange}>
                    <option disabled></option>
                    <option value={true}>Privada</option>
                    <option value={false}>Pública</option>
                </select>
                <div className='recipe-form-input'>
                    <span>Título</span>
                    <input type='text' name='title' value={userInput.title.value} onChange={handleChange}></input>
                    <span className='recipe-form-error'>{userInput.title.error}</span>
                </div>
                <div className='recipe-form-input'>
                    <span>Descrição</span>
                    <textarea className='scrollbar' name='description' value={userInput.description.value} rows='3' onChange={handleChange} ></textarea>
                    <span className='recipe-form-error'>{userInput.description.error}</span>
                </div>
                <div className='recipe-form-ingredients'>
                    <div className='recipe-form-ingredients-header'>
                        <h2>Ingredientes</h2>
                        {addingFood
                            ? <Button onClick={() => setAddingFood(false)} text='Cancelar' type='second' />
                            : <Button onClick={() => setAddingFood(true)} text='Adicionar alimento' type='second' />
                        }
                    </div>
                    <div className='recipe-form-ingredients-content'>
                        {addingFood ? <FoodSearch onSave={addFood} calledBy={'recipeForm'} />
                            :
                            <FoodList foods={userInput.foods.value} onSave={addFood} onDelete={removeFood} type='recipe' />
                        }
                    </div>
                    <span className='recipe-form-error'>{userInput.foods.error}</span>
                </div>
                <div className='recipe-form-input'>
                    <span>Modo de preparo</span>
                    <textarea className='scrollbar' name='preparement' value={userInput.preparement.value} rows='6' onChange={handleChange}></textarea>
                    <span>{userInput.preparement.error}</span>
                </div>
                <div className='recipe-form-input'>
                    <span>Imagem (URL)</span>
                    <input type='text' name='image' value={userInput.image.value} onChange={handleChange}></input>
                    <span className='recipe-form-error'>{userInput.image.error}</span>
                </div>
                <div className='recipe-form-buttons'>
                    <Button onClick={() => navigate('/receitas')} text={'Cancelar'} type='second' />
                    {recipe
                        ? <Button onClick={handleSubmit} text={'Editar'} />
                        : <Button onClick={handleSubmit} text={'Criar'} />
                    }
                </div>
            </div>
        </>
    )
}
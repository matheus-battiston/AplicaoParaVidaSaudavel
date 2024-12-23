import { useEffect, useState } from 'react'
import { useFoodFunctions } from '../../../api/hooks/useFoodFunctions.hook'
import { FoodList } from '../foodList/foodList.component'
import { RecipeList } from '../recipeList/recipeList.component'
import { LoadMoreButton } from '../loadMoreButton/loadMoreButton.component'
import './foodSearch.css'

export function FoodSearch({ onSave, calledBy }) {
    const [foodName, setFoodName] = useState('')
    const [foods, setFoods] = useState([])
    const [pageSize, setPageSize] = useState(6)
    const { getFoodByName, getRecentFoods } = useFoodFunctions()
    const [selectedPage, setSelectedPage] = useState('foods')
    useEffect(() => {
        async function fetchFoods() {
            const response = await getFoodByName(pageSize, foodName)
            setFoods(response)
        }
        async function fetchRecentFoods() {
            const response = await getRecentFoods()
            setFoods({content: response})
        }
 

        if(foodName!=='') fetchFoods()
        else fetchRecentFoods()
    }, [pageSize, foodName])


    function handleChange(event) {
        const { value } = event.target
        setFoodName(value)
        setSelectedPage('foods')
        setPageSize(6)
    }

    return (
        <div className='add-food-card'>
            <div className='add-food-header'>
                <h3>Adicionar Alimento</h3>
                <span>Aqui você pode adicionar o alimento a sua refeição diária ou a sua receita.</span>
            </div>
            <div className='search-food-input'>
                <input type='text' value={foodName} onChange={handleChange} placeholder='Busque pelo alimento...'></input>
            </div>
            <div className='add-food-options'>
                <button className={selectedPage === 'foods' ? 'add-food-selected' : ''} onClick={() => setSelectedPage('foods')}>Alimentos</button>
                {calledBy !== 'recipeForm'
                    ? <button className={selectedPage === 'recipes' ? 'add-food-selected' : ''} id='add-food-options-right' onClick={() => setSelectedPage('recipes')}>Receitas</button>
                    : null}
            </div>
            {selectedPage === 'recipes'
                ? <RecipeList type={'add'} onSave={onSave} />
                : <>
                    {foodName==='' && foods?.content?.length
                    ? <h3>Alimentos Recentes</h3>
                    : null}
                    <FoodList foods={foods.content} type='search' onSave={onSave} />
                    <LoadMoreButton pageSize={pageSize} setPageSize={setPageSize} totalElements={foods.totalElements} />
                </>

            }
        </div>
    )
}
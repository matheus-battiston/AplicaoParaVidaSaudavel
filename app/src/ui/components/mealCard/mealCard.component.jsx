import { useState } from 'react'
import { FoodSearch } from '../foodSearch/foodSearch.component'
import { useTrackerFunctions } from '../../../api/hooks/useTrackerFunctions.hook'
import './mealCard.css'
import { FoodList } from '../foodList/foodList.component'
import { Button } from '../button/button.component'

export function MealCard({ meal, name, setReload, day, period }) {
    const { addFoodToMeal, addRecipeToMeal, removeFoodFromMeal } = useTrackerFunctions()
    const [showList, setShowList] = useState()

    async function addFood(content) {
        if (content.id)
            await addFoodToMeal(day, period, content)
        else
            await addRecipeToMeal(day, period, content)
        setShowList('details')
        setReload(value => !value)
    }
    async function removeFood(foodId) {
        await removeFoodFromMeal(meal.id, foodId)
        setReload(value => !value)
    }
    
    return (
        <div className='meal-card border'>
            <div className='meal-header'>
                <h2>{name}</h2>
                {showList === 'add'
                    ? <Button onClick={() => setShowList(null)} text='Cancelar' type='second' />
                    : <Button onClick={() => setShowList('add')} text='Adicionar alimento' type='second' />}
            </div>
            {showList === 'details' ?
                <>
                    <button className='meal-show-details' onClick={() => setShowList(null)}>Ver menos</button>
                    <FoodList foods={meal?.alimentos} onSave={addFood} onDelete={removeFood} type='meal' />
                </>
                :
                <button className='meal-show-details' onClick={() => setShowList('details')}>Ver detalhes</button>
            }
            {showList === 'add' ?
                <FoodSearch onSave={addFood} />

                : null}
            <div className='meal-details'>
                <span>{meal?.calorias || 0} kcal</span>
                <div className='meal-nutrients'>
                    <span>{meal?.carboidratos || 0} Carboidrato</span>
                    <span>{meal?.proteinas || 0} Proteína</span>
                    <span>{meal?.lipidios || 0} Gordura</span>
                </div>
            </div>
        </div>

    )
}
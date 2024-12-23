import { useState } from 'react'
import { FoodDetails } from '../foodDetails/foodDetails.component';
import './foodList.css'
import edit from '../../../images/edit.svg'
import trash from '../../../images/trash.svg'
import { Button } from '../button/button.component';


export function FoodList({ foods, type, onSave, onDelete }) {
    const [showFoodDetails, setShowFoodDetails] = useState(false)
    const [creatingFood, setCreatingFood] = useState(false)

    function handleDelete(foodId) {
        onDelete(foodId)
    }

    return (
        <div className='foods'>
            {creatingFood ? <FoodDetails onClose={() => setCreatingFood(false)} type='new' /> : null}
            <ul className='food-list'>
                {foods?.map(food =>
                    <li className='food-list-item' key={food.id}>
                        {showFoodDetails === food.id ? <FoodDetails food={food} onClose={() => setShowFoodDetails(false)} onSave={onSave} type={type} /> : null}

                        <div className='food-info'>
                            {food.nome.split(",").map((text, index) => {
                                if (index === 0) return <h3 key={index}>{text}</h3>
                                return <span key={index}>{text}</span>
                            })}
                            <div className='food-details'>
                                <span>{food.quantidade + ' gramas'} </span>
                                <span>-</span>
                                <span>{Math.round(food.calorias * food.quantidade / 100) + ' kcal'}</span>
                            </div>
                        </div>
                        <span className='food-info-community'>{food.comunidade ? 'Alimento da comunidade' : ''}</span>
                        <div className='food-options'>
                            {type === 'recipe' || type === 'meal'
                                ? <>
                                    <img onClick={() => setShowFoodDetails(food.id)} className='edit-food-button' src={edit} />
                                    <img onClick={() => handleDelete(food.id)} className='remove-food-button' src={trash} />
                                </>
                                :
                                <button onClick={() => setShowFoodDetails(food.id)}>+</button>

                            }

                        </div>
                    </li>
                )}
                {type === 'search' ?
                    <li className='food-list-item'>
                        <h3>Não encontrou o que buscava?</h3>
                        <Button onClick={() => setCreatingFood(true)} text={'Criar alimento'} />
                    </li>
                    : null}

            </ul>
        </div>
    )
}
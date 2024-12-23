import { useState } from 'react';
import { useFoodFunctions } from '../../../api/hooks/useFoodFunctions.hook';
import { Button } from '../button/button.component';
import { Modal } from '../modal/modal.component'
import './foodDetails.css'

export function FoodDetails({ food, type, onClose, onSave }) {
    const [quantity, setQuantity] = useState(Number.parseFloat(food?.quantidade || 100))
    const {createFood} = useFoodFunctions()
    const [userInput, setUserInput] = useState({
        name: { value: '', error: '' },
        calories: { value: '', error: '' },
        protein: { value: '', error: '' },
        carbo: { value: '', error: '' },
        fat: { value: '', error: '' },
    })
    function handleQuantityChange(event) {
        const { value } = event.target
        setQuantity(value)
    }
    function handleFormChange(event) {
        const { name, value } = event.target
        setUserInput(oldInput => ({ ...oldInput, [name]: { ...oldInput[name], value: value } }))
    }


    async function handleSubmit() {
        await onSave({ ...food, quantidade: quantity })
        onClose()
    }
    async function handleFoodSubmit() {
      
        if (validateInput('name') && validateInput('calories') && validateInput('protein') && validateInput('carbo') && validateInput('fat')) {
            
            await createFood(userInput)
            onClose()
        }
    }
    function getFoodCardName() {
        if (food)
            return (
                food.nome.split(",").map((text, index) => {
                    if (index === 0) return <h2 key={index}>{text}</h2>
                    return <span key={index}>{text}</span>
                })
            )
        return (<>
            <h3>Nome do alimento</h3>
            <input placeholder='ex: Frango, peito, sem pele' name='name' value={userInput.name.value} onChange={handleFormChange}></input>
            <span className='error'>{userInput.name.error}</span>
        </>)
    }
    function getFoodCardQuantity() {
        if (food)
            return (<>
                <input value={quantity} onChange={handleQuantityChange}></input>
            </>)
        return (
            <h3>100g</h3>
        )
    }
    function getFoodCardValues() {
        if (food)
            return (<>
                <h3>Calorias: {(Number.parseFloat(food?.calorias) * quantity / 100).toFixed(1)}</h3>
                <h3>Proteinas: {(Number.parseFloat(food?.proteinas) * quantity / 100).toFixed(1)}</h3>
                <h3>Carboidratos: {(Number.parseFloat(food?.carboidratos) * quantity / 100).toFixed(1)}</h3>
                <h3>Lipidios: {(Number.parseFloat(food?.lipidios) * quantity / 100).toFixed(1)}</h3>
            </>)
        return (<>
            <div className='food-details-table-input'>
                <h3>Calorias</h3>
                <input type='number' name='calories' value={userInput.calories.value} onChange={handleFormChange}></input>
            </div>
                <span className='error'>{userInput.calories.error}</span>
            <div className='food-details-table-input'>
                <h3>Proteinas(g)</h3>
                <input type='number' name='protein' value={userInput.protein.value} onChange={handleFormChange}></input>
            </div>
                <span className='error'>{userInput.protein.error}</span>
            <div className='food-details-table-input'>
                <h3>Carboidratos(g)</h3>
                <input type='number' name='carbo' value={userInput.carbo.value} onChange={handleFormChange}></input>
            </div>
                <span className='error'>{userInput.carbo.error}</span>
            <div className='food-details-table-input'>
                <h3>Lipidios(g)</h3>
                <input type='number' name='fat' value={userInput.fat.value} onChange={handleFormChange}></input>
            </div>
                <span className='error'>{userInput.fat.error}</span>
        </>)
    }

    function validateInput(input) {
        if (!userInput[input].value) {
            setUserInput(oldInput => ({ ...oldInput, [input]: { ...oldInput[input], error: "Campo não pode ser vazio" } }))
            return false
        }
        const pattern = /^-?\d*\.?\d+$/;
        if (input==='name' ||  pattern.test(userInput[input].value)) {
            setUserInput(oldInput => ({ ...oldInput, [input]: { ...oldInput[input], error: null } }))
            return true
        }

        setUserInput(oldInput => ({ ...oldInput, [input]: { ...oldInput[input], error: "Campo deve ser um número válido" } }))

    }

    return (
        <Modal onClose={onClose}>
            <div className='food-details-card'>
                <div className='food-details-header'>
                    <div className='food-details-header-left'>
                        <div className='food-details-name'>
                            {getFoodCardName()}
                        </div>
                    </div>
                    <span className='food-details-community'>{food?.comunidade ? 'Alimento da comunidade' : ''}</span>
                    <div className='food-details-quantity'>
                        <h2>Quantidade(g)</h2>
                        <div className='food-details-quantity-input'>
                            {getFoodCardQuantity()}
                        </div>
                    </div>
                </div>
                <div className='food-details-table'>
                    <h2>VALOR NUTRICIONAL</h2>
                    {getFoodCardValues()}
                </div>
                {type === 'search' ? <Button onClick={handleSubmit} className='food-details-button' text='Adicionar' /> : null}
                {type === 'recipe' || type=== 'meal' ? <Button onClick={handleSubmit} className='food-details-button' text='Editar' /> : null}
                {type === 'new' ? <Button onClick={handleFoodSubmit} className='food-details-button' text='Criar' /> : null}

            </div>
        </Modal>
    )
}
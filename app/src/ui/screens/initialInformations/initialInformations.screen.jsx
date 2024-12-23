import { useState } from "react";
import './initialInformations.css'
import { useUserFunctions } from "../../../api/hooks/useUserFunctions.hook";
import useGlobalUser from "../../../context/user.context";
import { Button } from "../../components/button/button.component";

export function InitialInformation() {
    const [, setLoggedUser] = useGlobalUser()
    const { sendUserInitialInformation } = useUserFunctions()
    const [currentPage, setCurrentPage] = useState()
    const [userInput, setUserInput] = useState({
        sex: { value: 'M', error: '' },
        weight: { value: '', error: '' },
        height: { value: '', error: '' },
        image: { value: '', error: '' },
        goal: { value: '', error: '' },
        activity: { value: '', error: '' }
    })

    function handleChange(event) {
        const { name, value } = event.target
        setUserInput(oldInput => ({ ...oldInput, [name]: { ...oldInput[name], value: value } }))
    }

    async function handleSubmit() {
        if (!userInput.activity.value)
            setUserInput(oldInput => ({ ...oldInput, activity: { ...oldInput.activity, error: "É preciso escolher uma nível de atividade" } }))
        else {
            const response = await sendUserInitialInformation(userInput)
            if (response.id) setLoggedUser(response)
        }
    }

    function validateHeight() {
        if (!userInput.height.value) {
            setUserInput(oldInput => ({ ...oldInput, height: { ...oldInput.height, error: "Altura não pode ser vazia" } }))
            return false
        }
        if (userInput.height.value.match(/^\d+$/)) {
            setUserInput(oldInput => ({ ...oldInput, height: { ...oldInput.height, error: null } }))
            return true
        }

        setUserInput(oldInput => ({ ...oldInput, height: { ...oldInput.height, error: "Altura deve ser um número inteiro" } }))

    }
    function validateWeight() {
        if (!userInput.weight.value) {
            setUserInput(oldInput => ({ ...oldInput, weight: { ...oldInput.weight, error: "Peso não pode ser vazio" } }))
            return false
        }
        if (userInput.weight.value.match(/^\d+$/)) {
            setUserInput(oldInput => ({ ...oldInput, weight: { ...oldInput.weight, error: null } }))
            return true
        }

        setUserInput(oldInput => ({ ...oldInput, weight: { ...oldInput.weight, error: "Peso deve ser um número inteiro" } }))
    }
    function validateImage() {
        if (userInput.image.value.length > 511) {
            setUserInput(oldInput => ({ ...oldInput, image: { ...oldInput.image, error: 'URL deve ter no máximo 511 caractéres' } }))
            return false
        }
        setUserInput(oldInput => ({ ...oldInput, image: { ...oldInput.image, error: "" } }))
        return true
    }

    function handleFirstPage() {

        if (validateHeight() && validateWeight() && validateImage())
            setCurrentPage('goal')
    }
    function handleGoalsPage() {
        if (!userInput.goal.value)
            setUserInput(oldInput => ({ ...oldInput, goal: { ...oldInput.goal, error: "É preciso escolher uma meta" } }))
        else setCurrentPage('activity')
    }

    if (currentPage === 'goal')
        return (
            <div className="initial-informations">
                <div className="initial-informations-card">
                    <div className="initial-informations-header">

                        <h2>Qual a sua meta?</h2>
                    </div>
                    <div className="initial-informations-goals">
                        <button className={userInput.goal.value === 'P' ? 'selected-info-button' : 'information-button'} name="goal" value='P' onClick={handleChange}>Perder Gordura</button>
                        <button className={userInput.goal.value === 'G' ? 'selected-info-button' : 'information-button'} name="goal" value='G' onClick={handleChange}>Ganhar Músculo</button>
                        <button className={userInput.goal.value === 'M' ? 'selected-info-button' : 'information-button'} name="goal" value='M' onClick={handleChange}>Manter Peso</button>
                    </div>
                    <span className='error'>{userInput.goal.error}</span>
                    <div className="initial-informations-buttons">
                        <Button onClick={() => setCurrentPage(null)} text='Voltar' type='second' />
                        <Button onClick={() => handleGoalsPage()} text='Avançar' />
                    </div>
                </div>
            </div>
        )

    if (currentPage === 'activity')
        return (
            <div className="initial-informations">
                <div className="initial-informations-card">
                    <div className="initial-informations-header">
                        <h2>Qual seu nível de atividade?</h2>
                    </div>
                    <div className="initial-informations-activity">
                        <button className={userInput.activity.value === 'P' ? 'selected-info-button' : 'information-button'} name="activity" value='P' onClick={handleChange}>
                            <h3>Pouco ou nenhum</h3></button>
                        <button className={userInput.activity.value === 'M' ? 'selected-info-button' : 'information-button'} name="activity" value='M' onClick={handleChange}>
                            <h3>Moderado</h3>
                            <span>(meia hora de algum exercício, quatro vezes por semana)</span>
                        </button>
                        <button className={userInput.activity.value === 'I' ? 'selected-info-button' : 'information-button'} name="activity" value='I' onClick={handleChange}>
                            <h3>Intenso</h3>
                            <span>(uma hora de algum exercício, pelo menos quatro vezes por semana)</span>
                        </button>
                    </div>
                    <span className='error'>{userInput.activity.error}</span>
                    <div className="initial-informations-buttons">
                        <Button onClick={() => setCurrentPage('goal')} text='Voltar' type='second' />
                        <Button onClick={() => handleSubmit()} text='Enviar' />
                    </div>
                </div>
            </div>
        )

    return (
        <div className="initial-informations">
            <div className="initial-informations-card">
                <h1>Nos conte mais sobre você</h1>
                <div className='initial-informations-item'>
                    <span>Sexo *</span>
                    <select >
                        <option disabled value={''}></option>
                        <option value={'M'}>Homem</option>
                        <option value={'F'}>Mulher</option>
                    </select>
                </div>
                <div className='initial-informations-item'>
                    <label>Peso (kg) * </label>
                    <input className="identification-input" name="weight" value={userInput.weight.value} onChange={handleChange} required />
                    <span className='error'>{userInput.weight.error}</span>
                </div>
                <div className='initial-informations-item'>
                    <label>Altura (cm) *</label>
                    <input className="identification-input" name="height" value={userInput.height.value} onChange={handleChange} />
                    <span className='error'>{userInput.height.error}</span>
                </div>
                <div className='initial-informations-item'>
                    <label>Imagem (URL)</label>
                    <input className="identification-input" name="image" value={userInput.image.value} onChange={handleChange} required />
                    <span className='error'>{userInput.image.error}</span>
                </div>
                <div className="initial-informations-buttons">
                    <Button onClick={() => handleFirstPage()} text='Avançar' />
                </div>
            </div>
        </div>
    )
}
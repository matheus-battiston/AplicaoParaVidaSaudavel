import { useState, useEffect } from 'react'
import { useTrackerFunctions } from '../../../api/hooks/useTrackerFunctions.hook';
import useGlobalUser from '../../../context/user.context';
import { Header } from '../../components/header/header.component'
import { MealCard } from '../../components/mealCard/mealCard.component'
import { ProgressCircle } from '../../components/progressCircle/progressCircle.component';
import './tracker.css'

export function Tracker() {
    const daysOfWeek = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];
    const months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
    const { getDayMeals, getDailyReport, drinkWater } = useTrackerFunctions()
    const [currentDate] = useState(new Date().toLocaleDateString().split('/').reverse().join('-'))
    const [chosenDate, setChosenDate] = useState(currentDate)
    const [dailyReport, setDailyReport] = useState()
    const [mealList, setMealList] = useState([])
    const [reload, setReload] = useState(false)

    const [user] = useGlobalUser()

    const date = new Date(chosenDate)
    date.setDate(date.getDate() + 1)
    const dayOfWeek = daysOfWeek[date.getDay()];
    const dayOfMonth = date.getDate();
    const month = months[date.getMonth()];
    useEffect(() => {
        async function fetchContent() {
            const report = await getDailyReport(chosenDate)
            const meals = await getDayMeals(chosenDate)
            setDailyReport(report)
            setMealList(meals)

        }

        fetchContent()
    }, [chosenDate, reload])

    function handleWater(event) {
        const { value } = event.target
        if (dailyReport?.agua + Number.parseInt(value) >= 0) {
            drinkWater(chosenDate, value)
            setReload(value => !value)
        }
    
    }
    function handleChange(event) {
        const { value } = event.target
        if (value <= currentDate)
            setChosenDate(value)
        else
            setChosenDate(currentDate)
    }
    function changeReload() {
        setReload(value => !value)
    }
    function getProteinPercentage() {
        return Number.parseInt(Number.parseFloat(dailyReport?.proteinas) * 100 / Number.parseFloat(dailyReport?.metaProteinas))
    }
    function getCarboPercentage() {
        return Number.parseInt(Number.parseFloat(dailyReport?.carboidratos) * 100 / Number.parseFloat(dailyReport?.metaCarboidratos))
    }
    function getFatPercentage() {
        return Number.parseInt(Number.parseFloat(dailyReport?.lipidios) * 100 / Number.parseFloat(dailyReport?.metaLipidios))
    }

    return (
        <div className='tracker'>
            <Header page='tracker' />
            <div className='container tracker-screen'>
                <div className='tracker-header'>
                    <h2>Olá, {user.nome}</h2>
                    <div className='tracker-date'>
                        <h2>{dayOfWeek}, {dayOfMonth}/{month}</h2>
                        <input type='date' autoComplete="off" value={chosenDate} onChange={handleChange} max={currentDate} />
                    </div>
                </div>
                <div className='tracker-screen-content'>
                    <div className='tracker-left-page'>

                        <div className='tracker-calories'>
                            <div className='tracker-calories-info'>
                                <span>Meta diária</span>
                                <h2>{(dailyReport?.metaCalorias || '0') + 'kcal'}</h2>
                            </div>

                            <div className='tracker-calories-circle'>
                                <ProgressCircle consumed={dailyReport?.calorias} goal={dailyReport?.metaCalorias} />
                            </div>
                            <div className='tracker-calories-info'>
                                <span>Consumidas</span>
                                <h2>{dailyReport?.calorias || 0}kcal</h2>
                            </div>
                        </div>

                        <div className='tracker-water-card'>
                            <div className='tracker-water-card-top'>
                                <div className='tracker-water-consumed'>
                                    <span>Água consumida</span>
                                    <h2>{dailyReport?.agua || 0}ml</h2>
                                </div>
                                <div className='tracker-water-goal'>
                                    <span>Meta de água</span>
                                    <h2>{dailyReport?.metaAgua || 0}ml</h2>
                                </div>
                            </div>
                            <div className='tracker-water-card-buttons'>
                                <button value={-200} onClick={handleWater}>-</button>
                                <span>200ml</span>
                                <button value={200} onClick={handleWater}>+</button>
                            </div>
                        </div>
                        <div className='tracker-macros-card'>
                            <div className='tracker-macros-info'>
                                <h3>Proteinas</h3>
                                <div className='tracker-macros-completion'>
                                    <div className='tracker-macros-bar'><div className='tracker-macros-bar-progress'
                                        style={{ width: `${getProteinPercentage()}%` }}></div></div>
                                    <span>{dailyReport?.proteinas}/{dailyReport?.metaProteinas}</span>
                                </div>
                            </div>
                            <div className='tracker-macros-info'>
                                <h3>Carboidratos</h3>
                                <div className='tracker-macros-completion'>
                                    <div className='tracker-macros-bar'><div className='tracker-macros-bar-progress'
                                        style={{ width: `${getCarboPercentage()}%` }}></div></div>
                                    <span>{dailyReport?.carboidratos}/{dailyReport?.metaCarboidratos}</span>
                                </div>
                            </div>
                            <div className='tracker-macros-info'>
                                <h3>Gorduras</h3>
                                <div className='tracker-macros-completion'>
                                    <div className='tracker-macros-bar'><div className='tracker-macros-bar-progress'
                                        style={{ width: `${getFatPercentage()}%` }}></div></div>
                                    <span>{dailyReport?.lipidios}/{dailyReport?.metaLipidios}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='tracker-right-page'>
                        <h2 className='tracker-meals-header'>Diário</h2>
                        <div className='tracker-meals-list scrollbar'>
                            <MealCard name={'Café da manhã'} meal={mealList.find(meal => meal.periodo === 'CM')} setReload={changeReload} day={chosenDate} period='CM' />
                            <MealCard name={'Lanche da manhã'} meal={mealList.find(meal => meal.periodo === 'LM')} setReload={changeReload} day={chosenDate} period='LM' />
                            <MealCard name={'Almoço'} meal={mealList.find(meal => meal.periodo === 'AL')} setReload={changeReload} day={chosenDate} period='AL' />
                            <MealCard name={'Lanche da Tarde'} meal={mealList.find(meal => meal.periodo === 'LT')} setReload={changeReload} day={chosenDate} period='LT' />
                            <MealCard name={'Jantar'} meal={mealList.find(meal => meal.periodo === 'JA')} setReload={changeReload} day={chosenDate} period='JA' />
                            <MealCard name={'Ceia'} meal={mealList.find(meal => meal.periodo === 'CE')} setReload={changeReload} day={chosenDate} period='CE' />
                        </div>

                    </div>
                </div>
            </div>
        </div>
    )
}
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useGlobalUser from '../../../context/user.context';
import { Button } from '../../components/button/button.component';
import { ChatButton } from '../../components/chatButton/chatButton.component';
import { Header } from '../../components/header/header.component';
import { RecipeList } from '../../components/recipeList/recipeList.component';
import './recipes.css'

export function RecipesScreen() {
    const [loggedUser] = useGlobalUser();
    const [alergies, setAlergies] = useState([])
    const [currentList, setCurrentList] = useState('my')
    const navigate = useNavigate()

    function handleChange(event) {
        const { value, checked } = event.target
        if (checked) {
            setAlergies(alergiesList => [...alergiesList, value])
        }
        else setAlergies(alergies.filter(alergie => alergie !== value))
    }

    function getCurrentList() {
        if (currentList === 'top')
            return (
                <div className='recipe-screen-top'>
                    <div className='recipe-screen-top-filter border'>
                        <h2>Filtrar</h2>
                        <div className='recipe-screen-top-alergies'>
                            <span>Alergias</span>
                            <div className='recipe-screen-top-alergies-item'>
                                <input value='leite' onChange={handleChange} type='checkbox'></input>
                                <span>Leite</span>
                            </div>
                            <div className='recipe-screen-top-alergies-item'>
                                <input value='ovo' onChange={handleChange} type='checkbox'></input>
                                <span>Ovo</span>
                            </div>
                            <div className='recipe-screen-top-alergies-item'>
                                <input value='peixe' onChange={handleChange} type='checkbox'></input>
                                <span>Peixe</span>
                            </div>
                            <div className='recipe-screen-top-alergies-item'>
                                <input value='soja' onChange={handleChange} type='checkbox'></input>
                                <span>Soja</span>
                            </div>
                            <div className='recipe-screen-top-alergies-item'>
                                <input value='trigo' onChange={handleChange} type='checkbox'></input>
                                <span>Trigo</span>
                            </div>
                        </div>
                    </div>
                    <RecipeList type='top' alergies={alergies} />
                </div>
            )
        if (currentList === 'following')
            return (
                <RecipeList type='following' />
            )

        return (
            <>
                <Button text={'Criar receita'} onClick={() => navigate('/receita')} />
                <RecipeList type={loggedUser.id} />
            </>
        )
    }

    return (
        <div className='recipe'>
            <Header page='recipe' />
            <div className='container recipe-screen'>

                <h2>RECEITAS</h2>
                <div className='recipe-list-options'>
                    <button className={currentList === 'my' ? 'selected-recipe-list' : ''} onClick={() => setCurrentList('my')}>Minhas Receitas</button>
                    <button className={currentList === 'following' ? 'selected-recipe-list' : ''} onClick={() => setCurrentList('following')}>Seguindo</button>
                    <button className={currentList === 'top' ? 'selected-recipe-list' : ''} id='recipe-list-options-last' onClick={() => setCurrentList('top')}>Top Receitas</button>
                </div>
                <div className='recipe-screen-list'>
                    {getCurrentList()}
                </div>
            </div>
            <ChatButton closed />
        </div>
    )
}
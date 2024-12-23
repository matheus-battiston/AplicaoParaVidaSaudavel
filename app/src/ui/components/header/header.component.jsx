import './header.css'
import { useNavigate } from "react-router-dom";
import { useState } from 'react';
import useGlobalUser from '../../../context/user.context';
import defaultImage from '../../../images/default.png'
import logo from '../../../images/logoHorizontal.png'
import icon from '../../../images/headerIcon.png'


export function Header({ page }) {
    const [user,] = useGlobalUser()
    const [menuOpen, setMenuOpen] = useState(false);

    const navigate = useNavigate()
    function getClass(name) {
        if (name === page) return 'selected-page'
        else return null
    }

    return (
        <>
            <div className='container header-container'>
                <div className='header-logo'>
                    <img src={logo} alt='logo' />
                    <div className='header-burger'>
                        <img src={icon} className="menu-icon" onClick={() => setMenuOpen(!menuOpen)} />

                    </div>
                </div>

                <div className={'header-items' + (menuOpen ? ' open' : '')}>
                    <button onClick={() => navigate('/')} className={getClass('home')}>Comunidade</button>
                    <button onClick={() => navigate('/receitas')} className={getClass('recipe')}>Receitas</button>
                    <button onClick={() => navigate('/diario')} className={getClass('tracker')}>Diário</button>
                    <img onClick={() => navigate('/perfil/' + user.id)} className='border' src={user.foto || defaultImage} alt='usuario' />
                </div>
            </div>
        </>
    )
}
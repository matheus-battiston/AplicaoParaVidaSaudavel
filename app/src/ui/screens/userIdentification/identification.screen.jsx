import './identification.css'
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from 'react';
import useGlobalUser from '../../../context/user.context';
import { login } from '../../../api/user/identification/login.api';
import { register } from '../../../api/user/identification/register.api';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Button } from '../../components/button/button.component';
import logo from '../../../images/logoVertical.svg'
import logoHorizontal from '../../../images/logoHorizontal.png'

export function IdentificationScreen() {
    const navigate = useNavigate()
    const [page, setPage] = useState('login')
    const [currentDate] = useState(new Date().toLocaleDateString().split('/').reverse().join('-'))
    const [loginInput, setLoginInput] = useState({
        username: { value: '', error: '' },
        password: { value: '', error: '' },
        error: ''
    })
    const [registerInput, setRegisterInput] = useState({
        name: { value: '', error: '' },
        email: { value: '', error: '' },
        password: { value: '', error: '' },
        birthDate: { value: '', error: '' },
        error: ''
    })

    const [user, setUser] = useGlobalUser()

    function recoverPassword() {
        navigate('/recuperar-senha')
    }

    function handleLoginChange(event) {
        const { name, value } = event.target
        setLoginInput(oldFormInput => ({ ...oldFormInput, [name]: { ...oldFormInput[name], value: value } }))
    }

    async function handleLoginSubmit(event) {
        event.preventDefault()
        if (validateLoginUsername() && validateLoginPassword()) {
            try {
                const user = await login({ username: loginInput.username.value, password: loginInput.password.value })
                setUser(user)
            }
            catch (error) {
                setLoginInput(oldInput => ({ ...oldInput, error: "Senha inválida ou email não cadastrado" }))
            }
        }
    }

    useEffect(() => {
        if (user) {
            navigate('/')
        }
    }, [user, navigate])

    function validateLoginUsername() {
        if (!loginInput.username.value) {
            setLoginInput(oldFormInput => ({ ...oldFormInput, username: { ...oldFormInput.username, error: 'Nome não pode ser vazio' } }))
            return false
        }
        setLoginInput(oldFormInput => ({ ...oldFormInput, username: { ...oldFormInput.username, error: '' } }))
        return true
    }
    function validateLoginPassword() {
        if (!loginInput.password.value) {
            setLoginInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: 'Senha não pode ser vazia' } }))
            return false
        }
        setLoginInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: '' } }))
        return true
    }


    function handleRegisterChange(event) {
        const { name, value } = event.target
        if (name !== 'birthDate')
            setRegisterInput(oldFormInput => ({ ...oldFormInput, [name]: { ...oldFormInput[name], value: value } }))
        else if (value <= currentDate)
            setRegisterInput(oldFormInput => ({ ...oldFormInput, [name]: { ...oldFormInput[name], value: value } }))

    }

    async function handleRegisterSubmit(event) {
        event.preventDefault()
        if (validateRegisterUsername() && validateRegisterEmail() && validateRegisterPassword() && validateRegisterBirthDate()) {
            const response = await register(registerInput)
            if (response.response) {
                setRegisterInput(oldFormInput => ({ ...oldFormInput, error: "Email já cadastrado" }))
            }
            else {
                toast("Usuário Cadastrado!")
                setLoginInput(oldInput => ({ ...oldInput, error: '' }))
                setPage('login')

            }

        }

    }

    function validateRegisterUsername() {
        if (!registerInput.name.value) {
            setRegisterInput(oldFormInput => ({ ...oldFormInput, name: { ...oldFormInput.name, error: 'Campo Obrigatório' } }))
            return false
        }
        setRegisterInput(oldFormInput => ({ ...oldFormInput, name: { ...oldFormInput.name, error: '' } }))
        return true
    }
    function validateRegisterEmail() {
        if (!registerInput.email.value) {
            setRegisterInput(oldFormInput => ({ ...oldFormInput, email: { ...oldFormInput.email, error: 'Campo Obrigatório' } }))
            return false
        }
        if (!registerInput.email.value.match('@')) {
            setRegisterInput(oldFormInput => ({ ...oldFormInput, email: { ...oldFormInput.email, error: 'Email inválido' } }))
        }
        setRegisterInput(oldFormInput => ({ ...oldFormInput, email: { ...oldFormInput.email, error: '' } }))
        return true
    }
    function validateRegisterPassword() {
        if (!registerInput.password.value) {
            setRegisterInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: 'Campo Obrigatório' } }))
            return false
        }
        setRegisterInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: '' } }))
        return true
    }
    function validateRegisterBirthDate() {


        if (!registerInput.birthDate.value) {
            setRegisterInput(oldFormInput => ({ ...oldFormInput, birthDate: { ...oldFormInput.birthDate, error: 'Campo Obrigatório' } }))
            return false
        }


        if (new Date(registerInput.birthDate.value) > new Date()){
            setRegisterInput(oldFormInput => ({ ...oldFormInput, birthDate: { ...oldFormInput.birthDate, error: 'Data de nascimento invalida' } }))
            return false
        }

        setRegisterInput(oldFormInput => ({ ...oldFormInput, birthDate: { ...oldFormInput.birthDate, error: '' } }))
        return true
    }
    return (
        <div className='identification-screen-container'>
            <ToastContainer />
            <div className='identification-logo-vertical'>
                <img src={logo} alt='logo' />
            </div>
            <div className='identification-logo-horizontal'>
                <img src={logoHorizontal} alt='logo' />
            </div>
            {page === 'login' ?
                <form className="identification-form" onSubmit={handleLoginSubmit}>
                    <h1>Login</h1>
                    <div className='form-item'>
                        <label>Email</label>
                        <input
                            className="identification-input"
                            name="username"
                            autoComplete="off"
                            value={loginInput.username.value}
                            onChange={handleLoginChange}
                        />
                        <span className='error'>{loginInput.username.error}</span>
                    </div>
                    <div className='form-item'>
                        <label className="identification-lable">Senha</label>
                        <input
                            type='password'
                            className="identification-input"
                            name="password"
                            autoComplete="off"
                            value={loginInput.password.value}
                            onChange={handleLoginChange}
                        />
                        <span className='error'>{loginInput.password.error}</span>
                    </div>
                    <div className='identification-form-button'>
                        <Button text={'Entrar'} />
                        <span className='error'>{loginInput.error || null}</span>
                    </div>
                    <span>Não possui uma conta? <a onClick={() => setPage('register')}>Registre-se</a> </span>
                    <span onClick={recoverPassword}>Esqueci minha senha</span>
                </form>
                : null}


            {page === 'register' ?
                <form className="identification-form" onSubmit={handleRegisterSubmit}>
                    <h1>Registre-se</h1>
                    <div className='form-item'>
                        <label>Nome completo</label>
                        <input className="identification-input" name="name" autoComplete="off" value={registerInput.name.value} onChange={handleRegisterChange} />
                        <span className='error'>{registerInput.name.error}</span>
                    </div>
                    <div className='form-item'>
                        <label>Email</label>
                        <input className="identification-input" type='email' name="email" autoComplete="off" value={registerInput.email.value} onChange={handleRegisterChange} />
                        <span className='error'>{registerInput.email.error}</span>
                    </div>
                    <div className='form-item'>
                        <label>Senha</label>
                        <input className="identification-input" type='password' name="password" autoComplete="off" value={registerInput.password.value} onChange={handleRegisterChange} />
                        <span className='error'>{registerInput.password.error}</span>
                    </div>
                    <div className='form-item'>
                        <label>Data de Nascimento</label>
                        <input className="identification-input" type='date' name="birthDate" autoComplete="off" value={registerInput.birthDate.value} onChange={handleRegisterChange} />
                        <span className='error'>{registerInput.birthDate.error}</span>
                    </div>
                    <div className='identification-form-button'>
                        <Button text={'Registrar'} />
                        <span className='error'>{registerInput.error}</span>
                    </div>
                    <span>Já possui uma conta? <a onClick={() => setPage('login')}>Login</a> </span>
                </form>
                : null}

        </div>
    )
}
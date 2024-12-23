import "./recoverPassword.css"
import logoVertical from "../../../images/logoVertical.svg"
import { Button } from "../../components/button/button.component"
import { useResetPassword } from "../../../api/hooks/useResetPassword/useResetPassword.hook"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { RecoverPasswordContainer } from "../../components/recoverPasswordContainer/recoverPasswordContainer.component" 

export function RecoverPassword(){

    const { response, error, requestPassword } = useResetPassword()
    const [email, setEmail] = useState('')
    const navigate = useNavigate()

    function onChange(event){
        const {value} = event.target
        setEmail(value)
    }

    function sendRecoverRequest(event){
        event.preventDefault()
        requestPassword(email)
    }

    function mainMenu(){
        navigate('/')
    }

    function resetPassword(){
        navigate('/resetar-senha')
    }

    function success(){
        return (
            <>
                <div className="logo-infos-recover">
                    <img className="logo-recover" src={logoVertical}/>
                    <p className="success-recover">Um código foi enviado para o seu email!</p>
                </div>

                <div className="buttons-recover">
                    <Button type='second' text='Voltar' onClick={mainMenu}/>
                    <Button type='main' text='Tela de recuperação' onClick={resetPassword}/>
                </div>
            </>
        )

    }

    function recoverPassword(){
        return (
            <>
                <div className="logo-infos-recover">
                    <img className="logo-recover" src={logoVertical}/>
                    <h1 className="title-recover">Recuperação de senha</h1>
                    <form className="input-recover" onSubmit={sendRecoverRequest}>
                        <span className="item-recover">Digite seu email</span>
                        <input type="text" onChange={onChange}/>
                    </form>
                    {error === true? <p className="error">Erro: Revise as informações digitadas</p> : null}
                </div>
                    
                <div className="buttons-recover">
                    <Button type='second' text='Cancelar' onClick={mainMenu}/>
                    <Button type='main' text='Enviar' onClick={sendRecoverRequest}/>
                </div>
            </>
                    
        )
    }

    return (
        <>
            <RecoverPasswordContainer>
                {response === false? recoverPassword() : success()}
            </RecoverPasswordContainer>
        </>
        )
}
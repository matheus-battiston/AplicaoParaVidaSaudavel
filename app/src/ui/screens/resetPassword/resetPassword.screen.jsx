import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { RecoverPasswordContainer } from "../../components/recoverPasswordContainer/recoverPasswordContainer.component"
import "./resetPassword.css"
import logoVertical from "../../../images/logoVertical.svg"
import { Button } from "../../components/button/button.component"
import { useResetPasswordWithToken } from "../../../api/hooks/useResetPassword/useResetPasswrodWithToken.hook"


export function ResetPasswordScreen(){


    const {response ,error, requestPasswordWithToken} = useResetPasswordWithToken()
    const [input, setInput] = useState({email:'', password: '', token: ''})
    const navigate = useNavigate()

    function cancel(){
        navigate('/')
    }

    function onChange(event){
        const {value, name} = event.target

        setInput(oldInput => ({...oldInput, [name]:value}))
    }

    function send(){
        requestPasswordWithToken(input)
    }

    function resetSuccess(){
        return (
            <>
                <div className="logo-infos-recover">
                    <img className="logo-recover" src={logoVertical}/>
                    <p className="success-recover">Senha alterada com sucesso!</p>
                </div>

                <div className="buttons-reset">
                    <Button type='second' text='Voltar' onClick={cancel}/>
                </div>
            </>
        )
    }

    function resetPassword(){

       return ( <>
            <div className="logo-infos-recover">
                <img className="logo-recover" src={logoVertical}/>
                <h1 className="title-recover">Alteração de senha</h1>
                <form className="input-recover">
                    <span className="item-recover">Email</span>
                    <input name="email" className="input-recover" type="text" onChange={onChange}/>

                    <span className="item-recover">Token</span>
                    <input name="token" className="input-recover" type="text" onChange={onChange}/>

                    <span className="item-recover">Nova senha</span>
                    <input name="password" className="input-recover" type="text" onChange={onChange}/>
                </form>
                {error === true? <p className="error">Erro: Revise as informações digitadas</p> : null}
            </div>
            <div className="buttons-recover">
                <Button type='second' text='Cancelar' onClick={cancel}/>
                <Button type='main' text='Enviar' onClick={send}/>
            </div>
        </>)
    }

    return (
        <RecoverPasswordContainer>
            {response === false? resetPassword() : resetSuccess()}
            
        </RecoverPasswordContainer>
    )
}
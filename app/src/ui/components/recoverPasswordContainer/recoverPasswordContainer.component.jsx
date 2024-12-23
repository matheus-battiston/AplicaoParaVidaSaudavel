import "./recoverPasswordContainer.css"

export function RecoverPasswordContainer({children}){
    return (
            <div className="recover-password">
                <div className="recover-password-content">
                    {children}
                </div>
            </div>
    )
}
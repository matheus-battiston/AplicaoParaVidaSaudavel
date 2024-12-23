import "./cardAchievement.css"
import { Button } from "../button/button.component"
import { setTag } from "../../../api/user/achievements/setTag.api"

export function CardAchievement({achievement, reload, setReload}) {

    async function onClick(){
        await setTag(achievement.idConquista)
        setReload(!reload)
    }

    function getPercentage(){
        return (achievement.progresso/achievement.objetivo)*100
    }

    function completed(){
        return (
            <Button type={`${achievement.categoria}-button`} text="Usar tag" onClick={onClick}/>
        )
    }

    function progress(){
        if (achievement.categoria === "PLATINA"){
            return
        }

        return (
            <div className="progress">
                <div className="progress-bar-outside size-progress-bar">
                    <div className={`progress-bar-inside size-progress-bar ${achievement.categoria}-progress`} style={{width: `${getPercentage()}%`}}>
                    </div>
                </div>
                <p className="progress-text">{achievement.progresso}/{achievement.objetivo}</p>
            </div>
            
        )
    }
    return (
        <div className={`card-style ${achievement.categoria} ${achievement.desbloqueada === true? null : 'incomplete'}`}>
            <div className={`header-achievement ${achievement.categoria}-progress`}>
                <h2 className="title-achievement font-achievement">{achievement.categoria}</h2>

            </div>
            <h2 className="title-achievement font-achievement">{achievement.recompensa}</h2>
            <p className="description-achievement font-achievement">{achievement.descricao}</p>
            {achievement.desbloqueada === true? completed() : progress()}
        </div>
    )
}
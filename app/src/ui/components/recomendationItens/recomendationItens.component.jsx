import "./recomendationItens.css"
import editButton from "../../../images/edit-green.svg"
import checkButton from "../../../images/check-circle-2.svg"
import cancelButton from "../../../images/x-circle.svg"
import { useState } from "react"

export function RecomendationItens({edit, itemName, itemInfo, itemRef, editFunc, submit}){

    const [input, setInput] = useState("")

    function onChange (event){
        const {value} = event.target
        setInput(value)
    }

    function submitEntry(){
        submit(input, itemName)
    }

    function setEdit(){
        editFunc(itemName)
    }

    function removeEdit(){
        editFunc(null)
    }

    function notEditing(){
        return (
            <>
                <p className="item-info">{itemInfo}{itemRef}</p>
                <img onClick={setEdit} className="edit-button" src={editButton} alt="caneta verde"/> 
            </>
        )
    }

    function editing(){
        return (
                <form className="form-edit" onSubmit={submitEntry}>
                    <input className="item-info input-edit" type="text" onChange={onChange} defaultValue={itemInfo}/>
                    <div className="action-buttons-confirm-cancel">
                        <img onClick={removeEdit} className="edit-button" src={cancelButton} alt="X circulado em volta em vermelho"/> 
                        <img onClick={submitEntry} className="edit-button" src={checkButton} alt="simbolo de confirmar em verde"/> 
                    </div>
                </form> 
        )
    }

    return (
        <>
            <p className="item-title">{itemName}</p>
            <div className="info-edit">
                {edit === true? editing() : notEditing()}
            </div>
        </>
    )
}
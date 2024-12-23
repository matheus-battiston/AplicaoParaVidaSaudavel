import "./editProfileImage.css"
import { Modal } from "../modal/modal.component"
import { Button } from "../button/button.component"
import { editProfilePhoto } from "../../../api/user/edit/edit-image.api"
import { useState } from "react"
import useGlobalUser from "../../../context/user.context"

export function EditProfileImage({setEditImage, reload, setReload}){

    const [url, setUrl] = useState('')
    const [, setUser] = useGlobalUser()

    function closeEditProfileImage(){
        setEditImage(false)
        setUrl('')
    }

    function onChange(event){
        const {value} = event.target
        setUrl(value)
    }

    async function submit(event){
        event.preventDefault()
        await editProfilePhoto(url)
        setEditImage(false)
        setUser(user => ({...user, foto: url}))
        setUrl('')
        setReload(!reload)
    }


    return (
        <Modal small="true" onClose={closeEditProfileImage}>
            <form className='form-edit-image' onSubmit={submit}>
                <h1 className='title-edit-profile-image'>Alterar imagem</h1>
                <div>
                    <label>URL da imagem</label>
                    <input className='input-edit-profile-image' type="text" onChange={onChange}/>
                </div>
                <Button type="main" text="Enviar"/>
            </form>
        </Modal>
    )
}
import { useState } from 'react';
import { usePostFunctions } from '../../../api/hooks/usePostsFunctions.hook';
import useGlobalUser from '../../../context/user.context';
import './postForm.css'
import defaultImage from '../../../images/default.png'
import { Modal } from '../modal/modal.component';
import { Button } from '../button/button.component';



export function PostForm({ onClose, setReload }) {
    const { makePost } = usePostFunctions()
    const [user] = useGlobalUser()
    const [postInput, setPostInput] = useState({
        text: { value: '', error: '' },
        image: { value: '', error: '' },
        privacy: { value: false }
    })
    function handleChange(event) {
        const { name, value } = event.target
        setPostInput(oldInput => ({ ...oldInput, [name]: { ...oldInput[name], value: value } }))
    }
    async function handleSubmit(event) {
        event.preventDefault()

        if (postInput.text.value.length === 0) {
            setPostInput(oldInput => ({ ...oldInput, text: { ...oldInput.text, error: 'Campo não pode ser vazio' } }))
            return
        }
        if (postInput.text.value.length > 511) {
            setPostInput(oldInput => ({ ...oldInput, text: { ...oldInput.text, error: 'Campo deve ter até 511 caractéres' } }))
            return
        }
        if (postInput.image.value.length > 511) {
            setPostInput(oldInput => ({ ...oldInput, text: { ...oldInput.text, error: '' } }))
            setPostInput(oldInput => ({ ...oldInput, image: { ...oldInput.image, error: 'Campo deve ter até 511 caractéres' } }))
            return
        }
        setPostInput(oldInput => ({ ...oldInput, image: { ...oldInput.image, error: '' } }))
        await makePost(postInput)
        onClose()
        setReload(reload => !reload)
    }
    return (
        <Modal onClose={onClose}>
            <div className="post-form-card">
                <div className='post-form-header'>
                    <h2>Novo Post</h2>
                    <div className='post-form-info'>
                        <div className='post-form-info-user'>
                            <img src={user.foto}  onError={(e) => e.target.src = defaultImage} alt='usuario'/>
                            <h2>{user.nome}</h2>
                        </div>
                        <select name='privacy' onChange={handleChange}>
                            <option value={false} name='privacy'>Público</option>
                            <option value={true} name='privacy'>Privado</option>
                        </select>
                    </div>
                </div>

                <form onSubmit={handleSubmit} className='post-form'>
                    <div className='post-form-item'>
                        <textarea className='scrollbar' rows='8' value={postInput.text.value} name='text' onChange={handleChange} placeholder='Digite sua dúvida, opinião ou curiosidade!'></textarea>
                        <span className='error'>{postInput.text.error}</span>
                    </div>
                    <div className='post-form-item'>
                        <input name='image' value={postInput.image.value} onChange={handleChange} placeholder='Insira a URL de uma imagem (opcional)'></input>
                        <span className='error'>{postInput.image.error}</span>
                    </div>
                    <Button text='Publicar' />

                </form>
            </div>
        </Modal>
    );
}
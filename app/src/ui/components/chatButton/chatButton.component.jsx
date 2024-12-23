import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'
import { useUserFunctions } from '../../../api/hooks/useUserFunctions.hook';
import messageIcon from '../../../images/message-circle.svg'
import { ChatBox } from '../chatBox/chatBox.component';
import './chatButton.css'

export function ChatButton({closed}) {
    const [openChat, setOpenChat] = useState(false)
    const { getUserDetails } = useUserFunctions()
    const [sender, setSender] = useState('')

    function handleClickEvent() {
        setOpenChat(!openChat)
    }

    useEffect(() => {
        if(closed){
            setOpenChat(false)
        } else setOpenChat(true)
    }, [closed])

    useEffect(() => {
        async function fetchUserDetails() {
            const response = await getUserDetails()
            setSender(response)
        }

        fetchUserDetails()
    }, [])

     return !openChat ? 
            <button className="chat-button" onClick={handleClickEvent}>
                <img src={messageIcon} className='chat-icon' alt='Abrir mensagens' />
            </button>
            :
            <ChatBox sender={sender} />
}
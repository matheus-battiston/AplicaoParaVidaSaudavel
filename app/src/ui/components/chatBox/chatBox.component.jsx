import { useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';
import { useUserFunctions } from '../../../api/hooks/useUserFunctions.hook';
import { ChatMessage } from '../chatMessage/chatMessage.component';
import './chatBox.css'
import defaultImage from '../../../images/default.png'
import closeButton from '../../../images/close-button.svg'
import { ChatButton } from '../chatButton/chatButton.component';
import sendImage from '../../../images/send.svg'
import goBack from '../../../images/corner-down-left.svg'


let stompClient = null;

export function ChatBox({ sender }) {
    const [tab, setTab] = useState({});
    const [userData, setUserData] = useState({
        username: sender.nome,
        receivername: '',
        connected: false,
        message: ''
    });
    const { getUserConversations, getUserMessages } = useUserFunctions()
    const [conversations, setConversations] = useState([])
    const [messages, setMessages] = useState([])
    const [visible, setVisible] = useState(true)
    const [hideContacts, setHideContacts] = useState(false)
    const [hideTextBox, setHideTextBox] = useState(false)
    const messageEndRef = useRef(null)

    const connect = () => {
        let Sock = new SockJS('http://localhost:8080/ws');
        stompClient = over(Sock);
        stompClient.debug = null;
        stompClient.connect({}, onConnected, onError);
    }

    useEffect(() => {
        async function fetchUserConversations() {
            const response = await getUserConversations()
            setConversations(response)

        }
        connect()

        fetchUserConversations()
    }, [])

    useEffect(() => {
        async function fetchUserMessages() {
            const response = await getUserMessages(tab.idConversa)
            setMessages(response)
        }

        if (tab.idUsuario) {
            fetchUserMessages()
        }

    }, [tab])

    useEffect(() => {
        messageEndRef.current?.scrollIntoView()
    }, [messages])

    const onConnected = () => {
        setUserData({ ...userData, "connected": true });
        stompClient.subscribe('/user/' + sender.id + '/private', onPrivateMessage);
        userJoin();
    }

    const userJoin = () => {
        let chatMessage = {
            senderName: userData.username,
            status: "JOIN"
        };
        stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    }
    const onPrivateMessage = (payload) => {
        let payloadData = JSON.parse(payload.body);

        const message = {
            idRemetente: payloadData.senderId,
            mensagem: payloadData.message
        }

        setMessages(oldContent => ([...oldContent, message]))

    }

    const onError = (err) => {
        console.log(err)
    }

    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "message": value });
    }

    const sendPrivateValue = () => {
        if (stompClient) {
            let chatMessage = {
                senderId: sender.id,
                receiverId: tab.idUsuario,
                message: userData.message,
                status: "MESSAGE"
            };

            if (chatMessage.message) {
                stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));

                setUserData({ ...userData, "message": "" });
                setMessages(oldContent => ([...oldContent, {
                    idRemetente: chatMessage.senderId,
                    mensagem: chatMessage.message
                }]))
            }
        }
    }

    return visible ? (
        <div className='chat-container'>
            <div className={`chat-members-box ${hideContacts ? 'hidden' : ''}`}>
                <ul className='chat-member-list'>
                    {conversations.map((conversation, index) => (
                        conversation ?
                            <div
                                className={`chat-member ${conversation === tab ? 'selected' : ''} `}
                                key={index}
                                onClick={() => {
                                    setTab(conversation)
                                    setHideContacts(true)
                                    setHideTextBox(false)
                                    setUserData(oldData => ({ ...oldData, receivername: conversation.nome }))
                                }}>
                                {conversation.foto ?
                                    <img src={conversation.foto} alt={conversation.nome} />
                                    :
                                    <img src={defaultImage} alt={conversation.nome} />}

                                <li>{conversation.nome}</li>
                            </div>
                            :
                            null
                    ))}
                </ul>
            </div>
            {tab.idUsuario ?
                <div className={`main-chat-box ${hideTextBox ? 'hidden' : ''}`}>
                    <div className='chat-header'>
                        <div className='chat-header-user-info'>
                            {tab.foto ?
                                <>
                                    <img src={goBack} className='go-back-icon' onClick={() => {setHideContacts(false); setHideTextBox(true)}} alt="Voltar" />
                                    <img src={tab.foto} alt={tab.nome} />
                                </>
                                :
                                <>
                                    <img src={goBack} className='go-back-icon' onClick={() => {setHideContacts(false); setHideTextBox(true)}} alt="Voltar" />
                                    <img src={defaultImage} alt={tab.nome} />
                                </>
                            }
                            <div className='chat-header-user-name'>
                                <p>{tab.nome}</p>
                            </div>
                        </div>
                        <img
                            src={closeButton}
                            className='chat-close-button'
                            alt="Fechar"
                            onClick={() => { stompClient.disconnect(); setVisible(!visible) }} />
                    </div>
                    <div className='messages-box'>
                        {tab.idUsuario && <div className="chat-content">
                            <ul className="chat-messages">
                                {messages.map((message, index) => (
                                    message.idRemetente === sender.id ?
                                        <ChatMessage type='sent' key={index}>{message.mensagem}</ChatMessage>
                                        :
                                        <ChatMessage type='received' key={index}>{message.mensagem}</ChatMessage>
                                ))}
                                <div ref={messageEndRef} />
                            </ul>
                        </div>}

                    </div>
                    <div className='message-input-box'>
                        <input
                            type="text"
                            className='message-input'
                            name="message"
                            id="message"
                            placeholder='Digite uma mensagem...'
                            onChange={handleMessage}
                            value={userData.message}
                            autoComplete='off' />
                        <button className='message-send-button' onClick={sendPrivateValue}>Enviar</button>
                    </div>
                </div>
                :
                <div className='no-conversation-selected-box'>
                    <div className='no-conversation-selected-header'>
                        <img
                            src={closeButton}
                            className='chat-close-button'
                            alt="Fechar"
                            onClick={() => { stompClient.disconnect(); setVisible(!visible) }} />
                    </div>
                    <div className='no-conversation-selected-main'>
                        <img src={sendImage} alt="" />
                        <p>Clique em um contato para começar a conversar</p>
                    </div>
                </div>
            }
        </div >
    )
        :
        <ChatButton closed />

}

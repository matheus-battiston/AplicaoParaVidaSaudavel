import './userPage.css'
import { useParams } from 'react-router-dom'
import { Header } from '../../components/header/header.component'
import { useEffect, useState } from 'react'
import { useUserFunctions } from '../../../api/hooks/useUserFunctions.hook'
import useGlobalUser from '../../../context/user.context'
import { PostList } from '../../components/postList/postList.component'
import { Button } from '../../components/button/button.component'
import defaultImage from '../../../images/default.png'
import { RecipeList } from '../../components/recipeList/recipeList.component'
import { ChatButton } from '../../components/chatButton/chatButton.component'
import {DetailedUserInfos} from "../../components/detailedUserInfos/detailedUserInfos.component"
import { Achievements } from '../../components/achievements/achievements.component'
import { Modal } from '../../components/modal/modal.component'
import { editProfilePhoto } from '../../../api/user/edit/edit-image.api'
import { EditProfileImage } from '../../components/editProfileImage/editProfileImage.component'

export function UserPage() {
    const [loggedUser] = useGlobalUser()
    const { getUserInfo, getUserDetails, followUser, unfollowUser, addConversation } = useUserFunctions()
    const { id } = useParams()
    const [currentList, setCurrentList] = useState('posts')
    const [content, setContent] = useState({
        info: [],
        details: []
    })
    const [reload, setReload] = useState(false)
    const [sendMessage, setSendMessage] = useState(false)
    const [editImage, setEditImage] = useState(false)
    const [url, setUrl] = useState('')

    function editProfileImage(){
        setEditImage(true)
    }



    useEffect(() => {

        async function fetchUserInfo() {
            const response = await getUserInfo(id)
            setContent(oldContent => ({ ...oldContent, info: response }))
        }
        async function fetchUserDetails() {
            const response = await getUserDetails()
            setContent(oldContent => ({ ...oldContent, details: response }))
        }

        fetchUserInfo()
        if (currentList === 'profile') fetchUserDetails()

    }, [reload, currentList, id])

    useEffect(() => {
        
    }, [content])

    function getMessageButton() {
        return <Button text={'Mensagem'} type='second' onClick={async () => { await addConversation(content.info.id); setSendMessage(!sendMessage)}} />
    }

    function getCurrentList() {
        if (currentList === 'profile') return <DetailedUserInfos reload={reload} setReload={setReload} infos={content.details}/>
        if (currentList === 'achievements') return <Achievements reload={reload} setReload={setReload}/>
        if (currentList === 'recipes') return <RecipeList type={id} setReload={setReload} reload={reload} />
        return <PostList userId={id} setReload={setReload} reload={reload} />
    }
    function getFollowButton() {

        if (content.info.idSeguidores?.find(id => id === loggedUser.id))
            return <Button text={'Deixar de seguir'} onClick={async () => { await unfollowUser(content.info.id); setReload(value => !value) }} />
        return <Button text={'Seguir'} onClick={async () => { await followUser(content.info.id); setReload(value => !value) }} />
    }


    return (
        <div className='user'>
            {editImage === true? 
                <EditProfileImage setEditImage={setEditImage} reload={reload} setReload={setReload}/> : null}
            <Header page='profile' />
            <div className='container user-page'>
                <div className='user-info'>
                    <div className='user-info-left'>
                        <div className={Number.parseInt(id) === loggedUser.id? 'profile-image-style' : null} onClick={Number.parseInt(id) === loggedUser.id? editProfileImage : null}>
                            <img className='border profile-image img-hover' src={content.info.foto} onError={(e) => e.target.src = defaultImage} alt='usuario'/>
                        </div>
                        <h2 className='name-user'>{content.info.nome}</h2>
                        <span className={`user-title ${content.info.categoriaTag}-tag`}>{content.info.titulo || 'Iniciante'}</span>
                    </div>
                    <div className='user-info-right'>
                        <div className='user-info-right-buttons'>
                            {Number.parseInt(id) !== loggedUser.id
                                ?
                                <>
                                    {getFollowButton()}
                                    {getMessageButton()}
                                </>
                                : null
                            }
                        </div>
                        <div className='user-info-right-details'>
                            <span>{content?.info?.idSeguidores?.length} seguidores</span>
                            <span>{content?.info?.idSeguindo?.length} seguindo</span>
                        </div>
                    </div>
                </div>
                <div className='user-page-options'>
                    {Number.parseInt(id) === loggedUser.id
                        ?
                        <>
                            <button className={currentList==='profile' ? 'bold' : null} onClick={() => setCurrentList('profile')}>Minhas informações</button>
                            <button className={currentList==='achievements' ? 'bold' : null} onClick={() => setCurrentList('achievements')}>Conquistas</button>
                        </>
                        : null}
                    <button className={currentList==='posts' ? 'bold' : null} onClick={() => setCurrentList('posts')}>Posts</button>
                    <button className={currentList==='recipes' ? 'bold' : null} onClick={() => setCurrentList('recipes')}>Receitas</button>
                </div>
                <div className='user-selected-list'>
                    {getCurrentList()}
                </div>
            </div>
            {sendMessage ?
                <ChatButton />
                :
                <ChatButton closed />
            }
        </div>

    )
}
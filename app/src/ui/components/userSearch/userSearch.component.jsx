import './userSearch.css'
import search from '../../../images/search.svg'
import defaultImage from '../../../images/default.png'
import { useUserFunctions } from '../../../api/hooks/useUserFunctions.hook'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { LoadMoreButton } from '../loadMoreButton/loadMoreButton.component'

export function UserSearch() {
    const navigate = useNavigate()
    const { getUserByName } = useUserFunctions()
    const [userList, setUserList] = useState()
    const [input, setInput] = useState()
    const [pageSize, setPageSize] = useState(6)
    

    useEffect(() => {
        async function fetchUsers() {
            const response = await getUserByName(input, pageSize)
            setUserList(response)
        }

        if (input)
            fetchUsers()
        else {
            setUserList(null)
            setPageSize(6)
        }
    }, [input, pageSize])


    async function handleChange(event) {
        setInput(event.target.value)
    }


    return (
        <div className='user-search'>
            <div className='user-search-input'>
                <input placeholder='Pesquisar pessoas' value={input} onChange={handleChange}></input>
                <button><img src={search} /></button>
            </div>
            {userList ? <div className='user-search-list border'>
                {userList?.content?.map(user =>
                <div onClick={() => navigate('/perfil/' + user.id)} className='user-search-user' key={user.id}>
                    <img src={user.foto} onError={(e) => e.target.src = defaultImage} alt='usuario'/>
                    <div className='user-search-user-info'>
                        <h3>{user.nome}</h3>
                        <span>{user.titulo||'Rei da cozinha'}</span>
                    </div>
                </div>
                )}
                <LoadMoreButton pageSize={pageSize} totalElements={userList?.totalElements} setPageSize={setPageSize} />

            </div> : null}
        </div>
    )
}
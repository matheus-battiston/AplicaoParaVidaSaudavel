import { useEffect, useState } from 'react'
import { useRankingFuntions } from '../../../api/hooks/useRankingFunctions.hook'
import { LoadMoreButton } from '../loadMoreButton/loadMoreButton.component'
import defaultImage from '../../../images/default.png'

import './rankingList.css'
import { useNavigate } from 'react-router-dom'

export function RankingList({ reload }) {
    const [ranking, setRanking] = useState({ content: [] })
    const [pageSize, setPageSize] = useState(5)
    const { getRanking } = useRankingFuntions()
    const navigate = useNavigate()

    useEffect(() => {
        async function fetchRanking() {
            const response = await getRanking(pageSize)
            setRanking(response)
        }
        fetchRanking()


    }, [pageSize, reload])
    return (
        <>
            <h2>Ranking</h2>
            <ul className='ranking'>
                {ranking?.content?.map((usuario, index) =>
                    <li className='ranking-user' onClick={() => navigate('perfil/' + usuario.id)} key={usuario.id}>
                        <img className='border' src={usuario.foto} onError={(e) => e.target.src = defaultImage} alt='usuario'/>
                        <div className='ranking-user-info'>
                            <div className='ranking-user-info-top'>
                                <h3>#{index + 1}</h3>
                                <h3>{usuario.nome}</h3>
                            </div>
                            <span>{usuario.pontos} pontos</span>
                        </div>
                    </li>
                )}

                <div className='ranking-load-more'>
                    <LoadMoreButton pageSize={pageSize} totalElements={ranking?.totalElements} setPageSize={setPageSize} />
                </div>
            </ul>
        </>
    )
}
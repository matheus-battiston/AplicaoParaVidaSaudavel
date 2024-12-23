import { useState } from 'react';
import { ChatButton } from '../../components/chatButton/chatButton.component';
import { Header } from '../../components/header/header.component';
import { PostList } from '../../components/postList/postList.component';
import { RankingList } from '../../components/rankingList/rankingList.component';
import { UserSearch } from '../../components/userSearch/userSearch.component';
import './main.css'
import { NoPostsAvailable } from '../../components/noPostsAvailable/noPostsAvailable.component';

export function MainScreen() {
    const [reload, setReload] = useState(false)
    const [chosenPage, setChosenPage] = useState('comunity')

    return (
        <div className='home'>
            <Header page='home' />

            <div className='container home-choose-screen'>
                <button onClick={() => setChosenPage('rank')}>Ranking</button>
                <button onClick={() => setChosenPage('comunity')}>Comunidade</button>
            </div>
            <div className='container home-screen'>
                <div className={'home-screen-ranking ' + (chosenPage === 'rank' ? 'home-chosen' : '')}>
                    <RankingList reload={reload} />
                </div>
                <div className={'home-screen-posts ' + (chosenPage === 'comunity' ? 'home-chosen' : '')}>
                    <div className='home-screen-posts-header'>
                        <h2>Comunidade</h2>
                    </div>
                    <PostList setReload={setReload} reload={reload} />
                    <NoPostsAvailable/>
                </div>
                <UserSearch />

                <ChatButton closed />
            </div>
        </div>
    )
}
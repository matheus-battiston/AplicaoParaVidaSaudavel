import { useEffect, useState } from 'react'
import { usePostFunctions } from '../../../api/hooks/usePostsFunctions.hook'
import './postList.css'
import { PostForm } from '../postForm/postForm'
import { LoadMoreButton } from '../loadMoreButton/loadMoreButton.component'
import { Button } from '../button/button.component'
import { PostCard } from '../postCard/postCard.component'
import useGlobalUser from '../../../context/user.context'
import { NoPostsAvailable } from '../noPostsAvailable/noPostsAvailable.component'
export function PostList({ userId, reload, setReload }) {
    const [posts, setPosts] = useState({ content: [] })
    const [pageSize, setPageSize] = useState(6)
    const [creatingPost, setCreatingPost] = useState(false)
    const [user] = useGlobalUser()

    const { getPostPage, getUserPosts } = usePostFunctions()

    useEffect(() => {
        async function fetchUserPosts() {
            const response = await getUserPosts(userId, pageSize)
            setPosts(response)

        }
        async function fetchPosts() {
            const response = await getPostPage(pageSize)
            setPosts(response)
        }
        if (userId) fetchUserPosts()
        else fetchPosts()

    }, [reload, pageSize])


    return (
        <div className='posts-list'>
            {creatingPost ? <PostForm setReload={setReload} onClose={() => setCreatingPost(false)} /> : null}
            {(userId && user.id.toString() !== userId) ? null : <Button onClick={() => setCreatingPost(true)} text='Criar novo Post' />}
            {posts?.content?.map(post => <PostCard key={post.id} post={post} setReload={setReload} />)}
            <div className='post-list-load-more'>
                <LoadMoreButton pageSize={pageSize} totalElements={posts?.totalElements} setPageSize={setPageSize} />

            </div>
        </div>
    )
}

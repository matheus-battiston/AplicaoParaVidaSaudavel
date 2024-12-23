import { useEffect, useState } from 'react'
import { usePostFunctions } from '../../../api/hooks/usePostsFunctions.hook'
import './commentsList.css'
export function CommentsList({ post }) {
    const { getPostComments } = usePostFunctions()
    const [comments, setComments] = useState([])
    useEffect(() => {
        async function fetchComments() {
            const response = await getPostComments(post)
            setComments(response)
        }
        fetchComments()
    },[])


    return (
        <div className='comments-list-container border'>
            {comments?.map(comment =>
                <div className='comment-card' key={comment.id}>
                    <h3>{comment.usuario.nome}   comentou:</h3>
                    <p>{comment.comentario}</p>
                </div>
            )}
        </div>
    )
}
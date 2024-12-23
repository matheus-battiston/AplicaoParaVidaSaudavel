import './postCard.css'
import { useState } from 'react'
import { usePostFunctions } from '../../../api/hooks/usePostsFunctions.hook'
import { CommentsList } from '../commentsList/commentsList.component'
import defaultImage from '../../../images/default.png'
import emptyHeart from '../../../images/emptyHeart.svg'
import fullheart from '../../../images/fullHeart.svg'
import useGlobalUser from '../../../context/user.context'
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom'


export function PostCard({ post, setReload }) {
    const [postImage, setPostImage] = useState(post.imagem)
    const [loggedUser] = useGlobalUser()
    const [createComment, setCreateComment] = useState(false)
    const [showComments, setShowComment] = useState(false)
    const [showEditPost, setShowEditPost] = useState(false)
    const navigate = useNavigate()
    const [comment, setComment] = useState({
        value: '', error: '', postId: ''
    })
    const { commentPost, likePost, dislikePost, alterPost, deletePost } = usePostFunctions()

    function handleChange(event) {
        const { value } = event.target
        setComment(oldComment => ({ ...oldComment, value: value }))
    }
    async function handleSubmit(event) {
        event.preventDefault()
        const postId = event.target.name
        if (comment.value === '') {
            setComment(oldComment => ({ ...oldComment, error: 'Comentário não pode ser vazio' }))
        }
        else {
            await commentPost(postId, comment.value)
            setCreateComment(null)
            setReload(reload => !reload)
        }
    }
    async function handleLike(post) {
        if (post?.curtido) {
            await dislikePost(post.id)
            setReload(reload => !reload)
        }
        else {
            await likePost(post.id)
            setReload(reload => !reload)
        }
    }

    async function handleAlterPost() {
        if (!post.privado) {
            await alterPost(post.id, true)
            setReload(reload => !reload)
        }
        else {
            await alterPost(post.id, false)
            setReload(reload => !reload)
        }
    }


    function getEditPostOptions() {
        if (post.autor.id === loggedUser.id) {
            return (
                <div className='post-edit-options'>
                    {showEditPost
                        ? <>
                            <button className='post-edit-options-button-clicked' onClick={() => setShowEditPost(false)}>{"<"}</button>
                            <div className='post-edit-options-list'>
                                <button className='post-delete' onClick={async () => { await deletePost(post.id); setReload(reload => !reload) }}>Deletar Post</button>
                                <button className='post-alter' onClick={() => handleAlterPost()}>{post.privado ? 'Tornar público' : 'Tornar privado'}</button>
                            </div>
                        </>
                        : <button className='post-edit-options-button' onClick={() => setShowEditPost(true)}>{"<"}</button>
                    }
                </div>
            )
        }
    }
    return (
        <div key={post.id} className='post border'>
            <div className='post-header'>
                <div onClick={() => navigate('/perfil/' + post.autor.id)} className='post-author-info'>
                    <img className='border' src={post.autor.foto} 
                    onError={(e) => e.target.src = defaultImage}  
                    alt='usuario' />
                    <h3 onClick={() => { }}>{post.autor.nome}</h3>
                    <span>{post.dataInclusao}</span>
                </div>
                {getEditPostOptions()}

            </div>
            <div className='post-content'>
                <span className='post-content-text'>{post.texto}</span>
                {postImage ? <img src={post.imagem} alt='post' onError={() => setPostImage(null)}/> : null}
            </div>
            <div className='post-interactions'>
                <button onClick={() => handleLike(post)}>
                    <span>{post?.nroCurtidas}</span>
                    <img alt='like' src={post.curtido ? fullheart : emptyHeart} />
                </button>
                <button onClick={() => setCreateComment(value => !value)} >Comentar</button>
                <button id='post-show-comments' onClick={() => setShowComment(value => !value)}> Ver Comentarios</button>
            </div>
            {showComments ? <CommentsList post={post.id} /> : null}
            {createComment ?
                <form name={post.id} className='post-comment-form' onSubmit={handleSubmit}>
                    <textarea rows='5' className='scrollbar reate-post-comment' value={comment.value} onChange={handleChange} placeholder='Escreva sua opinião'></textarea>
                    <button>Comentar</button>
                </form>
                : null
            }
        </div>
    )
}
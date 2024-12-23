import { axiosInstance } from "../user/_base/axiosInstance"


export function usePostFunctions() {
    async function getPostPage(size) {
        try {
            const response = await axiosInstance.get('/posts?sort=dataInclusao,desc&size='+ size)
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function getUserPosts(userId, size) {
        try {
            const response = await axiosInstance.get('/posts/usuario/' + userId + '?sort=dataInclusao,desc&size=' + size)
            return response.data
        }
        catch (error) {
            return error
        }
    }

    async function makePost(postInfo)   {
        try {
            const response = await axiosInstance.post('/posts', {
                "texto": postInfo.text.value,
                "url": postInfo.image.value,
                "privado": postInfo.privacy.value
            }) 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function commentPost(postId,text)   {
        try {
            const response = await axiosInstance.post('/posts/' + postId, {
                "texto": text
            }) 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function getPostComments(postId)   {
        try {
            const response = await axiosInstance.get('/posts/' + postId + '/comentarios')
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function likePost(postId)   {
        try {
            const response = await axiosInstance.put('/posts/' + postId+ '/curtir') 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function dislikePost(postId)   {
        try {
            const response = await axiosInstance.put('/posts/' + postId+ '/descurtir') 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function alterPost(postId,privacy)   {
        try {
            const response = await axiosInstance.put('/posts/' + postId, {
                "novaPrivacidade": privacy
            }) 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function deletePost(postId)   {
        try {
            const response = await axiosInstance.delete('/posts/' + postId)
            return response.data
        }
        catch(error) {
            return error
        }
    }
   
        
    return {getPostPage, getUserPosts ,getPostComments, makePost, commentPost, likePost, dislikePost, alterPost, deletePost}
}
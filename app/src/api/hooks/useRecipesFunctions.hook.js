import { axiosInstance } from "../user/_base/axiosInstance";

export function useRecipesFunctions() {

    async function getFollowingRecipes(size) {
        try {
            const response = await axiosInstance.get('/receitas/seguindo?size=' + size)
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function getTopRecipes(size, alergies) {
        try {
            const response = await axiosInstance.post('/receitas/top?size=' + size, {
                alergias: alergies
            })
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function getSuggestedRecipes(recipeId) {
        try {
            const response = await axiosInstance.get('/receitas/'+recipeId+'/sugestoes')
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function getUserRecipes(userId, size) {
        try {
            const response = await axiosInstance.get('/receitas/usuario/' + userId + '?size=' + size)
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function createRecipe(recipe) {
        try {
            const response = await axiosInstance.post('/receitas', {
                modoPreparo: recipe.preparement.value,
                imagem: recipe.image.value,
                titulo: recipe.title.value,
                descricao: recipe.description.value,
                privado: recipe.private.value,
                copia: recipe.copy.value,
                alimentos: recipe.foods.value
            })
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function copyRecipe(recipeId) {
        try {
            const response = await axiosInstance.put('/receitas/' + recipeId + '/copia')
            return response.data

        }
        catch (error) {
            return error
        }
    }
    async function editRecipe(recipe, recipeId) {
        try {
            const response = await axiosInstance.put('/receitas/' + recipeId, {
                modoPreparo: recipe.preparement.value,
                imagem: recipe.image.value,
                titulo: recipe.title.value,
                descricao: recipe.description.value,
                privado: recipe.private.value,
                copia: recipe.copy.value,
                alimentos: recipe.foods.value
            })
            return response.data

        }
        catch (error) {
            return error
        }
    }
    async function deleteRecipe(recipeId) {
        try {
            const response = await axiosInstance.delete('/receitas/' + recipeId)
            return response.data

        }
        catch (error) {
            return error
        }
    }
    async function rateRecipe(recipeId, rating) {
        try {
            const response = await axiosInstance.put('/receitas/' + recipeId+'/avaliar', {
                nota: rating
            })
            return response.data

        }
        catch (error) {
            return error
        }
    }
    return { getFollowingRecipes, getTopRecipes, getUserRecipes, createRecipe,editRecipe, copyRecipe, deleteRecipe, getSuggestedRecipes, rateRecipe }
}
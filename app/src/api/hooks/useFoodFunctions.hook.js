import { axiosInstance } from "../user/_base/axiosInstance"

export function useFoodFunctions() {
    async function getFoodByName(size, name) {
        try {
            const response = await axiosInstance.get('/alimentos?sort=nome,asc&size=' + size+'&text='+name)
            return response.data
        }
        catch (error) {

        }
    }
    async function getRecentFoods() {
        try {
            const response = await axiosInstance.get('/alimentos/recentes')
            return response.data
        }
        catch (error) {
        }
    }
    async function createFood(food) {
        try {
            const response = await axiosInstance.post('/alimentos', {
                nome: food.name.value,
                calorias: food.calories.value,
                proteinas: food.protein.value,
                carboidratos: food.carbo.value,
                lipidios: food.fat.value
            })
            return response.data
        }
        catch (error) {
            return error
        }
    }
    return {getFoodByName, createFood, getRecentFoods}
}
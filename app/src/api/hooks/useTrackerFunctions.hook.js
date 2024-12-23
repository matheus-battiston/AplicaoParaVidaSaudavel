import { axiosInstance } from "../user/_base/axiosInstance"


export function useTrackerFunctions() {
    async function getDayMeals(day) {
        try {
            const response = await axiosInstance.get('/tracker/refeicao/listar/' + day)
            return response.data
        }
        catch (error) {
            return [...error]
        }
    }
    async function getDailyReport(day) {
        try {
            const response = await axiosInstance.get('/tracker/relatorio/' + day)
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function addFoodToMeal(day, period, food) {
        try {
            const response = await axiosInstance.post('/tracker/refeicao', {
                "alimento": food,
                "periodo": period,
                "dia": day
            })
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function addRecipeToMeal(day, period, recipeId) {
        try {
            const response = await axiosInstance.post('/tracker/refeicao/receita', {
                "idReceita": recipeId,
                "periodo": period,
                "dia": day
            })
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function removeFoodFromMeal(mealId, foodId) {
        try {
            const response = await axiosInstance.post('/tracker/refeicao/remover/   ' + mealId, {
                "idAlimento": foodId
            })
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function drinkWater(day, quantity) {
        try {
            const response = await axiosInstance.put('/tracker/agua', {
                "quantidade": quantity,
                "data": day
            })
            return response.data
        }
        catch (error) {
            return error
        }
    }

    return { getDayMeals, addFoodToMeal, removeFoodFromMeal, getDailyReport, addRecipeToMeal, drinkWater }
}
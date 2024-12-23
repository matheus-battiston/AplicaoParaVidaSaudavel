import { axiosInstance } from "../user/_base/axiosInstance"

export function useRankingFuntions() {
    async function getRanking(size) {
        try {
            const response = await axiosInstance.get('/ranking?size='+size)
            return response.data
        }
        catch (error) {
            return error
        }
    }
    return {getRanking}
}
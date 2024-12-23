import { axiosInstance } from "../_base/axiosInstance";

export async function register(userInfo) {
    try {
        const response = await axiosInstance.post('/usuarios',
            {
                "nome": userInfo.name.value,
                "email": userInfo.email.value,
                "senha": userInfo.password.value,
                "dataNascimento": userInfo.birthDate.value,

            })
        return response.data
    }
    catch (error) {
        return error
    }
}
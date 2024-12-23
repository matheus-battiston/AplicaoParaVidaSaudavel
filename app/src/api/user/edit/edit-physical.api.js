import { axiosInstance } from '../_base/axiosInstance';

export async function editPhysical(value) {
  try {
    const response = await axiosInstance.post('/usuarios/me/atividade', {
      atividadeFisica: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

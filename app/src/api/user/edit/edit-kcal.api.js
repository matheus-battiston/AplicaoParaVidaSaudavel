import { axiosInstance } from '../_base/axiosInstance';

export async function editKcal(value) {
  try {
    const response = await axiosInstance.put('/usuarios/me/calorias', {
      valor: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

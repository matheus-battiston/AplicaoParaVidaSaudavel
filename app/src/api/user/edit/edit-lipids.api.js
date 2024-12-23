import { axiosInstance } from '../_base/axiosInstance';

export async function editLipids(value) {
  try {
    const response = await axiosInstance.put('/usuarios/me/lipidios', {
      valor: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

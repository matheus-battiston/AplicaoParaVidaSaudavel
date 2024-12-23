import { axiosInstance } from '../_base/axiosInstance';

export async function editWater(value) {
  try {
    const response = await axiosInstance.put('/usuarios/me/agua', {
      valor: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

import { axiosInstance } from '../_base/axiosInstance';

export async function insertWeight(value, date) {
  try {
    const response = await axiosInstance.post('/usuarios/me/peso', {
      peso: value,
      data: date,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

import { axiosInstance } from '../_base/axiosInstance';

export async function editCarbs(value) {
  try {
    const response = await axiosInstance.put('/usuarios/me/carboidratos', {
      valor: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

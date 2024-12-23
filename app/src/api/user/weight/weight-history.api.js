import { axiosInstance } from '../_base/axiosInstance';

export async function weightHistory() {
  try {
    const response = await axiosInstance.get(
      '/usuarios/me/historico-pesos',
      {}
    );
    return response.data;
  } catch (error) {
    return error;
  }
}

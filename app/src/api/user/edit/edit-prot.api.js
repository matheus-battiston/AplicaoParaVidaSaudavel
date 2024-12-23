import { axiosInstance } from '../_base/axiosInstance';

export async function editProt(value) {
  try {
    const response = await axiosInstance.put('/usuarios/me/proteinas', {
      valor: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

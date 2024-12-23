import { axiosInstance } from '../_base/axiosInstance';

export async function setTag(idConquista) {
  try {
    const response = await axiosInstance.put(
      '/usuarios/me/titulo/' + idConquista,
      {}
    );
    return response.data;
  } catch (error) {
    return error;
  }
}

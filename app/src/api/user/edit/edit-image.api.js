import { axiosInstance } from '../_base/axiosInstance';

export async function editProfilePhoto(value) {
  try {
    const response = await axiosInstance.post('/usuarios/me/foto', {
      urlFoto: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

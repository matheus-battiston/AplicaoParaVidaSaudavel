import { axiosInstance } from '../_base/axiosInstance';

export async function editHeight(height) {
  try {
    const response = await axiosInstance.post('/usuarios/me/altura', {
      altura: height,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

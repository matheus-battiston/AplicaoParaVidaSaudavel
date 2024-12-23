import { axiosInstance } from '../_base/axiosInstance';

export async function editGoal(value) {
  try {
    const response = await axiosInstance.post('/usuarios/me/meta', {
      meta: value,
    });
    return response.data;
  } catch (error) {
    return error;
  }
}

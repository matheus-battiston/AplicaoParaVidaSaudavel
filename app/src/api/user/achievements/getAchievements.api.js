import { axiosInstance } from '../_base/axiosInstance';

export async function getAchievements() {
  const response = await axiosInstance.get('/conquistas', {}, {});
  return response.data;
}

import { axiosInstance } from '../_base/axiosInstance';

export async function requestNewPassword(value) {
  const response = await axiosInstance.post('/esqueceu-senha/requisitar', {
    email: value,
  });
  return response.data;
}

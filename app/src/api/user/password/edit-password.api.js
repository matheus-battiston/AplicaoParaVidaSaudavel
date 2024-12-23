import { axiosInstance } from '../_base/axiosInstance';

export async function requestNewPasswordWithToken(email, token, novaSenha) {
  const response = await axiosInstance.post('/esqueceu-senha/alterar', {
    token: token,
    email: email,
    senha: novaSenha,
  });
  return response.data;
}

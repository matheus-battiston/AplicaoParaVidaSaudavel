import { useState } from 'react';
import { requestNewPasswordWithToken } from '../../user/password/edit-password.api';

export function useResetPasswordWithToken() {
  const [response, setResponse] = useState(false);
  const [error, setError] = useState(false);

  async function requestPasswordWithToken({ email, token, password }) {
    
    try {
      const response = await requestNewPasswordWithToken(
        email,
        token,
        password
      );
      setResponse(response);
    } catch (error) {
      setError(true);
    }
  }
  return { response, error, requestPasswordWithToken };
}

import { useState } from 'react';
import { requestNewPassword } from '../../user/password/request-new-password.api';

export function useResetPassword() {
  const [response, setResponse] = useState(false);
  const [error, setError] = useState(false);

  async function requestPassword(value) {
    try {
      const response = await requestNewPassword(value);
      setResponse(response);
    } catch (error) {
      setError(true);
    }
  }
  return { response, error, requestPassword };
}

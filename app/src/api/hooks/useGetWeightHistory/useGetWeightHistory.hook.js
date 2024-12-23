import { useEffect, useState } from 'react';
import { weightHistory } from '../../user/weight/weight-history.api';

export function useGetWeightHistory() {
  const [wightHistoryData, setWeightHistory] = useState(null);

  useEffect(() => {
    getWeight();
  }, []);

  async function getWeight() {
    try {
      const respostaApi = await weightHistory();
      setWeightHistory(respostaApi);
    } catch (error) {
    }
  }
  return { wightHistoryData, getWeight };
}

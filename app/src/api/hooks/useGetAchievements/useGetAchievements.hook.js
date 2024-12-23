import { useEffect, useState } from 'react';
import { getAchievements } from '../../user/achievements/getAchievements.api';

export function useGetAchievements() {
  const [achievements, setAchievements] = useState(null);

  useEffect(() => {
    getAchievementsFunc();
  }, []);

  async function getAchievementsFunc() {
    try {
      const respostaApi = await getAchievements();
      setAchievements(respostaApi);
    } catch (error) {
      
    }
  }
  return { achievements, getAchievementsFunc };
}

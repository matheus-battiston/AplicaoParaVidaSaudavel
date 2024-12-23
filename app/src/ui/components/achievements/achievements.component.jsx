import { useGetAchievements } from '../../../api/hooks/useGetAchievements/useGetAchievements.hook';
import { CardAchievement } from '../cardAchievement/cardAchievement.component';
import './achievements.css';

export function Achievements({ reload, setReload }) {
  const { achievements, getAchievementsFunc } = useGetAchievements();

  return (
    <div className='achievements-list'>
      {achievements
        ? achievements.map((achievement) => (
            <CardAchievement
              achievement={achievement}
              reload={reload}
              setReload={setReload}
            />
          ))
        : null}
    </div>
  );
}

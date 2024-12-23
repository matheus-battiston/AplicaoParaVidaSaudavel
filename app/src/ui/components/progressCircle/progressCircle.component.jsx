import './progressCircle.css'
export function ProgressCircle({ consumed, goal }) {
  const percentage = Math.round(consumed * 100 / goal)
  const diff = goal - consumed
  const radius = 100;
  const circumference = 2 * Math.PI * radius;
  const strokeDasharray = `${circumference} ${circumference}`;
  const dashOffset = (percentage>=100 ? 0 : circumference - (percentage / 100) * circumference).toString()
  const strokeWidth = 10;
  const center = radius + strokeWidth / 2;

  return (
    <svg className='progress-circle'>
      <circle
        cx={center}
        cy={center}
        r={radius}
        strokeWidth={strokeWidth}
        stroke="#ddd"
        fill="none"
        strokeDasharray={strokeDasharray}
        strokeDashoffset="0"
      />
      <circle
        cx={center}
        cy={center}
        r={radius}
        strokeWidth={strokeWidth}
        stroke="#9DE2C9"
        fill="none"
        strokeDasharray={strokeDasharray}
        strokeDashoffset={dashOffset}
        transform={`rotate(-90 ${center} ${center})`}
      />
      <text x={center} y={center-10} textAnchor="middle" dominantBaseline="middle" color='#858585'>
        {diff>=0 ? 'Consumir': 'Queimar'}
      </text>
      <text x={center} y={center+15} textAnchor="middle" dominantBaseline="middle" fontWeight='bold' fontSize='24px'>
        {Math.abs(diff) || 0 }kcal
      </text>
    </svg>
  )
}
import "./weightInfo.css"

export function WeightInfo({date, weight}) {
    return (
        <div className="weight-info">
            <p className="date">{date}</p>
            <p className="weight-info-write">{weight}kg</p>
        </div>
    )
}
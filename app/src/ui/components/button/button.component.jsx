import './button.css'

export function Button({type, onClick, text}) {
    return <button className={(type ? type : 'main ')} onClick={onClick}>{text}</button>
}
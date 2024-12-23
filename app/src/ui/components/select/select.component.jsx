import "./select.css"

export function Select({options, type, mensagemDefault, submit}){

    function onChange(event){
        const {value, name} = event.target
        submit(value, name)
    }

    return (
        <select className="select-style" name={type} onChange={onChange}>
            <option defaultValue hidden disabled>{mensagemDefault}</option>
            {options.map(option => {
                return <option value={option.valor} key={option.item}>{option.item}</option>
            })}
        </select>
    )
}
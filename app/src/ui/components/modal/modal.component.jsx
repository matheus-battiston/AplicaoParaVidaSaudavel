import './modal.css'
import arrow from '../../../images/returnArrow.svg'
export function Modal(props) {

    return (
        <div className='modal-overlay'>
            <div className={`scrollbar modal-children ${props.small? 'small' : ''}`}>
                <img className='modal-return-arrow' src={arrow} alt='arrow' onClick={props.onClose} />
                {props.children}
            </div>
        </div>
    )
}
import { Button } from '../button/button.component';
import './loadMoreButton.css'

export function LoadMoreButton({ pageSize, setPageSize, totalElements }) {
    if (pageSize < totalElements) {
        return (
            <Button onClick={() => setPageSize(size => size+6)} text='Ver mais' type='second' />
        )
    }
    return null

}
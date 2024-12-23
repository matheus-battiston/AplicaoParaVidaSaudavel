import './recipeRatingStars.css'

export function RecipeRatingStars({ rating }) {

    return (
        <div className='recipe-rating-stars'>
            <span className={(1 <= Math.round(Number.parseFloat(rating)) ? 'filled-star ' : 'unfilled-star ')}>⭐</span>
            <span className={(2 <= Math.round(Number.parseFloat(rating)) ? 'filled-star ' : 'unfilled-star ')}>⭐</span>
            <span className={(3 <= Math.round(Number.parseFloat(rating)) ? 'filled-star ' : 'unfilled-star ')}>⭐</span>
            <span className={(4 <= Math.round(Number.parseFloat(rating)) ? 'filled-star ' : 'unfilled-star ')}>⭐</span>
            <span className={(5 <= Math.round(Number.parseFloat(rating)) ? 'filled-star ' : 'unfilled-star ')}>⭐</span>
          
        </div>
    )
}
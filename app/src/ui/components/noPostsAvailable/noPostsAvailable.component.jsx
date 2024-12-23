import "./noPostsAvailable.css"

export function NoPostsAvailable(){
    
    return (
            <li className='no-posts-available-outside'>
                <div className='no-posts-available-inside'>
                    <h3>Nenhum post disponivel para você.</h3>
                    <span>Procure novos amigos para seguir e poder ver o que eles estão postando aqui!</span>
                </div>
            </li>
    )
}
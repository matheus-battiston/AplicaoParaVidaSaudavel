import './chatMessage.css'

export function ChatMessage({ type, children }) {
    return (
        <div className={`message-text ${type}`}>
            <p>{children}</p>
        </div>
    )
}
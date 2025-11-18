const Header = ({onSidebartoggle}) => {
    return (
        <header>
            <button onClick={onSidebartoggle}>Menu</button>
            <h1>Company ABC</h1>
        </header>
    )
}

export default Header
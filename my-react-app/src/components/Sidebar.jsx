const Sidebar = () => {
    const menuItems = [
        {label: "Home" , link: "/"},
        {label: "Products", link: "/products"},
        {label: "Profile", link: "/profile"},
        {label: "Settings", link: "/settings"}
    ]

    return<div>
        <ul>
            {
                menuItems.map((item, index) => (<li key={index}>{item.label}</li>))
            }
        </ul>
    </div>
}

export default Sidebar
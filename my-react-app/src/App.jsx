import "./styles.css";
import { useState } from "react";

import Header from "./components/Header.jsx";
import Footer from "./components/Footer.jsx";
import Sidebar from "./components/Sidebar.jsx";
import ProfileList from "./components/ProfileList.jsx";
import StepCounter from "./components/StepCounter.jsx";
import ProductInfo from "./components/ProductInfo.jsx";

const App = () => {
  const [sidebarToggle, setSidebarToggle] = useState(true);

  function toggleSidebar() {
    setSidebarToggle(!sidebarToggle)
  }



  return (
    <>

      <Sidebar />
      <Header onSidebartoggle = {toggleSidebar}/>

      <main>
        <p>{sidebarToggle && "sidebar open"}</p>
      </main>

      <div className="container">
        <h1>React App</h1>

        <div className="dashboard-row">
          <ProfileList />
          <StepCounter />
        </div>

        <div className="products-row">
          <ProductInfo
            name="ASUS V16"
            price={45999}
            details="ASUS V16 is a high-performance laptop suitable for gaming and productivity."
          />
          <ProductInfo
            name="Lenovo IdeaPad 3"
            price={32999}
            details="Lenovo IdeaPad 3 is a reliable laptop for everyday tasks and online learning."
          />
        </div>

        <Footer />
      </div>
    </>
  );
}

export default App;

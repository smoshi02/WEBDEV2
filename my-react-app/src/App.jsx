import "./styles.css";
import ProfileList from "./components/ProfileList.jsx"; // relative path
import StepCounter from "./components/StepCounter.jsx"; // relative path
import ProductInfo from "./components/ProductInfo.jsx"; // relative path


export default function App() {
  return (
    <div className="container">
      <h1>React App</h1>

      {/* Dashboard top row */}
      <div className="dashboard-row">
        <ProfileList />
        <StepCounter />
      </div>

      {/* Products below */}
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
    </div>
  );
}

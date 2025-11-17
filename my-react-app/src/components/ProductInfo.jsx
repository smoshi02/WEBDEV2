import { useState } from "react";

export default function ProductInfo({ name, price, details }) {
  const [showDetails, setShowDetails] = useState(false);

  return (
    <div className="product-card">
      <h2>{name}</h2>
      <p className="product-price">₱{price}</p>

      <button
        className="details-btn"
        onClick={() => setShowDetails(!showDetails)}
      >
        {showDetails ? "Hide Details" : "Show Details"}
      </button>

      {showDetails && <p className="product-details">{details}</p>}
    </div>
  );
}

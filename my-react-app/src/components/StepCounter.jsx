import { useState } from "react";

export default function StepCounter() {
  const [count, setCount] = useState(0);
  const [step, setStep] = useState(1);

  return (
    <div className="step-counter-container">
      <h2>Step Counter</h2>

      <div className="step-counter-form">
        <input
          type="number"
          placeholder="Enter step value"
          value={step}
          onChange={(e) => setStep(Number(e.target.value))}
        />

        <button onClick={() => setCount(count + step)}>Add</button>
      </div>

      <p style={{ marginTop: "15px", fontSize: "1.1rem", color: "#d6f5cc" }}>
        Count: {count}
      </p>
    </div>
  );
}

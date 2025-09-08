import React, { useState } from "react";
import "./HouseEntryForm.css";

function HouseEntryForm() {
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [day, setDay] = useState("");
  const [response, setResponse] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setResponse(null);
    try {
      const res = await fetch("/api/generate-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          year: parseInt(year, 10),
          month: parseInt(month, 10),
          day: parseInt(day, 10)
        })
      });
      const data = await res.json();
      setResponse(data);
    } catch (err) {
      setResponse({ password: -1, status: "Error connecting to server" });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <form className="form" onSubmit={handleSubmit}>
        <input
          type="number"
          placeholder="Year (YYYY)"
          value={year}
          onChange={(e) => setYear(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Month (MM)"
          value={month}
          onChange={(e) => setMonth(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Day (DD)"
          value={day}
          onChange={(e) => setDay(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? "Generating..." : "Generate Password"}
        </button>
      </form>

      {response && (
        <div className="response-box">
          <p><strong>Password:</strong> {response.password}</p>
          <p><strong>Status:</strong> {response.status}</p>
        </div>
      )}
    </div>
  );
}

export default HouseEntryForm;

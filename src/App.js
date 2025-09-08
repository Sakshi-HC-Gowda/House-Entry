import React from "react";
import HouseEntryForm from "./components/HouseEntryForm";
import AdminMembers from "./components/AdminMembers";

function App() {
  return (
    <div className="container">
      <h1>🏠 House Entry Password Generator</h1>
      <p className="small">Enter your DOB to generate the door password.</p>
      <HouseEntryForm />
      <hr />
      <AdminMembers />
    </div>
  );
}

export default App;

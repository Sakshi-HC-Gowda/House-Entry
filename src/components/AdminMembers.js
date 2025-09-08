import React, { useEffect, useState } from "react";

function AdminMembers() {
  const [members, setMembers] = useState([]);
  const [name, setName] = useState("");
  const [year, setYear] = useState(2000);
  const [month, setMonth] = useState(1);
  const [day, setDay] = useState(1);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const load = async () => {
    try {
      const res = await fetch("/api/members");
      const data = await res.json();
      setMembers(data);
    } catch (e) {
      setMessage("Failed to load members");
    }
  };

  useEffect(() => {
    load();
  }, []);

  const addMember = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    try {
      const res = await fetch("/api/members", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, year: Number(year), month: Number(month), day: Number(day) })
      });
      const text = await res.text();
      setMessage(text);
      await load();
    } catch (e) {
      setMessage("Failed to add member");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Admin: Members</h2>
      <form onSubmit={addMember} style={{ display: "flex", gap: 8, alignItems: "center", flexWrap: "wrap" }}>
        <input type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} />
        <input type="number" value={year} onChange={(e) => setYear(e.target.value)} min={1900} max={3000} />
        <input type="number" value={month} onChange={(e) => setMonth(e.target.value)} min={1} max={12} />
        <input type="number" value={day} onChange={(e) => setDay(e.target.value)} min={1} max={31} />
        <button type="submit" disabled={loading}>{loading ? "Adding..." : "Add Member"}</button>
        {message && <span style={{ marginLeft: 8 }}>{message}</span>}
      </form>

      <div style={{ marginTop: 16 }}>
        <h3>Registered DOBs ({members.length})</h3>
        <ul>
          {members.map((m, idx) => (
            <li key={`${m.year}-${m.month}-${m.day}-${idx}`} style={{ display: "flex", gap: 8, alignItems: "center" }}>
              <span style={{ minWidth: 180 }}>
                {m.name ? `${m.name} — ` : ""}
                {m.year}-{String(m.month).padStart(2, "0")}-{String(m.day).padStart(2, "0")}
              </span>
              <button onClick={async () => {
                try {
                  await fetch("/api/members", {
                    method: "DELETE",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ year: m.year, month: m.month, day: m.day })
                  });
                  await load();
                } catch (e) {
                  setMessage("Failed to delete");
                }
              }}>Delete</button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default AdminMembers;



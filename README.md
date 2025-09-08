# House Entry – Backend API + React Frontend

This starter contains:
- **backend/**: Spring Boot REST API exposing `/api/generate-password`.
- **frontend/**: React app with a form that POSTs DOB to the backend.
- **console/**: Your original Java console app (unchanged), if you want to run it standalone.

## Quick Start

### 1) Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run      # Mac/Linux
# or
mvnw.cmd spring-boot:run    # Windows (PowerShell)
```
API: `POST http://localhost:8080/api/generate-password`

Sample JSON body:
```json
{ "year": 2000, "month": 5, "day": 12 }
```

### 2) Frontend (React)
In another terminal:
```bash
cd frontend
npm install
npm start
```
Open: `http://localhost:3000`

By default, the frontend calls `http://localhost:8080/api/generate-password`. If your backend runs elsewhere, change the URL in `src/components/HouseEntryForm.js`.

### 3) Console App (Optional)
If you want to run your original console program:
```
cd console
javac HouseEntrySimple.java
java HouseEntrySimple
```

## Notes
- **CORS**: Enabled globally for all origins and common methods. Suitable for dev and for mobile/React clients.
- **Health**: `GET /health` and Actuator `GET /actuator/health`.
- **Docs**: Swagger UI at `/swagger-ui.html`, OpenAPI JSON at `/v3/api-docs`.
- **Response shape**: `{ password: <number>, status: "Door Opened" | "Invalid Date" | "Invalid Input" }` with HTTP 200 for success, 400 for invalid input.

## Members and Access Control
Only registered members can open the door. Register each member's DOB once.

- Register member:
  - `POST http://localhost:8080/api/members`
  - Body: `{ "year": 1997, "month": 8, "day": 3 }`
  - Response: `Registered` or `Already Registered`

- List members:
  - `GET http://localhost:8080/api/members`

- Open door (generate password):
  - `POST http://localhost:8080/api/generate-password`
  - Only works if DOB is registered; otherwise returns `{ password: -1, status: "Access Denied" }`.

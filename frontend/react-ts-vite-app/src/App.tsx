import { BrowserRouter, Routes, Route } from "react-router-dom";
import TutorProfilePage from "./pages/TutorProfilePage";
import ConfirmationPage from "./pages/ConfirmationPage";
import DashboardPage from "./pages/DashboardPage";
import ParentProfilePage from "./pages/ParentProfilePage";
import TutorSearchScreen from "./pages/TutorSearchScreen";
import RegisterParentPage from "./pages/RegisterParentPage";
import AdminDashboardPage from "./pages/AdminDashboardPage";
import TutorDashboardPage from "./pages/TutorDashboardPage";
// import StudentsPage from "./pages/StudentsPage";
import RegisterChildPage from "./pages/RegisterChildPage";
// import SettingsPage from "./pages/SettingsPage";
// import GetHelpPage from "./pages/GetHelpPage";
// import SearchPage from "./pages/SearchPage";
import { PersonaProvider } from "./context/PersonaProvider";
import PersonaPage from "./pages/PersonaPage";
import ChildProgressDashboard from "@/components/ChildProgressDashboard";
import LandingPage from "./pages/LandingPage";

function App() {
  return (
    <PersonaProvider>
      <BrowserRouter>
        <Routes>
          <Route index element={<LandingPage />} />
          <Route path="/persona" element={<PersonaPage />} />
          <Route path="/parents/:parentId" element={<ParentProfilePage />} />
          <Route path=".parents/register" element={<RegisterParentPage />} />
          <Route path="/" element={<DashboardPage />}>
            <Route path="/tutors" element={<TutorSearchScreen />} />
            <Route path="/tutors/:tutorId" element={<TutorProfilePage />} />
            <Route
              path="tutors/:tutorId/dashboard"
              element={<TutorDashboardPage />}
            />
            <Route
              path="/parents/:parentId/children/:childId/progress"
              element={<ChildProgressDashboard />}
            />
            <Route path="confirmation" element={<ConfirmationPage />} />
            <Route path="admin" element={<AdminDashboardPage />} />
            {/* <Route path="students" element={<StudentsPage />} /> */}
            <Route path="children/register" element={<RegisterChildPage />} />
            {/* <Route path="settings" element={<SettingsPage />} />
            <Route path="help" element={<GetHelpPage />} />
            <Route path="search" element={<SearchPage />} /> */}
          </Route>
        </Routes>
      </BrowserRouter>
    </PersonaProvider>
  );
}

export default App;

/*
import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'


function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}
  */

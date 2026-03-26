import { BrowserRouter, Routes, Route } from "react-router-dom";
import TutorProfilePage from "./pages/TutorProfilePage";
import ConfirmationPage from "./pages/ConfirmationPage";
import DashboardPage from "./pages/DashboardPage";
import ParentProfilePage from "./pages/ParentProfilePage";
import TutorSearchScreen from "./pages/TutorSearchScreen";
import RegisterParentPage from "./pages/RegisterParentPage";
import AdminDashboardPage from "./pages/AdminDashboardPage";
import TutorDashboardPage from "./pages/TutorDashboardPage";
import RegisterChildPage from "./pages/RegisterChildPage";
import { PersonaProvider } from "./context/PersonaProvider";
import PersonaPage from "./pages/PersonaPage";
import ChildProgressDashboard from "@/components/ChildProgressDashboard";
import LandingPage from "./pages/LandingPage";
import LiveSessionsPage from "./pages/LiveSessionsPage";
import VideoClassroomPage from "./pages/VideoClassroomPage";

function App() {
  return (
    <PersonaProvider>
      <BrowserRouter>
        <Routes>
          <Route index element={<LandingPage />} />
          <Route path="/persona" element={<PersonaPage />} />
          <Route path="/parents" element={<ParentProfilePage />} />
          <Route path="/parents/register" element={<RegisterParentPage />} />
          <Route path="/" element={<DashboardPage />}>
            <Route path="/tutors" element={<TutorSearchScreen />} />
            <Route path="/tutors/:tutorId" element={<TutorProfilePage />} />
            <Route
              path="tutors/dashboard"
              element={<TutorDashboardPage />}
            />
            <Route
              path="/parents/:parentId/progress"
              element={<ChildProgressDashboard />}
            />
            <Route path="confirmation" element={<ConfirmationPage />} />
            <Route path="admin" element={<AdminDashboardPage />} />
            <Route path="children/register" element={<RegisterChildPage />} />
            <Route path="live-sessions" element={<LiveSessionsPage />} />
            <Route
              path="live-sessions/:sessionId"
              element={<VideoClassroomPage />}
            />
          </Route>
        </Routes>
      </BrowserRouter>
    </PersonaProvider>
  );
}

export default App;
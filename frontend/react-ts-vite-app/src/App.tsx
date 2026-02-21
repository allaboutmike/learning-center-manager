import { BrowserRouter, Routes, Route } from "react-router-dom";
import TutorSearchScreen from "./pages/TutorSearchScreen";
import TutorProfilePage from "./pages/TutorProfilePage";
import ConfirmationPage from "./pages/ConfirmationPage";
import SessionReviewModal from "./pages/SessionReviewPage";
import DashboardPage from "./pages/DashboardPage";


function App() {
  return (
    <BrowserRouter>
      <Routes>
       <Route path="/" element={<DashboardPage />}>
          <Route index element={<TutorSearchScreen />} />
          <Route path="tutors/:tutorId" element={<TutorProfilePage />} />
          <Route path="/sessions/review" element={<SessionReviewModal />} />
          <Route path="confirmation" element={<ConfirmationPage />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;



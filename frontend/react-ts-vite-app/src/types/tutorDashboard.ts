import type { Session } from "./session";
import type { Subject } from "./subject";

export type TutorDashboard = {
  tutorName: string;
  totalStudentsTutored: number;
  totalSessionsCompleted: number;
  averageRating: number;
  nextUpcomingSession: Session | null;
  subjectName: Subject[];
};

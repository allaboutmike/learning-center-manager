import type { Session } from "./session";

export type ParentView = {
  sessionId: number;
  tutorId: string;
  subject: string;
  student: Child;
  sessionNotes: string;
};

export type Parent = {
  name: string;
  parentId: number;
  children: Child[];
};

export type Child = {
  id: string;
  childName: string;
  gradeLevel: number;
};

export type TabType = "upcoming" | "past";

export type SessionData = {
  [childId: string]: {
    upcoming: Session[];
    past: Session[];
  };
};

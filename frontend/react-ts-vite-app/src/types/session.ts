import type { Subject } from "./subject";

export type Session = {
  sessionId: number;
  tutorId: number;
  subjectId: number;
  sessionNotes?: string;
  date: string;
  childId: number;
  childName: string;
  time: string;
  tutorName: string;
  subjectName: Subject;
  attended: boolean;
};

export type Session = {
  sessionId: string;
  tutorId: string;
  subject: string;
  student: Child;
  sessionNotes: string;
};

export type Child = {
  id: string;
  childName: string;
};

export type TabType = "upcoming" | "past";

export type SessionData = {
  [childId: string]: {
    upcoming: Session[];
    past: Session[];
  };
};
    sessionId: number;
    tutorId: number;
    subjectId: number;
    sessionNotes?: string;
    date: string;
    childId: number;
}

import type { Session } from "./session";

export type Parent = {
  parentId: string;
  children: Child[];
  childSessions: Session[];
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

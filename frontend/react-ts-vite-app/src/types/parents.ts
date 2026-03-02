import type { Session } from "./session";

export type Parent = {
  parentId: number;
  children: ChildResponse[];
  childSessions: Session[];
  credits: number;
};

export type ChildResponse = {
  childId: string;
  firstName: string;
  gradeLevel: number;
};

export type TabType = "upcoming" | "past";

export type SessionData = {
  [childId: string]: {
    upcoming: Session[];
    past: Session[];
  };
};

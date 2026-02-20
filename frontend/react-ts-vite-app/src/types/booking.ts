import type { Child } from "./session";

export type Booking = {
  subjectId: number;
  sessionNotes: string;
  childId: Child;
  tutorTimeSlotId: number;
};

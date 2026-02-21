import type { Subject } from "./subject";

import type { TutorTimeslot } from "./tutorTimeslot.ts";


export type Tutor = {
  tutorId: number;
  name: string;
  profilePictureUrl: string;
  tutorSummary: string;
  avgRating: number;
  reviewCount: number;
  minGradeLevel: number;
  maxGradeLevel: number;
  subjects: Subject[];
  availableTimeSlots: TutorTimeslot[];
};

export type { TutorTimeslot };
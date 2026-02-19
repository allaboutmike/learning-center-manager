import type { TutorTimeslot } from "./tutorTimeslot.ts";
import type { Reviews } from "./reviews.ts";

import type { Subject } from "./subject";

export type Tutor = {
  tutorId: number;
  name: string;
  profilePictureUrl: string;
  tutorSummary: string;
  avgRating: number;
  reviewCount: number;
  reviews: Reviews[];
  minGradeLevel: number;
  maxGradeLevel: number;
  availableTimeSlots: TutorTimeslot[];
  subjects: Subject[];
};

export type { TutorTimeslot };
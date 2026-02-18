import type { TutorTimeslot } from "./tutorTimeslot.ts";
import type { Reviews } from "./reviews.ts";

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
};

export type { TutorTimeslot };
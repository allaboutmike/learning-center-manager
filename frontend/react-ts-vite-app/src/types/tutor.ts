import type { TutorTimeslot } from "./tutorTimeslot";



export type Tutor = {
  tutorId: number;
  name: string;
  profilePictureUrl: string;
  tutorSummary: string;
  avgRating: number;
  reviewCount: number;
  reviews: string[];
  minGradeLevel: number;
  maxGradeLevel: number;
  AvailableTimeSlots: TutorTimeslot[];
};

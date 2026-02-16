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

  export const tutor: Tutor = {
    
    tutorId: 1,
    name: "John Doe",
    profilePictureUrl: "public/sailor_moon.png",
    tutorSummary:
      "Experienced math tutor with a passion for helping students succeed.",
    avgRating: 4.5,
    reviewCount: 1,
    reviews: ["Great tutor!", "Helped me improve my grades."],
    minGradeLevel: 6,
    maxGradeLevel: 12,
    AvailableTimeSlots: [
      { tutorTimeslotId: 1, tutorId: 1, timeslotId: "2026-02-22 15:30:00" },
      { tutorTimeslotId: 2, tutorId: 1, timeslotId: "2026-02-22 16:30:00" },
      { tutorTimeslotId: 3, tutorId: 1, timeslotId: "2026-02-22 17:30:00" },
    ],
  };

    export const tutor2: Tutor = {
    
    tutorId: 2,
    name: "Jane Smith",
    profilePictureUrl: "public/sailor_moon.png",
    tutorSummary:
      "Experienced math tutor with a passion for helping students succeed.",
    avgRating: 4.9,
    reviewCount: 1,
    reviews: ["Great tutor!", "Helped me improve my grades."],
    minGradeLevel: 5,
    maxGradeLevel: 12,
    AvailableTimeSlots: [
      { tutorTimeslotId: 1, tutorId: 1, timeslotId: "2026-02-22 15:30:00" },
      { tutorTimeslotId: 2, tutorId: 1, timeslotId: "2026-02-22 16:30:00" },
      { tutorTimeslotId: 3, tutorId: 1, timeslotId: "2026-02-22 17:30:00" },
    ],
  };

    export const tutor3: Tutor = {
    
    tutorId: 3,
    name: "Harry Potter",
    profilePictureUrl: "public/sailor_moon.png",
    tutorSummary:
      "Experienced math tutor with a passion for helping students succeed.",
    avgRating: 5,
    reviewCount: 0,
    reviews: [],
    minGradeLevel: 9,
    maxGradeLevel: 12,
    AvailableTimeSlots: [
      { tutorTimeslotId: 1, tutorId: 1, timeslotId: "2026-02-22 15:30:00" },
      { tutorTimeslotId: 2, tutorId: 1, timeslotId: "2026-02-22 16:30:00" },
      { tutorTimeslotId: 3, tutorId: 1, timeslotId: "2026-02-22 17:30:00" },
    ],
  };

  export const TutorList: Tutor[] = [tutor, tutor2, tutor3];

import { Link, useParams } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { type Tutor } from "../types/tutor";
import { type TutorTimeslot } from "../types/tutor";
import { type Reviews } from "../types/reviews";
import { useState } from "react";
import { CardProfile } from "@/components/ui/cardProfile";

export default function TutorProfilePage() {
  

  const { tutorId } = useParams();
  const tutor = useLearningCenterAPI<Tutor>(tutorId ? `/api/tutors/${tutorId}` : "");
  const availability = useLearningCenterAPI<TutorTimeslot[]>(tutorId ? `/api/tutors/${tutorId}/availability` : "");
  const reviews = useLearningCenterAPI<Reviews[]>(tutorId ? `/api/tutors/${tutorId}/reviews` : "");

  const [selectedTimeSlot, setSelectedTimeSlot] = useState(-1);

  if (!tutorId) {
    return <p> Invalid tutor id. Please go back and search for tutors here: <Link to="/"> Search all Tutors </Link></p>;
  }
  if (!tutor) return <p>Loading...</p>;

  return (
    <>
     <div className="flex gap-8 p-6 items-start">
    <div className="w-1/3">
      <CardProfile
        name={tutor.name}
        profilePictureUrl={tutor.profilePictureUrl}
        minGradeLevel={tutor.minGradeLevel}
        maxGradeLevel={tutor.maxGradeLevel}
        tutorSummary={tutor.tutorSummary}
      />
    </div>
    </div>
    <div style={{ textAlign: "center", marginTop: 50 }}>
      <img
        src={tutor.profilePictureUrl}
        alt={tutor.name}
        style={{ width: 120, borderRadius: "50%" }}
      />
      <h1>{tutor.name}</h1>
      <p>
        Grades I teach: {tutor.minGradeLevel} - {tutor.maxGradeLevel}
      </p>
       {/* {tutor.tutorSummary} */}
        <p>Summary for the tutor (hard coded) </p> 
      <p>Rating:{tutor.avgRating}</p>
      <h3>Reviews:</h3>

{reviews && reviews.length > 0 ? (
  <ul>
    {reviews.map((review) => (
      <li key={review.reviewId}>
        <p>{review.comment}</p>
        <p>Rating: {review.rating} ⭐</p>
      </li>
    ))}
  </ul>
) : (
  <p>No reviews available</p>
)}

      <ul>
        <h2>Availability:</h2>
        {!availability && <p>There are no available time slots.</p>}
        {availability && availability.map((tutorTimeslots, index) => (
          <li
            className={
              selectedTimeSlot === index
                ? "bg-blue-500 text-white"
                : ""
            }
            key={tutorTimeslots.tutorTimeslotId}
            onClick={() => setSelectedTimeSlot(index)}
          >
            {tutorTimeslots.start} - {tutorTimeslots.end}
          </li>
          
        ))}
      </ul>
    </div>
    {/* <div className="flex min-h-svh flex-col items-center justify-center">
      <Button variant="blue">Click me</Button>
    </div> */}
    </>
  );
}

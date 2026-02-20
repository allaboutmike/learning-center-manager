import { Link, useParams } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { type Tutor } from "../types/tutor";
import { type TutorTimeslot } from "../types/tutor";
import { type Reviews } from "../types/reviews";
import { useState } from "react";

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
      <p>{tutor.tutorSummary}</p>
      <p>Rating:{tutor.avgRating}</p>
      <ul>
      <h3>Reviews: </h3>
      {tutor.reviewCount !== 0 ? tutor.reviewCount : "No reviews available"}
{/* 
        {reviews && reviews.map((review) => (
          <li key={review.reviewId}>
            <p>{review.comment}</p>
            <p>Rating: {review.rating} ⭐</p>
          </li>
        ))} */}
</ul>

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
  );
}

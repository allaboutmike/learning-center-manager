import { Link, useParams } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { type Tutor } from "../types/tutor";
import { type TutorTimeslot } from "../types/tutor";
import { useState } from "react";

export default function TutorProfilePage() {
  const { tutorId } = useParams();

  if (!tutorId) {
      return <p> Invalid tutor id. Please go back and search for tutors here: <Link to="/"> Search all Tutors </Link></p>;
  }
  const tutor = useLearningCenterAPI<Tutor>(`/api/tutors/${tutorId}`);
  const availability = useLearningCenterAPI<TutorTimeslot[]>(`/api/tutors/${tutorId}/availability`);

  if (!tutor) return <p>Loading...</p>;

  const [selectedTimeSlot, setSelectedTimeSlot] = useState(-1);

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
      <p>Rating: {tutor.avgRating}</p>
      <p>ReviewCount: {tutor.reviewCount}</p>
      
      <h3>Reviews:</h3>
      {tutor.reviewCount !== 0 ? tutor.reviewCount : "No reviews available"}
      <ul>
        <h2>Availability:</h2>
        {availability && availability.map((timeslot, index) => (
          <li
            className={
              selectedTimeSlot === index
                ? "bg-blue-500 text-white"
                : ""
            }
            key={timeslot.tutorTimeslotId}
            onClick={() => setSelectedTimeSlot(index)}
          >
            {timeslot.timeslotId}
          </li>
        ))}
      </ul>
    </div>
  );
}
import { Link, useParams } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { type Tutor } from "../types/tutor";
import { type TutorTimeslot } from "../types/tutor";
import { type Reviews } from "../types/reviews";
import { useState } from "react";
import { CardProfile } from "@/components/ui/cardProfile";
import { useNavigate } from "react-router-dom"; 
import { Button } from "@/components/ui/button";
import { type Subject } from "@/types/subject";
import { format, parseISO } from "date-fns";
import { Calendar } from "@/components/ui/calendar";

export default function TutorProfilePage() {
  
  const navigate = useNavigate();
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
<div className="grid grid-cols-[40%_60%] h-screen bg-amber-50">

  {/* LEFT COLUMN */}
  <div className="flex flex-col gap-3 p-8">

    <div className="grid grid-cols-2 gap-3">
      <div className="bg-amber-600 rounded-2xl p-4 flex items-center justify-center">
      <img
          src={tutor.profilePictureUrl}
          alt={tutor.name}
          style={{
          width: 250,
          height: 250,
          borderRadius: "10%"
          }}
        />
      </div>
      <div className="bg-amber-600 rounded-2xl p-4">
        1.1
      </div>
      <div className="bg-amber-600 rounded-2xl col-span-2 p-2">
        <h6 className="text-2xl font-bold pl-5">Name:</h6> <p className="text-2xl"> {tutor.name}</p>
      </div>
    </div>

    <div className="bg-amber-950 rounded-2xl p-4">03</div>

    <div className="bg-amber-800 rounded-2xl p-4 flex-1">
      04
    </div>

  </div>

  {/* RIGHT COLUMN */}
  <div className="bg-amber-300 rounded-2xl m-3">
    02
  </div>

</div>


        <CardProfile
        name={tutor.name}
        profilePictureUrl={tutor.profilePictureUrl}
        minGradeLevel={tutor.minGradeLevel}
        maxGradeLevel={tutor.maxGradeLevel}
        tutorSummary={tutor.tutorSummary}
        avgRating={tutor.avgRating}
        subject={tutor.subjects.map((subject: Subject) => subject.name)}
      />
    



    

   <h3>Reviews:</h3>

{tutor.avgRating ? (
  <p>Average Rating: {tutor.avgRating} ⭐</p>
) : (
  <p>No ratings available</p>
)}

{tutor.reviewCount ? (
  <p>Total Reviews: {tutor.reviewCount}</p>
) : (
  <p>0</p>
)}

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

        <Calendar />
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
            {format(parseISO(tutorTimeslots.start), "MMM d, yyyy h:mm a")} - {format(parseISO(tutorTimeslots.end), "h:mm a")}
          </li>
          
        ))}
      </ul>
      <Button disabled={selectedTimeSlot === -1} variant="secondary" onClick={() => navigate("/sessions/review", 
        { state: { tutorId: {tutorId}, subjectId: 1, tutorTimeslotId: 2, childId: 1} })}>Book Session</Button>
    </>
  );
}

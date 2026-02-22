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
    <div className="grid grid-cols-2 grid-rows-3 gap-7 pl-10 pr-15 pt-8 h-screen">
      <div className=" bg-amber-600 rounded-2xl"> <div className="flex gap-8 p-6 items-start">
    
    </div></div>
      <div className="bg-amber-300 row-span-6 rounded-2xl"> 02</div>
      <div className="bg-amber-950 rounded-2xl"> 03 </div>
      <div className="bg-amber-800 rounded-2xl"> 04</div>
      </div>


  <div className="w-1/3">
        <CardProfile
        name={tutor.name}
        profilePictureUrl={tutor.profilePictureUrl}
        minGradeLevel={tutor.minGradeLevel}
        maxGradeLevel={tutor.maxGradeLevel}
        tutorSummary={tutor.tutorSummary}
        avgRating={tutor.avgRating}
        subject={tutor.subjects.map((subject: Subject) => subject.name)}
      />
      </div>



    

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

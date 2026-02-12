import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

export default function TutorProfilePage() {
  const { tutorId } = useParams();
  const [tutor, setTutor] = useState<any>(null);

  useEffect(() => {
    fetch(`/api/tutors/${tutorId}`)
      .then((res) => res.json())
      .then((data) => {
        setTutor(data);
      });
  }, [tutorId]);

  if (!tutor) return <p>Loading...</p>;

  return (
    <div style={{ textAlign: "center", marginTop: 50 }}>
      <h1>{tutor.name}</h1>
      <img
        src={tutor.profilePictureUrl}
        alt={tutor.name}
        style={{ width: 120, borderRadius: "50%" }}
      />
      <p>Rating: {tutor.avgRating}</p>
      <p>Reviews: {tutor.reviewCount}</p>
      <p>
        Grades: {tutor.minGradeLevel} - {tutor.maxGradeLevel}
      </p>
    </div>
  );
}

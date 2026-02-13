import { useParams } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import type { Tutor } from "../types/tutor";

export default function TutorProfilePage() {
    const { tutorId } = useParams();
    if (!tutorId) {
        return <p> Invalid tutor id</p>;
    }
    const tutor = useLearningCenterAPI<Tutor>(`/api/tutors/${tutorId}`);



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

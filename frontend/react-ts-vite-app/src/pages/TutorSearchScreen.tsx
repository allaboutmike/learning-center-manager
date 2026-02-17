import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { type Tutor } from "../types/tutor";


export default function TutorSearchScreen() {
    const navigate = useNavigate();
    const [grade, setGrade] = useState("");
    const url = grade === "" ? "/api/tutors" : `/api/tutors?gradeLevel=${grade}`;
    const tutors = useLearningCenterAPI<Tutor[]>(url);


    function handleGradeChange(e: React.ChangeEvent<HTMLSelectElement>) {
        setGrade(e.target.value)
    }


    return (
        <div
            style={{
                textAlign: "center",
                marginTop: 40,
                backgroundColor: "#95C286",
                minHeight: "100vh",
                paddingTop: 40,
                fontFamily: "Poppins, sans-serif",
            }}
        >
            <h1 style={{ color: "#243B5A", fontWeight: 700 }}>
                Available Tutors
            </h1>

            <div style={{ marginBottom: 20 }}>
                <label style={{ marginRight: 8 }}>Select Grade:</label>

                <select
                    value={grade}
                    onChange={handleGradeChange}

                >
                    <option value="">All Grades</option>
                    <option value="1">Grade 1</option>
                    <option value="2">Grade 2</option>
                    <option value="3">Grade 3</option>
                    <option value="4">Grade 4</option>
                    <option value="5">Grade 5</option>
                    <option value="6">Grade 6</option>
                    <option value="7">Grade 7</option>
                    <option value="8">Grade 8</option>
                    <option value="9">Grade 9</option>
                    <option value="10">Grade 10</option>
                    <option value="11">Grade 11</option>
                    <option value="12">Grade 12</option>
                </select>

            </div>

            {!tutors&& <p>Loading...</p>}

            <ul style={{ width: 500, margin: "20px auto", padding: 0, listStyle: "none" }}>
                {tutors && tutors.map((tutor) => (
                    <li
                        key={tutor.tutorId}
                        onClick={() => {
                            navigate(`/tutors/${tutor.tutorId}`);
                        }}

                        style={{
                            border: "1px solid #e5e7eb",
                            borderRadius: 16,
                            padding: 20,
                            marginBottom: 16,
                            cursor: "pointer",
                            backgroundColor: "#ffffff",
                            boxShadow: "0 4px 12px rgba(0,0,0,0.05)",
                        }}

                    ><img
                            src={tutor.profilePictureUrl}
                            alt={tutor.name}
                            style={{
                                width: 70,
                                height: 70,
                                borderRadius: "50%",
                                objectFit: "cover",
                                marginBottom: 10,
                            }}
                        />

                        <h3>{tutor.name}</h3>
                        <p>{tutor.avgRating} ⭐ </p>
                        <p>Review Count: {tutor.reviewCount}</p>
                        <p>
                            Grades: {tutor.minGradeLevel} - {tutor.maxGradeLevel}
                        </p>
                    </li>
                ))}

            </ul>
        </div>
    );
}

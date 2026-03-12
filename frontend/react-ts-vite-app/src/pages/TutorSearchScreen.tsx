import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import type { Tutor } from "../types/tutor";

export default function TutorSearchScreen() {
  const navigate = useNavigate();
  const [grade, setGrade] = useState("");

  const url = grade === "" ? "/api/tutors" : `/api/tutors?gradeLevel=${grade}`;
  const tutors = useLearningCenterAPI<Tutor[]>(url);

  function handleGradeChange(e: React.ChangeEvent<HTMLSelectElement>) {
    setGrade(e.target.value);
  }

  return (
    <div className="min-h-screen bg-slate-50 p-8">

      {/* PAGE HEADER */}
      <div className="max-w-6xl mx-auto mb-8">
        <h1 className="text-4xl font-bold text-slate-900 mb-4">
          Available Tutors
        </h1>
        {/* FILTER */}
        <div className="flex items-center gap-3">
          <label className="font-semibold text-slate-700">Select Grade</label>

          <select
            value={grade}
            onChange={handleGradeChange}
            className="border rounded-lg px-3 py-2 bg-white shadow-sm focus:outline-none focus:ring-2 focus:ring-green-500"
          >
            <option value="">All Grades</option>
            {[...Array(12)].map((_, i) => (
              <option key={i + 1} value={i + 1}>
                Grade {i + 1}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* TUTOR GRID */}
      <div className="max-w-6xl mx-auto">

        {!tutors && (
          <p className="text-slate-500 text-center mt-10">Loading tutors...</p>
        )}

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">

          {tutors &&
            tutors.map((tutor) => (
              <div
                key={tutor.tutorId}
                onClick={() => navigate(`/tutors/${tutor.tutorId}`)}
                className="bg-white rounded-2xl shadow-md hover:shadow-xl transition cursor-pointer border border-green-800/30 p-6 flex flex-col items-center text-center"
              >
                {/* PROFILE IMAGE */}
                <img
                  src={tutor.profilePictureUrl}
                  alt={tutor.name}
                  className="w-20 h-20 rounded-full object-cover mb-4"
                />

                {/* NAME */}
                <h3 className="text-xl font-semibold text-slate-800">
                  {tutor.name}
                </h3>

                {/* RATING */}
                <p className="text-amber-500 mt-1">
                  ⭐ {tutor.avgRating}
                  <span className="text-slate-500 ml-1">
                    ({tutor.reviewCount})
                  </span>
                </p>

                {/* GRADES */}
                <p className="text-slate-600 mt-2 text-sm">
                  Grades {tutor.minGradeLevel} – {tutor.maxGradeLevel}
                </p>

                {/* SUBJECT TAGS */}
                <div className="flex flex-wrap justify-center gap-2 mt-3">
                  {tutor.subjects.map((s) => (
                    <span
                      key={s.subjectId}
                      className="bg-green-100 text-green-600 px-3 py-1 rounded-full text-xs"
                    >
                      {s.name}
                    </span>
                  ))}
                </div>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
}
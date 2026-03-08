import { Link, useParams } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { type Tutor } from "../types/tutor";
import { type TutorTimeslot } from "../types/tutor";
import { useState } from "react";
import { Dialog, DialogContent } from "@/components/ui/dialog";
import SessionReviewPage from "./SessionReviewPage";
import { Button } from "@/components/ui/button";
import { type Subject } from "../types/subject";
import type { Reviews } from "../types/reviews";
import { format, parseISO } from "date-fns";
import type { ChildResponse } from "@/types/parents";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

export default function TutorProfilePage() {
  const { tutorId } = useParams();
  const tutor = useLearningCenterAPI<Tutor>(
    tutorId ? `/api/tutors/${tutorId}` : "",
  );
  const availability = useLearningCenterAPI<TutorTimeslot[]>(
    tutorId ? `/api/tutors/${tutorId}/availability` : "",
  );
  const reviews = useLearningCenterAPI<Reviews[]>(
    tutorId ? `/api/tutors/${tutorId}/reviews` : "",
  );

  const parentId = 1;

  const children = useLearningCenterAPI<ChildResponse[]>(
    `/api/parents/${parentId}/children`,
  );

  const [selectedChildId, setSelectedChildId] = useState<number | null>(null);

  const [selectedTimeSlot, setSelectedTimeSlot] = useState(-1);
  const [isOpen, setIsOpen] = useState(false);
  const [selectedSubjectId, setSelectedSubjectId] = useState<number | null>(
    null,
  );

  if (!tutorId) {
    return (
      <p>
        {" "}
        Invalid tutor id. Please go back and search for tutors here:{" "}
        <Link to="/"> Search all Tutors </Link>
      </p>
    );
  }
  if (!tutor) return <p>Loading...</p>;

  return (
    <>
      {/* MAIN GRID LAYOUT */}
      <div className="grid grid-cols-12 gap-6 h-min-full p-6 bg-slate-50">
        <div className="col-span-5 flex flex-col gap-6">
          {/* IMAGE + BASIC INFO */}
          <div className="grid grid-cols-2 gap-4">
            <div className="bg-white rounded-2xl shadow overflow-hidden h-[220px]">
              <img
                src={tutor.profilePictureUrl}
                alt={tutor.name}
                className="w-full h-full object-cover"
              />
            </div>

            <div className="bg-white rounded-2xl shadow p-6 flex flex-col justify-center border border-sky-800/40 shadow-md shadow-sky-200/40">
              <h2 className="text-xl font-bold text-slate-800">{tutor.name}</h2>

              <p className="text-slate-500">
                Grades {tutor.minGradeLevel} – {tutor.maxGradeLevel}
              </p>

              <div className="flex items-center gap-2 mt-2">
                ⭐ {tutor.avgRating}
                <span className="text-slate-400">
                  ({reviews?.length ?? 0} reviews)
                </span>
              </div>
            </div>
          </div>

          {/* ABOUT ME */}
          <div className="bg-white rounded-2xl shadow p-6 border border-sky-800/50 shadow-md shadow-sky-200/40">
            <h3 className="font-semibold text-lg mb-3">About Me</h3>

            <p className="text-slate-600">{tutor.tutorSummary}</p>

            <h4 className="mt-4 font-semibold">Subjects</h4>

            <div className="flex flex-wrap gap-2 mt-2">
              {tutor.subjects.map((s: Subject) => (
                <span
                  key={s.subjectId}
                  className="bg-sky-100 text-sky-600 px-3 py-1 rounded-full text-sm"
                >
                  {s.name}
                </span>
              ))}
            </div>
          </div>

          {/* NEXT AVAILABLE SLOT */}
          <div className="bg-white rounded-2xl shadow p-6 border border-sky-800/50 shadow-md shadow-sky-200/40">
            <h3 className="font-semibold mb-2">Next Available Session</h3>

            {availability && availability.length > 0 ? (
              <p className="text-slate-700 font-medium">
                {format(parseISO(availability[0].start), "MMM d, h:mm a")}
              </p>
            ) : (
              <p className="text-slate-400">No sessions available</p>
            )}
          </div>

          {/* REVIEW PREVIEW */}
          <div className="bg-white rounded-2xl shadow p-6 flex-1 overflow-y-auto border border-sky-800/50 shadow-md shadow-sky-200/40">
            <h3 className="font-semibold mb-4">Tutor Reviews</h3>

            {reviews && reviews.length > 0 ? (
              reviews.map((review) => (
                <div key={review.reviewId} className="mb-4 border-b pb-3">
                  <p className="text-yellow-500">
                    {"⭐".repeat(review.rating)}
                  </p>

                  <p className="text-slate-600">{review.comment}</p>
                </div>
              ))
            ) : (
              <p className="text-slate-400">No reviews available</p>
            )}
          </div>
        </div>

        {/* RIGHT COLUMN */}
        <div className="col-span-7 bg-white rounded-2xl shadow p-6 flex flex-col border border-sky-800/50 shadow-md shadow-sky-200/40">
          <h2 className="text-xl font-semibold mb-4">Schedule a Session</h2>

          {/* SUBJECT DROPDOWN */}
          <div className="mb-6 ">
            <h3 className="font-semibold mb-2">Choose Subject</h3>

            <Select
              value={
                selectedSubjectId !== null ? String(selectedSubjectId) : ""
              }
              onValueChange={(value) => setSelectedSubjectId(Number(value))}
            >
              <SelectTrigger className="w-[250px]">
                <SelectValue placeholder="Select a subject" />
              </SelectTrigger>

              <SelectContent>
                {tutor.subjects.map((s: Subject) => (
                  <SelectItem key={s.subjectId} value={String(s.subjectId)}>
                    {s.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          {/* AVAILABILITY */}
          <div className="flex-1 bg-slate-100 rounded-xl p-4 overflow-y-auto border border-sky-800/50">
            <h3 className="mb-3 font-semibold">Availability</h3>

            <ul className="space-y-2">
              {availability &&
                availability.map((slot, index) => (
                  <li
                    key={slot.tutorTimeslotId}
                    onClick={() => setSelectedTimeSlot(index)}
                    className={`cursor-pointer p-3 rounded-lg border
${
  selectedTimeSlot === index
    ? "bg-sky-500 text-white"
    : "bg-white hover:bg-sky-100"
}`}
                  >
                    {format(parseISO(slot.start), "MMM d, yyyy h:mm a")}
                    {" - "}
                    {format(parseISO(slot.end), "h:mm a")}
                  </li>
                ))}
            </ul>
          </div>

          <Button
            variant="secondary"
            disabled={selectedTimeSlot === -1 || selectedSubjectId === null}
            className="mt-4"
            onClick={() => setIsOpen(true)}
          >
            Book this Session
          </Button>
        </div>
      </div>

      <ul>
        <Dialog open={isOpen} onOpenChange={setIsOpen}>
          <DialogContent className="max-w-lg">
            {availability && selectedTimeSlot !== -1 && (
              <div className="space-y-4">
                <div className="space-y-2">
                  <h2 className="text-lg font-semibold">Select Child</h2>

                  <Select
                    value={
                      selectedChildId !== null ? String(selectedChildId) : ""
                    }
                    onValueChange={(value) => setSelectedChildId(Number(value))}
                  >
                    <SelectTrigger className="w-full">
                      <SelectValue placeholder="Select a child" />
                    </SelectTrigger>

                    <SelectContent>
                      {children?.map((c) => (
                        <SelectItem key={c.childId} value={String(c.childId)}>
                          {c.firstName} (Grade {c.gradeLevel})
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                <SessionReviewPage
                  tutor={tutor}
                  slot={availability[selectedTimeSlot]}
                  subjectId={selectedSubjectId}
                  childId={selectedChildId}
                  onClose={() => setIsOpen(false)}
                />
              </div>
            )}
          </DialogContent>
        </Dialog>
      </ul>
    </>
  );
}

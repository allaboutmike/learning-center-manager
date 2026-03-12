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
import { usePersona } from "@/context/usePersona";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

import { Calendar } from "@/components/ui/calendar";

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

  const { persona } = usePersona();

  const parentId = persona.id as number;

  const children = useLearningCenterAPI<ChildResponse[]>(
    `/api/parents/${parentId}/children`,
  );

  const [selectedChildId, setSelectedChildId] = useState<number | null>(null);
  const [selectedTimeSlot, setSelectedTimeSlot] = useState(-1);
  const [selectedDate, setSelectedDate] = useState<Date | undefined>(undefined);
  const [isOpen, setIsOpen] = useState(false);
  const [selectedSubjectId, setSelectedSubjectId] = useState<number | null>(
    null,
  );

  if (!tutorId) {
    return (
      <p>
        Invalid tutor id. Please go back and search for tutors here:{" "}
        <Link to="/">Search all Tutors</Link>
      </p>
    );
  }

  if (!tutor) return <p>Loading...</p>;

  // Group availability by date
  const availabilityByDate =
    availability?.reduce(
      (acc, slot) => {
        const date = slot.start.split("T")[0];

        if (!acc[date]) {
          acc[date] = [];
        }

        acc[date].push(slot);

        return acc;
      },
      {} as Record<string, TutorTimeslot[]>,
    ) ?? {};

  // Convert available dates to Date objects
  const availableDates = Object.keys(availabilityByDate).map(
    (date) => new Date(date),
  );

  const timesForSelectedDate = selectedDate
    ? (availabilityByDate[format(selectedDate, "yyyy-MM-dd")] ?? [])
    : [];

  return (
    <>
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 h-min-full p-4 lg:p-6 bg-slate-50">
        {/* LEFT COLUMN */}
        <div className="lg:col-span-5 flex flex-col gap-6">
          {/* IMAGE + BASIC INFO */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="bg-white rounded-2xl shadow overflow-hidden h-[220px]">
              <img
                src={tutor.profilePictureUrl}
                alt={tutor.name}
                className="w-full h-full object-cover"
              />
            </div>

            <div className="bg-white rounded-2xl shadow p-6 flex flex-col justify-center border border-slate-200 shadow-md shadow-green-200/40">
              <h2 className="text-2xl font-bold text-slate-900">{tutor.name}</h2>

              <p className="text-slate-500">
                Grades {tutor.minGradeLevel} – {tutor.maxGradeLevel}
              </p>

              <div className="flex items-center gap-2 mt-2 text-amber-500">
                ⭐ {tutor.avgRating}
                <span className="text-slate-500">
                  ({reviews?.length ?? 0} reviews)
                </span>
              </div>
            </div>
          </div>

          {/* ABOUT ME */}
          <div className="bg-white rounded-2xl shadow p-6 border border-slate-200 shadow-md shadow-green-200/40">
            <h3 className="font-semibold text-xl mb-3">About Me</h3>

            <p className="text-slate-600">
              {tutor.summary || "No summary available."}{" "}
            </p>

            <h4 className="mt-4 font-semibold text-lg">Subjects</h4>

            <div className="flex flex-wrap gap-2 mt-2">
              {tutor.subjects.map((s: Subject) => (
                <span
                  key={s.subjectId}
                  className="bg-green-100 text-green-700 px-3 py-1 rounded-full text-sm font-medium"
                >
                  {s.name}
                </span>
              ))}
            </div>
          </div>

          {/* NEXT AVAILABLE */}
          <div className="bg-white rounded-2xl shadow p-6 border border-slate-200 shadow-md shadow-green-200/40">
            <h3 className="font-semibold mb-2 text-xl">
              Next Available Session
            </h3>

            {availability && availability.length > 0 ? (
              <p className="text-slate-800 font-semibold">
                {format(parseISO(availability[0].start), "MMM d, h:mm a")}
              </p>
            ) : (
              <p className="text-slate-400">No sessions available</p>
            )}
          </div>

          {/* REVIEWS */}
          <div className="bg-white rounded-2xl shadow p-6 flex-1 overflow-y-auto border border-slate-200 shadow-md shadow-green-200/40">
            <h3 className="font-semibold mb-4 text-xl">Tutor Reviews</h3>

            {reviews && reviews.length > 0 ? (
              reviews.map((review) => (
                <div key={review.reviewId} className="mb-4 border-b pb-3">
                  <p className="text-amber-500">
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
        <div className="lg:col-span-7 bg-white rounded-2xl p-4 lg:p-6 flex flex-col border border-green-800/50 shadow-md shadow-green-200/40">
          <h2 className="text-xl font-semibold mb-4">Schedule a Session</h2>

          {/* SUBJECT */}
          <div className="mb-6">
            <h3 className="font-semibold mb-2 text-xl">Choose Subject</h3>

            <Select
              value={
                selectedSubjectId !== null ? String(selectedSubjectId) : ""
              }
              onValueChange={(value) => setSelectedSubjectId(Number(value))}
            >
              <SelectTrigger className="w-full sm:w-[250px]">
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

          {/* CALENDAR + TIMESLOTS */}
          <div className="flex flex-col lg:flex-row gap-6 flex-1">
            {/* CALENDAR */}
            <div className="bg-white rounded-xl p-2">
              <h3 className="font-semibold mb-2 text-xl">Select Date</h3>

              <Calendar
                mode="single"
                selected={selectedDate}
                onSelect={setSelectedDate}
                disabled={(date) =>
                  !availableDates.some(
                    (d) => d.toDateString() === date.toDateString(),
                  )
                }
                className="rounded-md"
              />
            </div>

            {/* TIMESLOTS */}
            <div className="flex-1 bg-slate-100 rounded-xl p-4 overflow-y-auto border border-slate-200">
              <h3 className="mb-3 font-semibold text-xl">Available Times</h3>

              {timesForSelectedDate.length === 0 && (
                <p className="text-slate-400">
                  Select a date to view available times
                </p>
              )}

              <ul className="space-y-2">
                {timesForSelectedDate.map((slot, index) => (
                  <li
                    key={slot.tutorTimeslotId}
                    onClick={() => setSelectedTimeSlot(index)}
                    className={`cursor-pointer p-3 rounded-lg border
                    ${
                      selectedTimeSlot === index
                        ? "bg-green-600 text-white"
                        : "bg-white hover:bg-green-100"
                    }`}
                  >
                    {format(parseISO(slot.start), "h:mm a")} –{" "}
                    {format(parseISO(slot.end), "h:mm a")}
                  </li>
                ))}
              </ul>
            </div>
          </div>

          {selectedSubjectId !== null && selectedTimeSlot === -1 && (
            <p className="mt-4 text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-4 py-2">
              Please select a session timeslot to continue.
            </p>
          )}

          {selectedSubjectId === null && selectedTimeSlot !== -1 && (
            <p className="mt-4 text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg px-4 py-2">
              Please select a subject to continue.
            </p>
          )}

          <Button
            variant="secondary"
            disabled={
              persona.id === null ||
              persona.role !== "parent" ||
              selectedTimeSlot === -1 ||
              selectedSubjectId === null ||
              !selectedDate
            }
            className={`mt-4 active:scale-95 transition ${
              persona.id !== null &&
              persona.role === "parent" &&
              selectedTimeSlot !== -1 &&
              selectedSubjectId !== null &&
              selectedDate
                ? "bg-green-600 hover:bg-green-700 text-white"
                : ""
            }`}
            onClick={() => setIsOpen(true)} 
          >
            Book this Session
          </Button>
        </div>
      </div>

      {/* BOOKING MODAL */}
      <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogContent className="max-w-lg">
          {timesForSelectedDate && selectedTimeSlot !== -1 && (
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
                slot={timesForSelectedDate[selectedTimeSlot]}
                subjectId={selectedSubjectId}
                childId={selectedChildId}
                onClose={() => setIsOpen(false)}
              />
            </div>
          )}
        </DialogContent>
      </Dialog>
    </>
  );
}

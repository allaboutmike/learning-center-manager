import { useNavigate, useLocation, Link } from 'react-router-dom';
import { useLearningCenterAPI, useLearningCenterPost } from '../hooks/useLearningCenterAPI';
import type { Tutor } from '../types/tutor';
import type { TutorTimeslot } from '../types/tutorTimeslot';
import type { Subject } from '../types/subject';


export default function SessionReviewModal() {
    const navigate = useNavigate();
    const location = useLocation();
    const postData = useLearningCenterPost();

    if (!location.state) {
        return <p>No session data. Please <Link to="/tutors">select a tutor</Link> to book a session.</p>;
    }
    
    const { tutorId, subjectId, tutorTimeSlotId, childId } = location.state as {
        tutorId: number;
        subjectId: number;
        tutorTimeSlotId: number;
        childId: number;
    };

    const tutor = useLearningCenterAPI<Tutor>(`/api/tutors/${tutorId}`);
    const availability = useLearningCenterAPI<TutorTimeslot[]>(`/api/tutors/${tutorId}/availability`);

    const handleConfirmSession = async () => {
        try {
            // Create the session via POST request with all required IDs
            await postData<{ sessionId: string }>('/api/sessions', {
                tutorId,
                subjectId,
                childId,
                tutorTimeSlotId
            });

            // Navigate to confirmation page with the new session ID
            navigate(`/confirmation`);
        } catch (error) {
            console.error('Failed to create session:', error);
            alert('Failed to create session. Please try again.');
        }
    };

    if (!tutor || !availability) return <p>Loading...</p>;

    const selectedSubject = tutor.subjects.find((s: Subject) => s.subjectId === subjectId);
    const selectedTimeslot = availability.find((t: TutorTimeslot) => t.tutorTimeslotId === tutorTimeSlotId);
    if (!selectedSubject || !selectedTimeslot) return <p>Invalid session details.</p>;

    const sessionDate = new Date(selectedTimeslot.start);
    const formattedDate = sessionDate.toLocaleDateString('en-US', { 
        weekday: 'long', 
        month: 'long', 
        day: 'numeric', 
        year: 'numeric' 
    });
    const formattedTime = sessionDate.toLocaleTimeString('en-US', { 
        hour: 'numeric', 
        minute: '2-digit',
        hour12: true 
    });

    return (
        <div style={{ textAlign: 'center', marginTop: 50 }}>
            <h1>
                You've booked a session with {tutor.name} on {formattedDate} at {formattedTime}.
            </h1>
            <p>Subject: {selectedSubject.name}</p>
            <p>Click the button below to confirm your session.</p>
            <button 
                style={{ padding: '10px 20px', fontSize: 16 }} 
                onClick={handleConfirmSession}
            >
                Confirm Session
            </button>
        </div>
    );
}
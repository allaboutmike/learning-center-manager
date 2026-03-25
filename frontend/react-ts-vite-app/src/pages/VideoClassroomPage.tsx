import { useEffect, useRef } from "react";
import { useParams } from "react-router-dom";

export default function VideoClassroomPage() {
  const { sessionId } = useParams();
  const dialogRef = useRef<HTMLDialogElement | null>(null);

  useEffect(() => {
    const existing = document.querySelector(
      'script[src="https://cdn.jsdelivr.net/npm/@vonage/white-board@latest/white-board.js/+esm"]'
    );

    if (existing) {
      return;
    }

    const script = document.createElement("script");
    script.type = "module";
    script.src =
      "https://cdn.jsdelivr.net/npm/@vonage/white-board@latest/white-board.js/+esm";

    document.body.appendChild(script);
  }, []);

  return (
    <div className="p-6">
      <div className="mb-6">
        <h1 className="text-3xl font-bold">Live Session</h1>
        <p className="text-sm text-slate-500">Session ID: {sessionId}</p>
      </div>

      <div className="grid grid-cols-1 gap-6 lg:grid-cols-[1.1fr_1fr]">
        <section className="rounded-2xl border bg-white p-4 shadow-sm">
          <h2 className="mb-4 text-xl font-semibold">Video Area</h2>

          <div className="flex h-[350px] items-center justify-center rounded-xl bg-slate-100 text-slate-500">
            Video stream will appear here
          </div>

          <div className="mt-4 flex flex-wrap gap-3">
            <button className="rounded-lg border px-4 py-2 text-sm font-medium">
              Toggle Video
            </button>

            <button className="rounded-lg border px-4 py-2 text-sm font-medium">
              Toggle Audio
            </button>

            <button
              onClick={() => dialogRef.current?.showModal()}
              className="rounded-lg bg-green-600 px-4 py-2 text-sm font-medium text-white"
            >
              Open Whiteboard
            </button>
          </div>
        </section>

        <section className="rounded-2xl border bg-white p-4 shadow-sm">
          <h2 className="mb-4 text-xl font-semibold">Participants</h2>
          <div className="flex min-h-[350px] items-center justify-center rounded-xl bg-slate-100 text-slate-500">
            Participant video tiles will appear here
          </div>
        </section>
      </div>

      <dialog
        ref={dialogRef}
        className="w-full max-w-4xl rounded-2xl p-0 backdrop:bg-black/40"
      >
        <div className="flex items-center justify-between border-b p-4">
          <h2 className="font-semibold">Whiteboard</h2>
          <form method="dialog">
            <button className="rounded border px-3 py-1">Close</button>
          </form>
        </div>

        <div className="h-[70vh] p-4">
        </div>
      </dialog>
    </div>
  );
}
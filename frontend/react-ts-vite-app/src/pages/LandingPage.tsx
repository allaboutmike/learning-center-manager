import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { GuestSidebar } from "@/components/guest-sidebar";
import { SidebarProvider } from "@/components/ui/sidebar";

export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <SidebarProvider>
      <GuestSidebar />
      <main className="flex-1 flex flex-col items-center justify-center bg-slate-50 p-12 text-center">
        <h1 className="text-4xl font-bold text-slate-800 mb-4">
          Welcome to Dallas Learning Center!
        </h1>
        <p className="text-lg text-slate-500 max-w-lg mb-8">
          We are excited you would like to book a session with us! Browse our
          tutors below to find the right fit for your child.
        </p>
        <Button
          size="lg"
          onClick={() => navigate("/tutors")}
          className="bg-green-600 hover:bg-green-700 text-white"
        >
          View Our Tutors
        </Button>
      </main>
    </SidebarProvider>
  );
}

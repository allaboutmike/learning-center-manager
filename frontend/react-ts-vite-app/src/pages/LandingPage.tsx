import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { GuestSidebar } from "@/components/guest-sidebar";
import { SidebarProvider } from "@/components/ui/sidebar";


export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <SidebarProvider>
      <div className="flex w-full min-h-screen bg-slate-50">
        <GuestSidebar />

        <main className="flex-1 flex items-center justify-center px-12 py-16">
          <div className="max-w-6xl grid md:grid-cols-2 gap-12 items-center">

            {/* LEFT SIDE TEXT */}
            <div className="text-left space-y-6">

              <h1 className="text-4xl font-bold text-slate-900 leading-tight">
                Welcome to
                <span className="text-green-700"> Dallas Learning Center</span>
              </h1>

              <p className="text-lg text-slate-600 max-w-lg">
                We're excited you would like to book a session with us!
                Browse our tutors to find the perfect learning partner
                for your child.
              </p>

              <div className="flex gap-4 pt-4">

                <Button
                  size="lg"
                  onClick={() => navigate("/tutors")}
                  className="bg-green-600 hover:bg-green-700 text-white px-8"
                >
                  Browse Our Tutors
                </Button>

                <Button
                  size="lg"
                  onClick={() => navigate("/parents/register")}
                  variant="outline"
                  className="px-8"
                >
                  Sign Up
                </Button>

              </div>

            </div>

            {/* RIGHT SIDE IMAGE */}
            <div className="flex justify-center">
              <img
                src="team.png"
                alt="Dallas Learning Center Team"
                className="w-[900px]"
              />
            </div>

          </div>
        </main>
      </div>
    </SidebarProvider>
  );
}
import { useState, type SubmitEvent } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { useLearningCenterPost } from "@/hooks/useLearningCenterAPI";
import type { Parent } from "../types/parents";
import { usePersona } from "@/context/usePersona";
import { GuestSidebar } from "@/components/guest-sidebar";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import { SiteHeader } from "@/components/site-header";

export default function RegisterParentPage() {
  const navigate = useNavigate();
  const post = useLearningCenterPost();
  const { setPersona } = usePersona();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setIsSubmitting(true);

    try {
      const parent = await post<Parent>("/api/parents", {
        name: `${firstName} ${lastName}`,
        email,
        phone: phone || null,
      });

      setPersona({
        role: "parent",
        id: parent.parentId,
        name: `${firstName} ${lastName}`,
        image: "/parent.png",
      });

      navigate("/parents");
    } catch (err) {
      if (err instanceof Error && err.message.includes("409")) {
        setError("An account with this email already exists.");
      } else {
        setError("Registration failed. Please try again.");
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <SidebarProvider>
      <GuestSidebar variant="inset" showFooter />
      <SidebarInset>
        <SiteHeader />
        <div className="min-h-screen flex items-center justify-center bg-gray-50">
          <div className="bg-white rounded-xl shadow-md p-8 w-full max-w-md">
            <h1 className="text-2xl font-bold mb-6">Register as a Parent</h1>

            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <div className="flex gap-3">
                <div className="flex flex-col gap-1 flex-1">
                  <label className="text-sm font-medium">First Name *</label>
                  <input
                    type="text"
                    required
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-500"
                    placeholder="Jane"
                  />
                </div>

                <div className="flex flex-col gap-1 flex-1">
                  <label className="text-sm font-medium">Last Name *</label>
                  <input
                    type="text"
                    required
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-500"
                    placeholder="Smith"
                  />
                </div>
              </div>

              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Email *</label>
                <input
                  type="email"
                  required
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-500"
                  placeholder="jane.smith@example.com"
                />
              </div>

              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Phone (optional)</label>
                <input
                  type="tel"
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
                  className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-500"
                  placeholder="(555) 000-0000"
                />
              </div>

              {error && (
                <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-md px-3 py-2">
                  {error}
                </p>
              )}

              <Button
                type="submit"
                disabled={isSubmitting}
                className="mt-2 bg-green-600 hover:bg-green-700 text-white"
              >
                {isSubmitting ? "Registering..." : "Create Account"}
              </Button>
            </form>
          </div>
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}
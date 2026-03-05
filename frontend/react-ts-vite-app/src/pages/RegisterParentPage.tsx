import { useState, type SubmitEvent } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { useLearningCenterPost } from "@/hooks/useLearningCenterAPI";
import type { Parent } from "../types/parents";

export default function RegisterParentPage() {
  const navigate = useNavigate();
  const post = useLearningCenterPost();

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

      localStorage.setItem("parentId", String(parent.parentId));
      navigate("/");

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
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="bg-white rounded-xl shadow-md p-8 w-full max-w-md">

        <h1 className="text-2xl font-bold mb-6">Register as a Parent</h1>

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">

          {/* Name row */}
          <div className="flex gap-3">
            <div className="flex flex-col gap-1 flex-1">
              <label className="text-sm font-medium">First Name *</label>
              <input
                type="text"
                required
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
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
                className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Smith"
              />
            </div>
          </div>

          {/* Email */}
          <div className="flex flex-col gap-1">
            <label className="text-sm font-medium">Email *</label>
            <input
              type="email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="jane.smith@example.com"
            />
          </div>

          {/* Phone */}
          <div className="flex flex-col gap-1">
            <label className="text-sm font-medium">Phone (optional)</label>
            <input
              type="tel"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              className="border rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="(555) 000-0000"
            />
          </div>

          {/* Error message */}
          {error && (
            <p className="text-sm text-red-600 bg-red-50 border border-red-200 rounded-md px-3 py-2">
              {error}
            </p>
          )}

          <Button
            type="submit"
            disabled={isSubmitting}
            className="mt-2 bg-blue-600 hover:bg-blue-700 text-white"
          >
            {isSubmitting ? "Registering..." : "Create Account"}
          </Button>

        </form>
      </div>
    </div>
  );
}

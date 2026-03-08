import { personas } from "../types/personas";
import { useNavigate } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";

const PersonaPage = () => {

  type ParentResponse = {
    parentId: number
    name: string
  }

  // STEP 5 — fetch last 3 parents
  const recentParents =
    useLearningCenterAPI<ParentResponse[]>("/parents/recent");

  const navigate = useNavigate()

  // STEP 5 — convert API parents to persona objects
  const dynamicParents =
    recentParents?.map((parent) => ({
      name: parent.name,
      role: "parent",
      id: parent.parentId,
      redirect: "/parents",
      image: "/images/parent.png"
    })) ?? [];

  // STEP 6 — combine static + dynamic personas
  const allPersonas = [
    personas[0], // Parent 1
    personas[1], // Parent 2
    ...dynamicParents, // Last 3 parents from DB
    personas[2], // Tutor
    personas[3]  // Admin
  ];

  const handleSelect = (persona: typeof personas[0]) => {

  localStorage.setItem("role", persona.role)

  if (persona.role === "parent" && persona.id) {
    localStorage.setItem("parentId", persona.id.toString())

    navigate(`/parents/${persona.id}`)
    return
  }

  if (persona.role === "tutor" && persona.id) {
    localStorage.setItem("tutorId", persona.id.toString())

    navigate(`/tutors/${persona.id}/dashboard`)
    return
  }

  if (persona.role === "admin") {
    navigate("/admin")
  }
}

  return (
    <div className="flex flex-col items-center min-h-screen bg-slate-50 p-8">

      <h1 className="text-3xl font-bold text-slate-700 mb-10">
        Select Your Persona
      </h1>

      <div className="grid grid-cols-3 gap-6 max-w-4xl">

        {allPersonas.map((persona) => (
          <button
            key={persona.name}
            onClick={() => handleSelect(persona)}
            className="bg-white rounded-2xl shadow-md w-[250px] h-[220px] flex flex-col items-center justify-center text-center p-4 hover:shadow-lg hover:scale-105 transition"
          >
            <img
              src={persona.image}
              alt={persona.name}
              className="w-20 h-20 mb-3"
            />

            <h2 className="text-xl font-semibold text-slate-700">
              {persona.name}
            </h2>

            <p className="text-sm text-slate-500">
              Go to {persona.role} dashboard
            </p>
          </button>
        ))}

      </div>

    </div>
  )
}

export default PersonaPage
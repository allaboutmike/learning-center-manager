import { personas, type Persona } from "../types/personas";
import { useNavigate } from "react-router-dom";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { usePersona } from "@/context/usePersona";

const PersonaPage = () => {
  type ParentResponse = {
    parentId: number
    name: string
  }
  const { setPersona } = usePersona();

  const recentParents =
    useLearningCenterAPI<ParentResponse[]>(`/api/parents/recent`);

const parent1 = useLearningCenterAPI<ParentResponse>(`/api/parents/1`);
const parent2 = useLearningCenterAPI<ParentResponse>(`/api/parents/2`);



  const navigate = useNavigate()

  const parentImages = [
  "/parent6.png",
  "/parent4.png",
  "/parent7.png",
  "/parent1.png",
  ];

  const staticParents = [
  {
    ...personas[0],
    name: parent1?.name ?? "Parent 1",
  },
  {
    ...personas[1],
    name: parent2?.name ?? "Parent 2",
  }
];
  const dynamicParents =
  recentParents?.map((parent, index) => ({
    name: parent.name,
    dbName: parent.name,
    role: "parent",
    id: parent.parentId,
    redirect: "/parents",
    image: parentImages[index % parentImages.length]
  })) ?? [];

  
  const allPersonas = [
    ...staticParents,
    ...dynamicParents, // Last 3 parents from DB
    personas[2], // Tutor
    personas[3]  // Admin
    ] as Persona[];

  const handleSelect = (persona: Persona) => {
    setPersona(persona);    

    if (persona.role === "parent" && persona.id) {
      navigate(`/parents`)
      return
    }

    if (persona.role === "tutor" && persona.id) {
      navigate(`/tutors/dashboard`)
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
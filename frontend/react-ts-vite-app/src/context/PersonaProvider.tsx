import { useState } from "react";
import type { ReactNode } from "react";
import { PersonaContext } from "./PersonaContext";
import type { Persona, PersonaRoles } from "@/types/personas";
import { personas } from "@/types/personas";

const PERSONA_ORDER: PersonaRoles[] = ["guest", "parent", "admin", "tutor"];
const GUEST: Persona = { role: "guest", id: 0, name: "Guest", image: "/guest.png" };

export function PersonaProvider({ children }: { children: ReactNode }) {
  const [persona, setPersonaState] = useState<Persona>(() => {
    // Parent is set as the fallback page, which has no access to the admin dashboard button in the side bar.
    let app_persona;

    try {
      app_persona = JSON.parse(localStorage.getItem("app_persona") || "null") as Persona;
    } catch (e) {
      console.error("Failed to parse persona from localStorage:", e);
      setPersonaState(GUEST);
      return GUEST;
    }

    const saved = app_persona;
    return saved && ["parent", "admin", "tutor"].includes(saved.role)
      ? saved
      : GUEST;
  });

  const setPersona = (newPersona: Persona) => {
    localStorage.setItem("app_persona", JSON.stringify(newPersona));
    setPersonaState(newPersona);
  };

  const togglePersona = () => {
    const currentIndex = PERSONA_ORDER.indexOf(persona.role);
    const nextIndex = (currentIndex + 1) % PERSONA_ORDER.length;
    const nextPersona = PERSONA_ORDER[nextIndex];
    setPersona(personas.find((p) => p.role === nextPersona) || GUEST);
  };

  return (
    <PersonaContext.Provider value={{ persona, setPersona, togglePersona }}>
      {children}
    </PersonaContext.Provider>
  );
}

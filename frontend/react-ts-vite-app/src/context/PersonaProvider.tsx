import { useState } from "react";
import type { ReactNode } from "react";
import { PersonaContext } from "./PersonaContext";
import type { Persona } from "./PersonaContext";

const PERSONA_ORDER: Persona[] = ["guest", "parent", "admin", "tutor"];

export function PersonaProvider({ children }: { children: ReactNode }) {
  const [persona, setPersonaState] = useState<Persona>(() => {
    // Parent is set as the fallback page, which has no access to the admin dashboard button in the side bar.
    const saved = localStorage.getItem("app_persona") as Persona;
    return saved && ["parent", "admin", "tutor"].includes(saved)
      ? saved
      : "guest";
  });

  const setPersona = (newPersona: Persona) => {
    localStorage.setItem("app_persona", newPersona);
    setPersonaState(newPersona);
  };

  const togglePersona = () => {
    const currentIndex = PERSONA_ORDER.indexOf(persona);
    const nextIndex = (currentIndex + 1) % PERSONA_ORDER.length;
    const nextPersona = PERSONA_ORDER[nextIndex];
    setPersona(nextPersona);
  };

  return (
    <PersonaContext.Provider value={{ persona, setPersona, togglePersona }}>
      {children}
    </PersonaContext.Provider>
  );
}

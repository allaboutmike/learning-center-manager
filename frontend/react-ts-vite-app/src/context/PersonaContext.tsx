import { createContext } from "react";

export type Persona = "parent" | "admin" | "tutor";

export type PersonaContextType = {
  persona: Persona;
  setPersona: (persona: Persona) => void;
  togglePersona: () => void;
};

export const PersonaContext = createContext<PersonaContextType | null>(null);

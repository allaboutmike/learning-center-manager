import { createContext } from "react";
import type { Persona } from "../types/personas";

export type PersonaContextType = {
  persona: Persona;
  setPersona: (persona: Persona) => void;
  togglePersona: () => void;
};

export const PersonaContext = createContext<PersonaContextType | null>(null);

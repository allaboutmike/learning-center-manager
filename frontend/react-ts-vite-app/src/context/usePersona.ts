import { useContext } from "react";
import { PersonaContext } from "./PersonaContext";

export function usePersona() {
  const ctx = useContext(PersonaContext);
  if (!ctx) throw new Error("usePersona must be used within a PersonaProvider");
  return ctx;
}

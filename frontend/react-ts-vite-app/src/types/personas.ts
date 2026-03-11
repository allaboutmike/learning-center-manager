export type PersonaRoles = "parent" | "admin" | "tutor" | "guest";

export type Persona = {
  name: string;
  role: PersonaRoles;
  id: number | null;
  image: string;
};

export const personas: Persona[] = [
  {
    name: "Parent 1",
    role: "parent",
    id: 1,
    image: "/parent1.png",
  },
  {
    name: "Parent 2",
    role: "parent",
    id: 2,
    image: "/parent2.png",
  },
  {
    name: "Tutor",
    role: "tutor",
    id: 166,
    image: "/tutor.png",
  },
  {
    name: "Admin",
    role: "admin",
    id: null,
    image: "/admin.png",
  },
];
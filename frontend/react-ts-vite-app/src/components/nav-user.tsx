import {
  IconDotsVertical,
  IconLogout,
  IconUserCircle,
  IconUserShield,
  IconUsers,
  IconSchool,
} from "@tabler/icons-react";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar";
import { usePersona } from "@/context/usePersona";
import type { Persona, PersonaRoles } from "@/types/personas";
import { personas } from "@/types/personas";
import { useNavigate } from "react-router-dom";

const activePersonas = personas.filter((p) => p.role !== "guest" && p.id !== 2);
const personaLabels: Record<PersonaRoles, string> = {
  parent: "Parent",
  admin: "Admin",
  tutor: "Tutor",
  guest: "Guest",
};

const personaIcons: Record<PersonaRoles, React.ReactNode> = {
  parent: <IconUsers className="size-4" />,
  admin: <IconUserShield className="size-4" />,
  tutor: <IconSchool className="size-4" />,
  guest: <IconUserCircle className="size-4" />,
};

const personaRoutes: Record<PersonaRoles, string> = {
  parent: "/parents",
  admin: "/admin",
  tutor: "/tutors/dashboard",
  guest: "/",
};

export function NavUser({
  user,
}: {
  user: {
    name: string;
    email: string;
    avatar: string;
  };
}) {
  const { isMobile } = useSidebar();
  const navigate = useNavigate();
  const { persona, setPersona } = usePersona();

  const handlePersonaChange = (p: Persona) => {
    setPersona(p);
    navigate(personaRoutes[p.role]);
  };

  const handleLogout = () => {
    localStorage.removeItem("app_persona");
    setPersona({role: "guest", name: "Guest", id: null, image: ""});
    navigate("/");
  };

  return (
    <SidebarMenu>
      <SidebarMenuItem>
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <SidebarMenuButton
              size="lg"
              className="data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground"
            >
              <Avatar className="h-8 w-8 rounded-lg grayscale">
                <AvatarImage src={user.avatar} alt={user.name} />
                <AvatarFallback className="rounded-lg">CN</AvatarFallback>
              </Avatar>
              <div className="grid flex-1 text-left text-sm leading-tight">
                <span className="truncate font-medium">{user.name}</span>
                <span className="text-muted-foreground truncate text-xs">
                  {personaLabels[persona.role]}
                </span>
              </div>
              <IconDotsVertical className="ml-auto size-4" />
            </SidebarMenuButton>
          </DropdownMenuTrigger>
          <DropdownMenuContent
            className="w-(--radix-dropdown-menu-trigger-width) min-w-56 rounded-lg"
            side={isMobile ? "bottom" : "right"}
            align="end"
            sideOffset={4}
          >
            <DropdownMenuLabel className="p-0 font-normal">
              <div className="flex items-center gap-2 px-1 py-1.5 text-left text-sm">
                <Avatar className="h-8 w-8 rounded-lg">
                  <AvatarImage src={user.avatar} alt={user.name} />
                  <AvatarFallback className="rounded-lg">CN</AvatarFallback>
                </Avatar>
                <div className="grid flex-1 text-left text-sm leading-tight">
                  <span className="truncate font-medium">{user.name}</span>
                  <span className="text-muted-foreground truncate text-xs">
                    {user.email}
                  </span>
                </div>
              </div>
            </DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuLabel className="text-muted-foreground text-xs px-2">
              Switch Persona
            </DropdownMenuLabel>
            <DropdownMenuGroup>
              {(activePersonas as Persona[]).map((p) => (
                <DropdownMenuItem
                  key={p.role}
                  onClick={() => handlePersonaChange(p)}
                  className={persona.role === p.role ? "bg-accent" : ""}
                >
                  {personaIcons[p.role]}
                  {personaLabels[p.role]}
                </DropdownMenuItem>
              ))}
            </DropdownMenuGroup>
            {/* <DropdownMenuSeparator />
            <DropdownMenuGroup>
              <DropdownMenuItem>
                <IconUserCircle />
                Account
              </DropdownMenuItem>
              <DropdownMenuItem>
                <IconCreditCard />
                Billing
              </DropdownMenuItem>
              <DropdownMenuItem>
                <IconNotification />
                Notifications
              </DropdownMenuItem>
            </DropdownMenuGroup> */}
            <DropdownMenuSeparator />
            <DropdownMenuItem onClick={handleLogout}>
              <IconLogout />
              Log out
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </SidebarMenuItem>
    </SidebarMenu>
  );
}

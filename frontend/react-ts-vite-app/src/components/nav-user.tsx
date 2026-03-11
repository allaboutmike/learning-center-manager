import {
  IconCreditCard,
  IconDotsVertical,
  IconLogout,
  IconNotification,
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
import type { Persona } from "@/context/PersonaContext";
import { useNavigate } from "react-router-dom";

const personaLabels: Record<Persona, string> = {
  parent: "Parent",
  admin: "Admin",
  tutor: "Tutor",
  guest: "Guest",
};

const personaIcons: Record<Persona, React.ReactNode> = {
  parent: <IconUsers className="size-4" />,
  admin: <IconUserShield className="size-4" />,
  tutor: <IconSchool className="size-4" />,
  guest: <IconUserCircle className="size-4" />,
};

const personaRoutes: Record<Persona, string> = {
  parent: "/parents/:parentId",
  admin: "/admin",
  tutor: "/tutors/:tutorId/dashboard",
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
    navigate(personaRoutes[p]);
  };

  const handleLogout = () => {
    localStorage.removeItem("app_persona");
    localStorage.removeItem("parentId");
    localStorage.removeItem("tutorId");
    localStorage.removeItem("role");
    setPersona("guest");
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
                  {personaLabels[persona]}
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
              {(["parent", "admin", "tutor"] as Persona[]).map((p) => (
                <DropdownMenuItem
                  key={p}
                  onClick={() => handlePersonaChange(p)}
                  className={persona === p ? "bg-accent" : ""}
                >
                  {personaIcons[p]}
                  {personaLabels[p]}
                </DropdownMenuItem>
              ))}
            </DropdownMenuGroup>
            <DropdownMenuSeparator />
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
            </DropdownMenuGroup>
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

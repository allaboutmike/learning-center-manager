import * as React from "react";
import {
  IconChartBar,
  IconDashboard,
  IconFolder,
  IconListDetails,
  IconUserPlus,
  IconShieldCheck,
  IconUsers,
} from "@tabler/icons-react";

import { NavMain } from "@/components/nav-main";
import { NavUser } from "@/components/nav-user";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { useNavigate } from "react-router-dom";
import { usePersona } from "@/context/usePersona";
import type { PersonaRoles } from "@/types/personas";
import BuyCreditsDialog from "@/pages/BuyCreditsDialog";
import { useRegisterChildDialog } from "@/context/RegisterChildDialogContext";


const data = {
  user: {
    name: "Learning Central",
    email: "learningcentral@dlc.com",
    avatar: "/avatars/shadcn.jpg",
  },
  navMain: [
    {
      title: "Parent Profile",
      url: "/parents",
      icon: IconDashboard,
      roles: ["parent"] as PersonaRoles[],
    },
    {
      title: "Tutor Dashboard",
      url: "/tutors/dashboard",
      icon: IconDashboard,
      roles: ["tutor"] as PersonaRoles[],
    },
    {
      title: "Admin Dashboard",
      url: "/admin",
      icon: IconShieldCheck,
      roles: ["admin"] as PersonaRoles[],
    },
    {
      title: "Students",
      url: "/students",
      icon: IconListDetails,
      roles: [] as PersonaRoles[],
    },
    {
      title: "Session Review",
      url: "/session-review",
      icon: IconListDetails,
      roles: [] as PersonaRoles[],
    },
    {
      title: "Child's Progress",
      url: "/parents/:parentId/children/:childId/progress",
      icon: IconChartBar,
      roles: ["parent", "admin", "tutor"] as PersonaRoles[],
    },
    {
      title: "Book a Session",
      url: "/tutors",
      icon: IconFolder,
      roles: [] as PersonaRoles[],
    },
    {
      title: "List of Tutors",
      url: "/tutors",
      icon: IconListDetails,
      roles: ["parent"] as PersonaRoles[],
    },
    {
      title: "Register a Child",
      url: "/children/register",
      icon: IconUsers,
      roles: ["parent"] as PersonaRoles[],
    },
    {
      title: "Register a Parent",
      url: "/parents/register",
      icon: IconUserPlus,
      roles: [] as PersonaRoles[],
    },
  ],
  navSecondary: [],
};

type AppSidebarProps = React.ComponentProps<typeof Sidebar>;

export function AppSidebar({ ...props }: AppSidebarProps) {
  const { persona } = usePersona();
  const { openDialog } = useRegisterChildDialog();
  const [parentId, setParentId] = React.useState<number | null>(1);
  const [isHydrated, setIsHydrated] = React.useState(false);

  React.useEffect(() => {
    const storedParentId =
      persona.role === "parent" ? (persona.id ?? null) : null;
    if (storedParentId) {
      setParentId(storedParentId);
    }
    setIsHydrated(true);
  }, []);

  const navigate = useNavigate();
  const [credits] = React.useState(0);
  const [showBuyCreditsDialog, setShowBuyCreditsDialog] = React.useState(false);

  // If the parent has 0 credits, then they will be redirected to the Buy Credit Dialog. Otherwise, they will continue to the next step of the booking flow.
  // Change the navigate parameter to the correct url here
  const handleBookingFlow = () => {
    if (credits === 0) {
      setShowBuyCreditsDialog(true);
    } else {
      navigate("/booking-dialog");
    }
  };

  // This function will handle closing the dialog when the user leaves without buying credits.
  const handleCloseDialog = (open: boolean) => {
    setShowBuyCreditsDialog(open);
    if (!open) {
      navigate("/");
    }
  };
  return (
    <Sidebar collapsible="offcanvas" {...props}>
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton
              asChild
              className="data-[slot=sidebar-menu-button]:!p-1.5"
            >
              <a href="/" className="flex items-center">
                <span className="p-4 text-lg font-semibold text-green-500 min-w-8 duration-200 ease-linear">
                  Dallas Learning Central
                </span>
              </a>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>

      <SidebarContent>
        <NavMain
          items={data.navMain
            .filter((item) => item.roles.includes(persona.role))
            .map((item) => {
              if (item.title === "Tutor Dashboard") {
                return {
                  ...item,
                  url: "/tutors/dashboard",
                  onClick: () => navigate("/tutors/dashboard"),
                };

              }
              if (item.title === "Parent Profile") {
                return {
                  ...item,
                  url: "/parents",
                  onClick: () => navigate("/parents"),
                };
              }
              if (item.title === "Child's Progress") {
                return {
                  ...item,
                  url: `/parents/${parentId}/progress`,
                  onClick: () => {
                    if (!isHydrated) {
                      console.warn(
                        "Cannot navigate: parentId or childId is missing from local storage",
                      );
                      return;
                    }
                    navigate(`/parents/${parentId}/progress`);
                  },
                };
              }
              if (item.title === "Book a Session") {
                return {
                  ...item,
                  onClick: handleBookingFlow,
                  className:
                    "text-sidebar-foreground hover:bg-sidebar-accent hover:text-sidebar-accent-foreground",
                };
              }
              if (item.title === "Register a Child") {
                return {
                  ...item,
                  url: "/children/register",
                  onClick: () => openDialog(),
                };
              }


              if (item.title === "Register a Parent") {
                return {
                  ...item,
                  url: "/parents/register",
                  onClick: () => {
                    navigate("/parents/register");
                  },
                };
              }

              return item;
            })}
        />
      </SidebarContent>

      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
      {parentId !== null && (
        <BuyCreditsDialog
          parentId={parentId}
          open={showBuyCreditsDialog}
          onOpenChange={handleCloseDialog}
        />
      )}
    </Sidebar>
  );
}

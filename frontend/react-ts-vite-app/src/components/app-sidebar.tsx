import * as React from "react";
import {
  IconCreditCard,
  IconChartBar,
  IconDashboard,
  // IconDatabase,
  // IconFileWord,
  IconFolder,
  IconHelp,
  IconInnerShadowTop,
  IconListDetails,
  // IconReport,
  IconSearch,
  IconSettings,
  IconUserPlus,
  IconShieldCheck,
  IconUsers,
} from "@tabler/icons-react";

// import { NavDocuments } from "@/components/nav-documents"
import { NavMain } from "@/components/nav-main";
import { NavSecondary } from "@/components/nav-secondary";
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
import type { Persona } from "@/context/PersonaContext";
import BuyCreditsDialog from "@/pages/BuyCreditsDialog";

const data = {
  user: {
    name: "Learning Central",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  navMain: [
    {
      title: "Parent Dashboard",
      url: "/parents/:parentId",
      icon: IconDashboard,
      roles: ["parent"] as Persona[],
    },
    {
      title: "Tutor Dashboard",
      url: "/tutors/:tutorId/dashboard",
      icon: IconDashboard,
      roles: ["tutor"] as Persona[],
    },
    {
      title: "Admin Dashboard",
      url: "/admin",
      icon: IconShieldCheck,
      roles: ["admin"] as Persona[],
    },
    {
      title: "Students",
      url: "/students",
      icon: IconListDetails,
      roles: ["tutor", "admin"] as Persona[],
    },
    {
      title: "Session Review",
      url: "/session-review",
      icon: IconListDetails,
      roles: [] as Persona[],
    },
    {
      title: "Child's Progress",
      url: "/parents/:parentId/children/:childId/progress",
      icon: IconChartBar,
      roles: ["parent", "admin", "tutor"] as Persona[],
    },
    {
      title: "Book a Session",
      url: "/tutors",
      icon: IconFolder,
      roles: [] as Persona[],
    },
    {
      title: "Buy Credits",
      url: "#",
      icon: IconCreditCard,
      roles: ["parent"] as Persona[],
    },
    {
      title: "Register a Child",
      url: "/children/register",
      icon: IconUsers,
      roles: ["parent", "admin"] as Persona[],
    },
    {
      title: "Register a Parent",
      url: "/parents/register",
      icon: IconUserPlus,
      roles: ["admin"] as Persona[],
    },
  ],
  navSecondary: [
    {
      title: "Settings",
      url: "/settings",
      icon: IconSettings,
      roles: ["parent", "admin", "tutor"] as Persona[],
    },
    {
      title: "Get Help",
      url: "/help",
      icon: IconHelp,
      roles: ["parent", "admin", "tutor"] as Persona[],
    },
    {
      title: "Search",
      url: "/search",
      icon: IconSearch,
      roles: ["parent", "admin", "tutor"] as Persona[],
    },
  ],
  // documents: [
  //   {
  //     name: "Data Library",
  //     url: "#",
  //     icon: IconDatabase,
  //   },
  //   {
  //     name: "Reports",
  //     url: "#",
  //     icon: IconReport,
  //   },
  //   {
  //     name: "Word Assistant",
  //     url: "#",
  //     icon: IconFileWord,
  //   },
  // ],
};

type AppSidebarProps = React.ComponentProps<typeof Sidebar> & {
  onRegisterChildClick?: () => void;
};

export function AppSidebar({
  onRegisterChildClick,
  ...props
}: AppSidebarProps) {
  const { persona } = usePersona();
  const [tutorId, setTutorId] = React.useState<string | null>(null);
  const [childId, setChildId] = React.useState<number | null>(null);
  const [parentId, setParentId] = React.useState<number | null>(null);
  const [isHydrated, setIsHydrated] = React.useState(false);

  React.useEffect(() => {
    const storedTutorId = localStorage.getItem("tutorId");
    if (storedTutorId) {
      setTutorId(storedTutorId);
    }
    const storeParentId = localStorage.getItem("parentId");
    if (storeParentId) {
      setParentId(parseInt(storeParentId, 10));
    }
    const storedChildId = localStorage.getItem("childId");
    if (storedChildId) {
      setChildId(parseInt(storedChildId));
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

  const handleBuyCreditsClick = () => {
    setShowBuyCreditsDialog(true);
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
              <a href="/dashboard">
                <IconInnerShadowTop className="!size-5" />
                <span className="text-base font-semibold">
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
            .filter((item) => item.roles.includes(persona))
            .map((item) => {
              if (item.title === "Tutor Dashboard") {
                if (tutorId) {
                  return {
                    ...item,
                    url: `/tutors/${tutorId}/dashboard`,
                    onClick: () => navigate(`/tutors/${tutorId}/dashboard`),
                  };
                }
              }
              if (item.title === "Parent Dashboard") {
                return {
                  ...item,
                  url: `/parents/${parentId}`,
                  onClick: () => navigate(`/parents/${parentId}`),
                };
              }
              if (item.title === "Child's Progress") {
                return {
                  ...item,
                  url:
                    parentId && childId
                      ? `/parents/${parentId}/children/${childId}/progress`
                      : "#",
                  onClick: () => {
                    if (!isHydrated) {
                      console.warn(
                        "Cannot navigate: parentId or childId is missing from local storage",
                      );
                      return;
                    }
                    navigate(
                      `/parents/${parentId}/children/${childId}/progress`,
                    );
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

              if (item.title === "Buy Credits") {
                return { ...item, onClick: handleBuyCreditsClick };
              }

              if (item.title === "Register a Child") {
                return {
                  ...item,
                  url: "children/register",
                  onClick: () => {
                    onRegisterChildClick?.();
                  },
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

              if (item.title === "") {
                return {
                  ...item,
                  url: "children/register",
                  onClick: () => {
                    onRegisterChildClick?.();
                  },
                };
              }

              return item;
            })}
        />
        <NavSecondary
          items={data.navSecondary.filter((item) =>
            item.roles.includes(persona),
          )}
          className="mt-auto"
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

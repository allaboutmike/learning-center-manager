import * as React from "react";
import {
  IconCamera,
  IconChartBar,
  IconDashboard,
  // IconDatabase,
  IconFileAi,
  IconFileDescription,
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

const data = {
  user: {
    name: "Learning Central",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  navMain: [
    {
      title: "Parent Dashboard",
      url: "/dashboard",
      icon: IconDashboard,
      roles: ["parent", "admin", "tutor"] as Persona[],
    },
    {
      title: "Students",
      url: "/students",
      icon: IconListDetails,
      roles: ["parent", "admin"] as Persona[],
    },
    {
      title: "Child's Progress",
      url: "/progress",
      icon: IconChartBar,
      roles: ["parent", "admin", "tutor"] as Persona[],
    },
    {
      title: "Book a Session",
      url: "#",
      icon: IconFolder,
      roles: ["parent"] as Persona[],
    },
    {
      title: "Register a Child",
      url: "/children/register",
      icon: IconUsers,
      roles: ["parent", "admin"] as Persona[],
    },
    {
      title: "Register Parent",
      url: "/parents/register",
      icon: IconUserPlus,
      roles: ["admin"] as Persona[],
    },
    {
      title: "Admin Dashboard",
      url: "/admin",
      icon: IconShieldCheck,
      roles: ["admin"] as Persona[],
    },
  ],
  navClouds: [
    {
      title: "Capture",
      icon: IconCamera,
      isActive: true,
      url: "#",
      items: [
        {
          title: "Active Proposals",
          url: "#",
        },
        {
          title: "Archived",
          url: "#",
        },
      ],
    },
    {
      title: "Proposal",
      icon: IconFileDescription,
      url: "#",
      items: [
        {
          title: "Active Proposals",
          url: "#",
        },
        {
          title: "Archived",
          url: "#",
        },
      ],
    },
    {
      title: "Prompts",
      icon: IconFileAi,
      url: "#",
      items: [
        {
          title: "Active Proposals",
          url: "#",
        },
        {
          title: "Archived",
          url: "#",
        },
      ],
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

  const navigate = useNavigate();

  const [credits, setCredits] = React.useState(0);
  const [showBuyCreditsDialog, setShowBuyCreditsDialog] = React.useState(false);

  // If the parent has 0 credits, then they will be redirected to the Buy Credit Dialog. Otherwise, they will continue to the next step of the booking flow.
  const handleBookingFlow = () => {
    if (credits === 0) {
      setShowBuyCreditsDialog(true);
    } else {
      navigate("/booking-dialog");
    }
  };

  // This function will handle the successful purchase of the credit if the parent has to buy credits.
  const handlePurchaseSuccess = () => {
    setCredits((prev) => prev + 5);
    setShowBuyCreditsDialog(false);
  };

  // This function will handle closing the dialog when the user leaves without buying credits.
  const handleCloseDialog = () => {
    setShowBuyCreditsDialog(false);
    navigate("/");
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
                  url: "#",
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

      {/* Mock Dialog Component */}
      {showBuyCreditsDialog && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
          <div className="bg-white p-6 rounded-lg shadow-xl max-w-sm w-full">
            <h3 className="text-lg font-bold mb-4">Buy Credits</h3>
            <p className="mb-6">
              You have 0 credits. Please purchase more to book a session.
            </p>
            <div className="flex flex-col gap-2">
              <button
                onClick={handlePurchaseSuccess}
                className="bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
              >
                Buy 5 Credits
              </button>
              <button
                onClick={handleCloseDialog}
                className="bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
              >
                Cancel & Go to Dashboard
              </button>
            </div>
          </div>
        </div>
      )}
    </Sidebar>
  );
}

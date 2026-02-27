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

const data = {
  user: {
    name: "Learning Central",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  navMain: [
    {
      title: "Parent Dashboard",
      url: "#",
      icon: IconDashboard,
    },
    {
      title: "Students",
      url: "#",
      icon: IconListDetails,
    },
    {
      title: "Child's Progress",
      url: "#",
      icon: IconChartBar,
    },
    {
      title: "Book a Session",
      url: "#",
      icon: IconFolder,
    },
    {
      title: "Register a Child",
      url: "#",
      icon: IconUsers,
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
      url: "#",
      icon: IconSettings,
    },
    {
      title: "Get Help",
      url: "#",
      icon: IconHelp,
    },
    {
      title: "Search",
      url: "#",
      icon: IconSearch,
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

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
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
        <NavMain items={data.navMain} onItemClick={handleBookingFlow} />
        <NavSecondary items={data.navSecondary} className="mt-auto" />
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

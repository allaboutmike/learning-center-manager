import { Outlet } from "react-router-dom"
import { AppSidebar } from "@/components/app-sidebar"
import { GuestSidebar } from "@/components/guest-sidebar"
import { SiteHeader } from "@/components/site-header"
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar"
import { usePersona } from "@/context/usePersona"

export default function DashboardLayout() {
  const { persona } = usePersona()

  return (
    <SidebarProvider>
      {persona === "guest" ? (
        <GuestSidebar variant="inset" />
      ) : (
        <AppSidebar variant="inset" />
      )}
      <SidebarInset>
        <SiteHeader />
        <div className="p-6">
          <Outlet />
        </div>
      </SidebarInset>
    </SidebarProvider>
  )
}

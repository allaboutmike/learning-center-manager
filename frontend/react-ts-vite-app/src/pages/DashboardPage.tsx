import { Outlet } from "react-router-dom"
import { AppSidebar } from "@/components/app-sidebar"
import { GuestSidebar } from "@/components/guest-sidebar"
import { SiteHeader } from "@/components/site-header"
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar"
import { usePersona } from "@/context/usePersona"
import RegisterChildModal from "@/components/RegisterChildModal"
import {
  RegisterChildDialogProvider,
  useRegisterChildDialog,
} from "@/context/RegisterChildDialogContext"

function DashboardPageContent() {
  const { persona } = usePersona()
  const { isOpen, setIsOpen } = useRegisterChildDialog()

  return (
    <SidebarProvider>
      {persona.role === "guest" ? (
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

      <RegisterChildModal
        open={isOpen}
        onOpenChange={setIsOpen}
        parentId={persona.id ?? 1}
        onChildCreated={() => { }}
      />
    </SidebarProvider>
  )
}

export default function DashboardPage() {
  return (
    <RegisterChildDialogProvider>
      <DashboardPageContent />
    </RegisterChildDialogProvider>
  )
}
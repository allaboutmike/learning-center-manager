import * as React from "react"

type RegisterChildDialogContextType = {
  isOpen: boolean
  openDialog: () => void
  closeDialog: () => void
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>
}

const RegisterChildDialogContext =
  React.createContext<RegisterChildDialogContextType | null>(null)

export function RegisterChildDialogProvider({
  children,
}: {
  children: React.ReactNode
}) {
  const [isOpen, setIsOpen] = React.useState(false)

  const openDialog = () => setIsOpen(true)
  const closeDialog = () => setIsOpen(false)

  return (
    <RegisterChildDialogContext.Provider
      value={{ isOpen, openDialog, closeDialog, setIsOpen }}
    >
      {children}
    </RegisterChildDialogContext.Provider>
  )
}

export function useRegisterChildDialog() {
  const context = React.useContext(RegisterChildDialogContext)

  if (!context) {
    throw new Error(
      "useRegisterChildDialog must be used within RegisterChildDialogProvider",
    )
  }

  return context
}
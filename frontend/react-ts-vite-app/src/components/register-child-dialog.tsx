import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog"

type RegisterChildDialogProps = {
  open: boolean
  onOpenChange: (open: boolean) => void
}

export function RegisterChildDialog({
  open,
  onOpenChange,
}: RegisterChildDialogProps) {
  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Register Child</DialogTitle>
        </DialogHeader>

        {/* paste the existing child registration form fields here */}
      </DialogContent>
    </Dialog>
  )
}
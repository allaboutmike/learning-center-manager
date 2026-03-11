import { useState, type ChangeEvent } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { useLearningCenterPatch } from "@/hooks/useLearningCenterAPI";
import type { Parent } from "@/types/parents";

interface DialogProps {
  parentId: number;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
}

export default function BuyCreditsDialog({ parentId, open, onOpenChange }: DialogProps) {
  const [credits, setCredits] = useState<number>(1);

  const handleCreditsChange = (event: ChangeEvent<HTMLInputElement>) => {
    const stringValue = event.target.value;
    const numberValue = parseInt(stringValue, 10);

    if (isNaN(numberValue)) {
      setCredits(1);
    } else {
      setCredits(numberValue);
    }
  };

  const increaseCreditsOnClick = () => {
    setCredits((prev) => prev + 1);
  };

  const decreaseCreditsOnClick = () => {
    setCredits((prev) => Math.max(1, prev - 1));
  };

  const patch = useLearningCenterPatch();

  const postPurchasedCredits = async () => {
    try {
      await patch<Parent>(`/api/parents/${parentId}`, { credits: credits });
      onOpenChange?.(false);
      window.location.reload();
    } catch (error) {
      console.error("Error purchasing credits:", error);
      alert("Purchase failed. Please try again.");
    }
  };

  return (
    <>
      <Dialog open={open ?? false} onOpenChange={onOpenChange ?? (() => {})}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Buy Credits</DialogTitle>
            <DialogDescription>
              Please enter the number of credits you would like to buy
            </DialogDescription>
          </DialogHeader>

          <main className="flex items-center justify-center gap-1.5 my-4">
            <Button
              disabled={credits === 1}
              className="bg-green-500 text-white hover:bg-green-600 h-10 w-10 disabled:bg-gray-300 disabled:text-gray-500 disabled:cursor-not-allowed"
              onClick={decreaseCreditsOnClick}
            >
              -
            </Button>

            <input
              id="credits"
              type="number"
              readOnly
              min="1"
              value={credits}
              max="15"
              className="text-center w-10 font-medium bg-transparent border-0 focus:outline-none"
              onChange={handleCreditsChange}
            />

            <Button
              disabled={credits === 15}
              className="bg-green-500 text-white hover:bg-green-600 h-10 w-10"
              onClick={increaseCreditsOnClick}
            >
              +
            </Button>
          </main>

          <Button
            onClick={postPurchasedCredits}
            className="bg-green-500 text-white hover:bg-green-600"
          >
            Buy {credits} credit{credits != 1 ? "s" : ""}
          </Button>

          <Button variant="outline" onClick={() => onOpenChange?.(false)}>
            Cancel
          </Button>
        </DialogContent>
      </Dialog>
    </>
  );
}
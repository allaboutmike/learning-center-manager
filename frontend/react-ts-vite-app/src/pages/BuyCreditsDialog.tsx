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
  const [credits, setCredits] = useState<number>(0);

  const handleCreditsChange = (event: ChangeEvent<HTMLInputElement>) => {
    const stringValue = event.target.value;
    const numberValue = parseInt(stringValue, 10);
    if (isNaN(numberValue)) {
      setCredits(0);
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
      await patch<Parent>(
        `/api/parents/${parentId}`,
        { credits: credits }
      );

      window.location.href = `/`;
    } catch (error) {
      console.error("Error purchasing credits:", error);
      alert("Purchase failed. Please try again.");
    }
  }

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
          <main>
            <Button onClick={decreaseCreditsOnClick}>{`${"-"}`}</Button>
            <input
              id="credits"
              type="number"
              readOnly
              min="0"
              value={credits}
              max="15"
              onChange={handleCreditsChange}
            />
            <Button
              disabled={credits === 15}
              onClick={increaseCreditsOnClick}
            >{`${"+"}`}</Button>
          </main>
          <Button onClick={postPurchasedCredits}>
            Buy {credits} credit{credits != 1 ? "s" : ""}
          </Button>
        </DialogContent>
      </Dialog>
    </>
  );
}

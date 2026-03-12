import { useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import type { ChildResponse } from "../types/parents";
import { useLearningCenterPost } from "../hooks/useLearningCenterAPI";

type RegisterChildModalProps = {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  parentId: number;
  onChildCreated: (child: ChildResponse) => void;
};

const SUBJECT_OPTIONS = ["Math", "Science", "English", "Reading"];

export default function RegisterChildModal({
  open,
  onOpenChange,
  parentId,
  onChildCreated,
}: RegisterChildModalProps) {
  const [name, setName] = useState("");
  const [gradeLevel, setGradeLevel] = useState("");
  const [selectedSubjects, setSelectedSubjects] = useState<string[]>([]);
  const [errors, setErrors] = useState<{
    name?: string;
    gradeLevel?: string;
    subjects?: string;
    submit?: string;
  }>({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const post = useLearningCenterPost();

  const resetForm = () => {
    setName("");
    setGradeLevel("");
    setSelectedSubjects([]);
    setErrors({});
    setIsSubmitting(false);
    setSuccessMessage("");
  };

  const toggleSubject = (subject: string) => {
    setErrors((prev) => ({ ...prev, subjects: undefined, submit: undefined }));

    setSelectedSubjects((prev) => {
      if (prev.includes(subject)) {
        return prev.filter((item) => item !== subject);
      }

      if (prev.length >= 2) {
        setErrors((current) => ({
          ...current,
          subjects: "Select 1–2 subjects",
        }));
        return prev;
      }

      return [...prev, subject];
    });
  };

  const validateForm = () => {
    const nextErrors: {
      name?: string;
      gradeLevel?: string;
      subjects?: string;
    } = {};

    if (!name.trim()) {
      nextErrors.name = "Child name is required";
    }

    if (!gradeLevel.trim()) {
      nextErrors.gradeLevel = "Grade level is required";
    }

    if (selectedSubjects.length < 1 || selectedSubjects.length > 2) {
      nextErrors.subjects = "Select 1–2 subjects";
    }

    setErrors(nextErrors);
    return Object.keys(nextErrors).length === 0;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;

    setIsSubmitting(true);
    setErrors({});
    setSuccessMessage("");

    try {
      const createdChild = await post<ChildResponse>(
        `/api/parents/${parentId}/children`,
        {
          name: name.trim(),
          gradeLevel: Number(gradeLevel),
        }
      );

      setTimeout(() => {
        onChildCreated(createdChild);
        resetForm();
        onOpenChange(false);
      });
    } catch (err) {
      console.error(err);
      setErrors({
        submit: "Failed to register child",
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Dialog
      open={open}
      onOpenChange={(nextOpen) => {
        if (!nextOpen) {
          resetForm();
        }
        onOpenChange(nextOpen);
      }}
    >
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Register Child</DialogTitle>
        </DialogHeader>

        <div className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="name">Child Name</Label>
            <Input
              id="name"
              value={name}
              onChange={(e) => {
                setName(e.target.value);
                setErrors((prev) => ({
                  ...prev,
                  name: undefined,
                  submit: undefined,
                }));
              }}
              placeholder="Enter child name"
            />
            {errors.name && (
              <p className="text-red-500 text-sm mt-1">{errors.name}</p>
            )}
          </div>

          <div className="space-y-2">
            <Label htmlFor="grade">Grade Level</Label>
            <select
              id="grade"
              className="h-10 w-full rounded-md border border-input bg-background px-3 text-sm"
              value={gradeLevel}
              onChange={(e) => {
                setGradeLevel(e.target.value);
                setErrors((prev) => ({
                  ...prev,
                  gradeLevel: undefined,
                  submit: undefined,
                }));
              }}
            >
              <option value="">Select grade level</option>
              <option value="0">Kindergarten</option>
              <option value="1">Grade 1</option>
              <option value="2">Grade 2</option>
              <option value="3">Grade 3</option>
              <option value="4">Grade 4</option>
              <option value="5">Grade 5</option>
              <option value="6">Grade 6</option>
              <option value="7">Grade 7</option>
              <option value="8">Grade 8</option>
              <option value="9">Grade 9</option>
              <option value="10">Grade 10</option>
              <option value="11">Grade 11</option>
              <option value="12">Grade 12</option>
            </select>

            {errors.gradeLevel && (
              <p className="text-red-500 text-sm mt-1">{errors.gradeLevel}</p>
            )}
          </div>

          <div className="space-y-2">
            <Label>Subjects</Label>
            <div className="mt-2 space-y-2">
              {SUBJECT_OPTIONS.map((subject) => (
                <label key={subject} className="flex items-center gap-2 text-sm">
                  <input
                    type="checkbox"
                    checked={selectedSubjects.includes(subject)}
                    onChange={() => toggleSubject(subject)}
                  />
                  <span>{subject}</span>
                </label>
              ))}
            </div>
            {errors.subjects && (
              <p className="text-red-500 text-sm mt-1">{errors.subjects}</p>
            )}
          </div>

          {errors.submit && (
            <p className="text-red-500 text-sm">{errors.submit}</p>
          )}

          {successMessage && (
            <p className="text-green-600 text-sm font-medium">
              {successMessage}
            </p>
          )}
        </div>

        <DialogFooter>
          <Button
            variant="outline"
            onClick={() => {
              resetForm();
              onOpenChange(false);
            }}
            disabled={isSubmitting}
          >
            Cancel
          </Button>
          <Button className="bg-green-600 hover:bg-green-700 text-white px-6" onClick={handleSubmit} disabled={isSubmitting}>
            {isSubmitting ? "Creating..." : "Register Child"}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
import { Badge } from "@/components/ui/badge"
import {
  Card,
  CardAction,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

interface CardProfileProps {
  name: string;
  profilePictureUrl?: string;
  minGradeLevel?: number;
  maxGradeLevel?: number;
  tutorSummary?: string;
  avgRating?: number;
}
export function CardProfile(props: CardProfileProps) {
  return (
    <Card className="relative mx-auto w-full max-w-sm pt-0">
      <div className="absolute inset-0 z-30 aspect-video bg-black/35" />
      <img
        src={props.profilePictureUrl || "https://via.placeholder.com/400x200?text=Tutor+Profile+Picture"}
        alt="Profile Picture of Tutor"
        className="relative z-20 aspect-video w-full object-cover"
      />
      <CardHeader>
        <CardAction>
          <Badge variant="secondary"> Grades: {props.minGradeLevel} - {props.maxGradeLevel}</Badge>
        </CardAction>
        <CardTitle>{props.name}</CardTitle>
        <CardDescription>
          {props.tutorSummary || "This is a brief summary about the tutor. It can include their teaching style, subjects they specialize in, and any other relevant information that would help parents and students understand more about them."}
        </CardDescription>
      </CardHeader>
      <CardFooter>
        <Badge variant="secondary">Math</Badge>
        <Badge variant="secondary">Science</Badge>
        <Badge variant="secondary">English</Badge>
        <Badge variant="secondary">History</Badge>
      </CardFooter>
    </Card>
  )
}
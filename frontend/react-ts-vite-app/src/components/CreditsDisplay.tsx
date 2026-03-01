import { useLearningCenterAPI } from "@/hooks/useLearningCenterAPI"
export default function CreditsDisplay() {
    const parentId = 1;
    const parentCredits = useLearningCenterAPI<number>(`/api/parents/${parentId}/creditBalance`);

    if (!parentCredits) return null;
    console.log(parentCredits);


    return (
        <button
            onClick={() => window.location.href = "/buy-credits"}
            aria-label={`Buy credits - you currently have ${parentCredits} credits`}
            title="Click to buy more credits"
            className="flex items-center gap-2 px-4 py-2 bg-green-100 text-green-800 rounded-md hover:bg-green-200 transition-colors"
        >
            <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                className="w-6 h-6 animate-spin-coin"
            >
                <circle cx="12" cy="12" r="11" fill="#FACC15" stroke="#CA8A04" strokeWidth="1.5" />
                <circle cx="12" cy="12" r="8" fill="#FDE047" stroke="#CA8A04" strokeWidth="1" />
                <text
                    x="12"
                    y="16.5"
                    textAnchor="middle"
                    fontSize="11"
                    fontWeight="bold"
                    fill="#854D0E"
                    fontFamily="Arial"
                >
                    $
                </text>
            </svg>
            Credits: {parentCredits} <span className="text-xs underline ml-1">Buy more</span>
        </button>
    );
}
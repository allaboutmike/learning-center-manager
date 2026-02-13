import { useEffect, useState } from "react";

export function useLearningCenterAPI<T>(url: string) {
    const [data, setData] = useState<T | null>(null);

    useEffect(() => {
        fetch(url)
            .then((res) => res.json())
            .then((json) => setData(json as T));
    }, [url]);

    return data;
}

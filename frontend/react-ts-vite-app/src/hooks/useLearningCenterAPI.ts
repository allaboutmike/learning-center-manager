import { useEffect, useState } from "react";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export function useLearningCenterAPI<T>(url: string) {
    const [data, setData] = useState<T | null>(null);

    useEffect(() => {
        fetch(`${API_BASE_URL}${url}`)
            .then((res) => res.json())
            .then((json) => setData(json as T));
    }, [url]);

    return data;
}

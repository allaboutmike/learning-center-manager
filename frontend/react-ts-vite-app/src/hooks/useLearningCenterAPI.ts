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

export function useLearningCenterPost() {
    return async <T, B = unknown>(url: string, body?: B): Promise<T> => {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: body ? JSON.stringify(body) : undefined,
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json() as Promise<T>;
    };
}
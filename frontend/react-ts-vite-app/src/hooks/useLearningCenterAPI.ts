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

export function useLearningCenterPost() {
    return async <T, B = unknown>(url: string, body?: B): Promise<T> => {
        const response = await fetch(`${API_BASE_URL}${url}`, {
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
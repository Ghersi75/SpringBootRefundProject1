import { useEffect, useState } from "react";
import { IsUsernameAvailableType } from "../Types/APIReturnTypes";

// https://stackoverflow.com/questions/77123890/debounce-in-reactjs
export const useDebounce = (username: string, delay: number) => {
  const [isAvailable, setIsAvailable] = useState<boolean | null>(null);
  useEffect(() => {
    const handler = setTimeout(async () => {
      await fetch(`http://localhost:8080/user/check-username?username=${username}`)
        .then((res) => res.json())
        .then((data: IsUsernameAvailableType) => setIsAvailable(data.isAvailable));
      }, delay);
      
      return (() => {
        clearTimeout(handler);
      })
  }, [username]);

  return isAvailable;
}
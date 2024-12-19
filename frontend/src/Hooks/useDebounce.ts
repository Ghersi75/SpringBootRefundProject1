import { useEffect, useState } from "react";
import axios from "axios";

// https://stackoverflow.com/questions/77123890/debounce-in-reactjs
export const useDebounce = (username: string, delay: number) => {
  const [isAvailable, setIsAvailable] = useState<boolean | null>(null);
  useEffect(() => {
    if (username == "") {
      return;
    }
    // When it's null its being checked, uselful for sign up component
    setIsAvailable(null);
    const handler = setTimeout(async () => {
      await axios.get(`http://localhost:8080/user/check-username?username=${username}`)
        .then((res) => setIsAvailable(res.data.isAvailable));
    }, delay);

    return (() => {
      clearTimeout(handler);
    })
  }, [username]);

  return isAvailable;
}
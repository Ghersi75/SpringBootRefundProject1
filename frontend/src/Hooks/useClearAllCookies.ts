import { useCookies } from "react-cookie";


export default function useClearAllCookies() {
  const [cookies, _, removeCookie] = useCookies();

  const clearCookies = () => {
    for (const cookieName in cookies) {
      removeCookie(cookieName);
    }
  }

  return { clearCookies };
}
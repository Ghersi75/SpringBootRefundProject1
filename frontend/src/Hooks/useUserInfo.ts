import { useContext } from "react";
import { UserContext } from "../Context/UserContext";

export const useUserInfo = () => {
  const content = useContext(UserContext);
  if (!content) {
    throw new Error("UserContext must be placed within a UserProvider component")
  }

  return { ...content };
}
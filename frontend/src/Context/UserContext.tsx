import { createContext, useEffect, useState } from "react";
import { ChildrenPropType, UserContextType, UserInfoType } from "../Types/ContextTypes";
import { useCookies } from "react-cookie";

export const UserContext = createContext<UserContextType | null>(null);

// Fancy name for a simple function
// Currently spaces in username are being replaced with + to store them properly as cookies
// This is just annoying for username display
// Technically if a user includes a + in their name it would just be changed to a space as well
// TODO: Change username constraints to alphanumerical only in the backend
const decodeUsername = (username: string) => {
  return username.replace("+", " ");
}

export const UserProvider = ({ children }: ChildrenPropType) => {
  const [userCookie, _] = useCookies(["userInfo"]);
  const decodedUsername = decodeUsername(userCookie.userInfo.username);
  const [userInfo, setUserInfo] = useState<UserInfoType | null>({ ...userCookie.userInfo, username: decodedUsername });

  // Update if cookie changes or is removed
  useEffect(() => {
    setUserInfo({ ...userCookie.userInfo, username: decodedUsername });
  }, [userCookie.userInfo])

  return (
    <UserContext.Provider value={{ userInfo, setUserInfo }}>
      { children }
    </UserContext.Provider>
  )
}
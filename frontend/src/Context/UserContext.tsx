import { createContext, useState } from "react";
import { ChildrenPropType, UserContextType, UserInfoType } from "../Types/ContextTypes";
import { useCookies } from "react-cookie";

export const UserContext = createContext<UserContextType | null>(null);

export const UserProvider = ({ children }: ChildrenPropType) => {
  const [userCookie, _] = useCookies(["userInfo"]);
  const [userInfo, setUserInfo] = useState<UserInfoType | null>(userCookie.userInfo);
  return (
    <UserContext.Provider value={{ userInfo, setUserInfo }}>
      { children }
    </UserContext.Provider>
  )
}
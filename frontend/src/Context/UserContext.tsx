import { createContext, useState } from "react";
import { ChildrenPropType, UserContextType, UserInfoType } from "../Types/ContextTypes";

export const UserContext = createContext<UserContextType | null>(null);

export const UserProvider = ({ children }: ChildrenPropType) => {
  const [userInfo, setUserInfo] = useState<UserInfoType | null>(null);
  return (
    <UserContext.Provider value={{ userInfo, setUserInfo }}>
      { children }
    </UserContext.Provider>
  )
}
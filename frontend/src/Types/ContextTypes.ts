import { ReactNode } from "react";

export interface ChildrenPropType {
  children: ReactNode
}

export interface UserInfoType {
  username: string,
  email: string,
  userId: number,
  userRole: "EMPLOYEE" | "MANAGER",
  profilePicLink: string | null
}

export interface UserContextType {
  userInfo: UserInfoType | null,
  setUserInfo: React.Dispatch<React.SetStateAction<UserInfoType | null>>
}

export interface UserInfoCookieType {
  user: UserInfoType
}
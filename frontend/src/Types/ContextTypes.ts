import { ReactNode } from "react";

export interface ChildrenPropType {
  children: ReactNode
}

export interface UserInfoType {
  username: string,
  email: string,
  profilePicLink: string | null
}

export interface UserContextType {
  userInfo: UserInfoType | null,
  setUserInfo: React.Dispatch<React.SetStateAction<UserInfoType | null>>
}

export interface UserInfoCookieType {
  user: UserInfoType
}
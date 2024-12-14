import { ReactNode } from "react";

export interface ChildrenPropType {
  children: ReactNode
}

export interface UserInfoType {
  username: string,
  email: string
}

export interface UserContextType {
  userInfo: UserInfoType | null,
  setUserInfo: React.Dispatch<React.SetStateAction<UserInfoType | null>>
}
import { Navigate } from "react-router-dom";
import { useUserInfo } from "../../Hooks/useUserInfo";
import { ReactNode } from "react";

export default function RedirectIfLoggedIn({ children }: { children: ReactNode }) {
  const { userInfo } = useUserInfo();

  if (userInfo != null) {
    return <Navigate to="/" />
  }

  return children;
}
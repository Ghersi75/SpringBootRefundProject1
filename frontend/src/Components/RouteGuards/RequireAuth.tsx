import { ReactNode } from "react";
import { useUserInfo } from "../../Hooks/useUserInfo";
import { Navigate } from "react-router-dom";

export default function RequireAuth({ children }: { children: ReactNode }) {
  const { userInfo } = useUserInfo();

  if (userInfo == null) {
    return <Navigate to="/login" />
  }

  return children;
}
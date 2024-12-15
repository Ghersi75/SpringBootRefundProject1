import { useNavigate } from "react-router-dom";
import { useUserInfo } from "../Hooks/useUserInfo";
import { ChildrenPropType } from "../Types/ContextTypes";
import { useEffect, useState } from "react";

export default function LeaveIfLoggedIn({ children }: ChildrenPropType) {
  const { userInfo } = useUserInfo();
  const navigate = useNavigate();
  const [isChecking, setIsChecking] = useState(true);

  useEffect(() => {
    const timeout = setTimeout(() => {
      if (userInfo != null) {
        navigate("/");
      }
    }, 1000)
    
    return () => {
      clearTimeout(timeout)
    }

    setIsChecking(false);
  }, [userInfo])

  if (isChecking) {
    return <div className="grow bg-zinc-700 flex justify-center items-center text-white"> Loading... </div>;
  } else {
    return children;
  }
}
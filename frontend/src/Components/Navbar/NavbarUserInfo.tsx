import { useContext } from "react";
import { UserContext } from "../Context/UserContext";

export default function NavbarUserInfo() {
  const content = useContext(UserContext);
  
    if (!content) {
      throw new Error("Error");
    }
  
    const { userInfo } = content;
    if (!userInfo) {
      return;
    }
    const { username, email, profilePicLink } = userInfo;
  return(
    <div className="flex gap-4">
      <div className="flex flex-col justify-center">
        <h1> {username} </h1>
        <h1> {email} </h1>
      </div>
      <img src={profilePicLink ? profilePicLink : "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg"} className="w-[50px] rounded-full"/>
    </div>
  )
}
import { useUserInfo } from "../../Hooks/useUserInfo";
import useClearAllCookies from "../../Hooks/useClearAllCookies";

export default function NavbarUserInfo() {
  const { userInfo } = useUserInfo();
  const { clearCookies } = useClearAllCookies();

  if (userInfo == null) {
    return ;
  }

  const { username, email, userId, profilePicLink, userRole } = userInfo;
  return(
    <div className="flex items-center gap-4">
      <div className="flex flex-col justify-center">
        <h1> {`${username} { id: ${userId}, ${userRole.charAt(0).toUpperCase()}${userRole.substring(1).toLowerCase()} } `} </h1>
        <h1> {email} </h1>
      </div>
      <div>
        <img src={profilePicLink ? profilePicLink : "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg"} className="w-[50px] h-[50px] rounded-full"/>
        <h1 className="text-xs hover:underline hover:cursor-pointer text-blue-400" onClick={clearCookies}> Sign Out </h1>
      </div>
    </div>
  )
}
import { useUserInfo } from "../../Hooks/useUserInfo";

export default function NavbarUserInfo() {
  const { userInfo } = useUserInfo();

  if (userInfo == null) {
    return ;
  }

  const { username, email, userId, profilePicLink } = userInfo;
  return(
    <div className="flex items-center gap-4">
      <div className="flex flex-col justify-center">
        <h1> {`${username} { id: ${userId} }`} </h1>
        <h1> {email} </h1>
      </div>
      <img src={profilePicLink ? profilePicLink : "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg"} className="w-[50px] h-[50px] rounded-full"/>
    </div>
  )
}
import { Link } from "react-router-dom"
import NavbarUserInfo from "./NavbarUserInfo";
import { useUserInfo } from "../../Hooks/useUserInfo";

export default function Navbar() {
  const { userInfo } = useUserInfo();

  return(
    <nav className="h-[80px] min-w-svw bg-zinc-800 flex gap-4 py-4 px-8 justify-between items-center text-white">
      <h1> Logo </h1>
        {
          userInfo ?
          <NavbarUserInfo /> :
          <ul className="flex gap-8">
            <Link to="/login">
              <li> Login </li>
            </Link>
            <Link to="/signup">
              <li> Sign Up </li>
            </Link>
          </ul>
        }
    </nav>
  )
}
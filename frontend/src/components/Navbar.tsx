import { useContext } from "react"
import { Link } from "react-router-dom"
import { UserContext } from "../Context/UserContext"
import NavbarUserInfo from "./NavbarUserInfo";

export default function Navbar() {
  const content = useContext(UserContext);

  if (!content) {
    throw new Error("Error");
  }

  const { userInfo } = content;

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
import { useState } from "react";
import SignUp from "./SignUp";
import { useDebounce } from "../../Hooks/useDebounce";
import { SignUpReturnType } from "../../Types/APIReturnTypes";
import { useNavigate } from "react-router-dom";
import RedirectIfLoggedIn from "../RouteGuards/RedirectIfLoggedIn";

export default function SignUpManager() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const isUsernameAvailable = useDebounce(username, 1000);

  // TODO: Add some sort of loading if there's extra time
  const handleSignUp = async () => {
    await fetch("http://localhost:8080/user/signup", {
      method: "POST",
      body: JSON.stringify({
        email: email,
        username: username,
        password: password
      }),
      credentials: "include",
      headers: {
        "Content-Type": "application/json"
      }
    }).then((res) => res.json())
      .then((data: SignUpReturnType) => {
        // TODO: Add logic for failure
        if (data.success) {
          console.log(data);
          navigate("/");
        }
      })
  }

  return (
    <RedirectIfLoggedIn>
      <SignUp
        email={email}
        setEmail={setEmail}
        username={username}
        setUsername={setUsername}
        password={password}
        setPassword={setPassword}
        isUsernameAvailable={isUsernameAvailable}
        handleSignUp={handleSignUp} />
    </RedirectIfLoggedIn>
  )
}
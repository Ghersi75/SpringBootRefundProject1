import { useState } from "react";
import SignUp from "./SignUp";
import { useDebounce } from "../Hooks/useDebounce";

export default function SignUpManager() {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const isUsernameAvailable = useDebounce(username, 1000);
  
  return (
    <>
      <SignUp
        email={email}
        setEmail={setEmail}
        username={username}
        setUsername={setUsername}
        password={password}
        setPassword={setPassword}
        isUsernameAvailable={isUsernameAvailable}/>
    </>
  )
}
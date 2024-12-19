import { useState } from "react";
import SignUp from "./SignUp";
import { useDebounce } from "../../Hooks/useDebounce";
import { useNavigate } from "react-router-dom";
import RedirectIfLoggedIn from "../RouteGuards/RedirectIfLoggedIn";
import axios from "axios";

export default function SignUpManager() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const isUsernameAvailable = useDebounce(username, 1000);

  // TODO: Add some sort of loading if there's extra time
  const handleSignUp = async () => {
    setErrorMessage("");
    await axios.post("http://localhost:8080/user/signup", {
      email: email,
      username: username,
      password: password
    }, {
      withCredentials: true,
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then((res) => {
        if (res.data.success) {
          // console.log(res.data);
          navigate("/");
        }
      })
      .catch((err) => {
        setErrorMessage(err.response.data.error)
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
        errorMessage={errorMessage}
        handleSignUp={handleSignUp} />
    </RedirectIfLoggedIn>
  )
}
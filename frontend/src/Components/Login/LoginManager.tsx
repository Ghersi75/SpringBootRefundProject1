import { useState } from "react";
import Login from "./Login";
import RedirectIfLoggedIn from "../RouteGuards/RedirectIfLoggedIn";
import axios from "axios";

export default function LoginManager() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleLogin = async () => {
    await axios.post("http://localhost:8080/user/login", {
        email: email,
        password: password
      }, {
        headers: {
          "Content-Type": "application/json"
        },
        withCredentials: true
      })
      .then(res => {
        console.log(res.data);
        if (res.data.success == true) {
          // navigate(0);
          console.log("Done")
        }
      }).catch(err => {
        setErrorMessage(err.response.data.error)
      })
  }

  return (
    <RedirectIfLoggedIn>
      <Login
        email={email}
        setEmail={setEmail}
        password={password}
        setPassword={setPassword}
        handleLogin={handleLogin}
        errorMessage={errorMessage}
      />
    </RedirectIfLoggedIn>
  )
}
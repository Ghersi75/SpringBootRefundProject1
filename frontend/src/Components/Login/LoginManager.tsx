import { useState } from "react";
import Login from "./Login";
import RedirectIfLoggedIn from "../RouteGuards/RedirectIfLoggedIn";

export default function LoginManager() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    await fetch("http://localhost:8080/user/login", {
      method: "POST",
      body: JSON.stringify({
        email: email,
        password: password
      }),
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include"
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if ((data as any).success == true) {
          // navigate(0);
          console.log("Done")
        }
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
      />
    </RedirectIfLoggedIn>
  )
}
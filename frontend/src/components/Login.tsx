import { useState } from "react"
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [showPass, setShowPass] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  }

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  }

  const handleShowPassword = () => {
    setShowPass((prev) => !prev)
  }

  const handleLogin = async () => {
    await fetch("http://localhost:8080/user/login")
      .then(res => res.json())
      .then(data => { console.log(data); navigate("/"); })
  }

  return (
    <div className="grow flex justify-center items-center">
      <form className="bg-zinc-800 p-8 rounded-l flex flex-col gap-4" onSubmit={(e) => {e.preventDefault()}}>
        <label className="text-white"> Username: </label>
        <input className="bg-zinc-700 rounded p-2 text-white"  onChange={handleUsernameChange} value={username} />
        <label className="text-white"> Password: </label>
        <div className="relative">
          <input className="bg-zinc-700 rounded p-2 text-white" type={!showPass ? "password" : ""} onChange={handlePasswordChange} value={password}/>
          <div className="absolute right-0 top-1/2 -translate-y-1/2 right-4 text-white hover:underline cursor-pointer" onClick={handleShowPassword}> show </div>
        </div>
        <button className="bg-zinc-700 p-2 rounded text-white" onClick={handleLogin}> Log in </button>
      </form>
    </div>
  )
}
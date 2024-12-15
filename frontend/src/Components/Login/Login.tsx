import { useState } from "react"
// import { useNavigate } from "react-router-dom";
import LeaveIfLoggedIn from "../LeaveIfLoggedIn";
import { LoginFormPropsType } from "../../Types/UserFormTypes";


export default function Login({ email, setEmail, password, setPassword, handleLogin }: LoginFormPropsType) {
  const [showPass, setShowPass] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, setState: React.Dispatch<React.SetStateAction<string>>) => {
    setState(e.target.value);
  }

  const handleShowPassword = () => {
    setShowPass((prev) => !prev)
  }

  return (
    <LeaveIfLoggedIn>
      <div className="grow flex justify-center items-center">
        <form className="bg-zinc-800 p-8 rounded-l flex flex-col gap-4" onSubmit={(e) => {e.preventDefault()}}>
          <label className="text-white"> Email: </label>
          <input className="bg-zinc-700 rounded p-2 text-white"  onChange={(e) => { handleChange(e, setEmail) }} value={email} />
          <label className="text-white"> Password: </label>
          <div className="relative">
            <input className="bg-zinc-700 rounded p-2 text-white" type={!showPass ? "password" : ""} onChange={(e) => { handleChange(e, setPassword) }} value={password}/>
            <div className="absolute right-0 top-1/2 -translate-y-1/2 right-4 text-white hover:underline cursor-pointer" onClick={handleShowPassword}> show </div>
          </div>
          <button className="bg-zinc-700 p-2 rounded text-white" onClick={handleLogin}> Log in </button>
        </form>
      </div>
    </LeaveIfLoggedIn>
  )
}
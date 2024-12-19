import { useState } from "react"
import { LoginFormPropsType } from "../../Types/UserFormTypes";
import { useNavigate } from "react-router-dom";


export default function Login({ email, setEmail, password, setPassword, handleLogin }: LoginFormPropsType) {
  const [showPass, setShowPass] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, setState: React.Dispatch<React.SetStateAction<string>>) => {
    setState(e.target.value);
  }

  const handleShowPassword = () => {
    setShowPass((prev) => !prev)
  }

  return (
    <div className="grow flex justify-center items-center">
      <form className="bg-zinc-800 p-8 rounded-l flex flex-col gap-4" onSubmit={(e) => { e.preventDefault() }}>
        <label className="text-white"> Email: </label>
        <input className="bg-zinc-700 rounded p-2 text-white" onChange={(e) => { handleChange(e, setEmail) }} value={email} />
        <label className="text-white"> Password: </label>
        <div className="relative w-fit">
          <input className="bg-zinc-700 rounded p-2 text-white pr-16" type={!showPass ? "password" : ""} onChange={(e) => { handleChange(e, setPassword) }} value={password} />
          <div className="absolute right-0 top-1/2 -translate-y-1/2 right-4 text-white hover:underline cursor-pointer text-blue-500" onClick={handleShowPassword}>  {showPass ? "Hide" : "Show"}  </div>
        </div>
        <button className="bg-zinc-700 p-2 rounded text-white" onClick={handleLogin}> Log in </button>
        <p className="text-blue-600 text-sm"> Don't have an account? <br /> <span className="hover:underline hover:cursor-pointer" onClick={() => { navigate("/signup") }}> Sign up </span></p>
      </form>
    </div>
  )
}
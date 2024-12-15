import { useState } from "react"
import { SignUpFormPropsType } from "../../Types/UserFormTypes";

export default function SignUp({ email, setEmail, username, setUsername, password, setPassword, handleSignUp }: SignUpFormPropsType) {
  const [showPass, setShowPass] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, setState: React.Dispatch<React.SetStateAction<string>>) => {
    setState(e.target.value);
  }

  const handleShowPassword = () => {
    setShowPass((prev) => !prev)
  }

  return (
    <div className="grow flex justify-center items-center">
      <form className="bg-zinc-800 p-8 rounded-l flex flex-col gap-4" onSubmit={(e) => {e.preventDefault()}}>
        <label className="text-white"> Email: </label>
        <input className="bg-zinc-700 rounded p-2 text-white"  onChange={(e) => { handleChange(e, setEmail) }} value={email} />
        <label className="text-white"> Username: </label>
        <input className="bg-zinc-700 rounded p-2 text-white"  onChange={(e) => { handleChange(e, setUsername) }} value={username} />
        <label className="text-white"> Password: </label>
        <div className="relative">
          <input className="bg-zinc-700 rounded p-2 text-white" type={!showPass ? "password" : ""} onChange={(e) => { handleChange(e, setPassword) }} value={password}/>
          <div className="absolute right-0 top-1/2 -translate-y-1/2 right-4 text-white hover:underline cursor-pointer" onClick={handleShowPassword}> show </div>
        </div>
        <button className="bg-zinc-700 p-2 rounded text-white" onClick={handleSignUp}> Sign Up </button>
      </form>
    </div>
  )
}
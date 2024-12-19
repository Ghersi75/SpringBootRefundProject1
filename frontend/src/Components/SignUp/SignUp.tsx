import { useState } from "react"
import { SignUpFormPropsType } from "../../Types/UserFormTypes";
import { useNavigate } from "react-router-dom";
import spinner from "../../assets/ku66sf.svg";

function UsernameAvailable({ username, isUsernameAvailable }: { username: string, isUsernameAvailable: boolean | null }) {
  if ( username == "" ) {
    return ;
  }

  if (isUsernameAvailable == null ) {
    return (
      <>
        <img src={spinner} className="w-[20px] fill-white animate-spin fill-zinc-500"/>
        Checking username availability
      </>
    );
  }

  return (isUsernameAvailable ? "Username available" : "Username already taken");
}

export default function SignUp({ email, setEmail, username, setUsername, password, setPassword, handleSignUp, isUsernameAvailable, errorMessage }: SignUpFormPropsType) {
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
      <form className="bg-zinc-800 p-8 rounded-l flex flex-col gap-4" onSubmit={(e) => {e.preventDefault()}}>
        <label className="text-white"> Email: </label>
        <input className="bg-zinc-700 rounded p-2 text-white"  onChange={(e) => { handleChange(e, setEmail) }} value={email} />
        
        <div className="flex flex-col gap-2">
          <label className="text-white"> Username: </label>
          <input className="bg-zinc-700 rounded p-2 text-white"  onChange={(e) => { handleChange(e, setUsername) }} value={username} />
          <div className="text-sm text-white flex flex-row gap-2"> <UsernameAvailable username={username} isUsernameAvailable={isUsernameAvailable} /> </div>
        </div>
        <label className="text-white"> Password: </label>
        <div className="relative">
          <input className="bg-zinc-700 rounded p-2 text-white pr-16" type={!showPass ? "password" : ""} onChange={(e) => { handleChange(e, setPassword) }} value={password}/>
          <div className="absolute right-0 top-1/2 -translate-y-1/2 right-4 text-white hover:underline cursor-pointer text-blue-500" onClick={handleShowPassword}> { showPass ? "Hide" : "Show"} </div>
        </div>
        <button className={`bg-zinc-700 p-2 rounded text-white ${isUsernameAvailable == false ? "hover:bg-zinc-800" : "hover:bg-zinc-500 "}`} onClick={handleSignUp} disabled={isUsernameAvailable == false}> Sign Up </button>
        {errorMessage != "" && <h1 className="text-red-500"> Error: {errorMessage} </h1>}
        <p className="text-blue-600 text-sm"> Already have an account? <br /> <span className="hover:underline hover:cursor-pointer" onClick={() => {navigate("/login")}}> Log in </span></p>
      </form>
    </div>
  )
}
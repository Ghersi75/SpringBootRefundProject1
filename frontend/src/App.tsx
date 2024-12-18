import { Route, Routes } from "react-router-dom"
import Navbar from "./Components/Navbar/Navbar"
import SignUpManager from "./Components/SignUp/SignUpManager"
import { Toaster } from "react-hot-toast"
import LoginManager from "./Components/Login/LoginManager"
import TicketsManager from "./Components/Tickets/TicketsManager"

function App() {
  return (
    <div className="h-svh max-w-svw bg-zinc-900 flex flex-col">
      <Toaster />
      <Navbar />
      <Routes>
        <Route path="/" element= { <TicketsManager /> } />
        <Route path="/login" element={ <LoginManager /> } />
        <Route path="/signup" element={ <SignUpManager /> } />
      </Routes>
    </div>
  )
}

export default App

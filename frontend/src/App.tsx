import { Route, Routes } from "react-router-dom"
import Login from "./Components/Login"
import Navbar from "./Components/Navbar"
import Tickets from "./Pages/Tickets"
import SignUpManager from "./Components/SignUpManager"

function App() {
  return (
    <div className="min-h-svh min-w-svw bg-zinc-900 flex flex-col">
      <Navbar />
      <Routes>
        <Route path="/" element= { <Tickets /> } />
        <Route path="/login" element={ <Login /> } />
        <Route path="/signup" element={ <SignUpManager /> } />
      </Routes>
    </div>
  )
}

export default App

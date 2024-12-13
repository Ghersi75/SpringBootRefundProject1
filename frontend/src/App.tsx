import { Route, Routes } from "react-router-dom"
import Login from "./components/Login"
import Navbar from "./components/Navbar"
import Tickets from "./pages/Tickets"
import SignUp from "./components/SignUp"

function App() {
  return (
    <div className="min-h-svh min-w-svw bg-zinc-900 flex flex-col">
      <Navbar />
      <Routes>
        <Route path="/" element= { <Tickets /> } />
        <Route path="/login" element={ <Login /> } />
        <Route path="/signup" element={ <SignUp /> } />
      </Routes>
    </div>
  )
}

export default App

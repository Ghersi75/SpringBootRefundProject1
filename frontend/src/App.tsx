import Navbar from "./components/Navbar"
import Tickets from "./pages/Tickets"

function App() {
  return (
    <div className="min-h-svh min-w-svw bg-zinc-900 flex flex-col">
      <Navbar />
      <Tickets />
    </div>
  )
}

export default App

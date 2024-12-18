import { useEffect, useState } from "react"
import Modal from "./Modal";
import axios from "axios";
import { useUserInfo } from "../../Hooks/useUserInfo";
import Ticket from "./Ticket";
import TicketsTopRow from "./TicketsTopRow";

export default function Tickets() {
  const { userInfo } = useUserInfo();
  const [isOpen, setIsOpen] = useState(false);
  const [tickets, setTicket] = useState<any[]>([]);

  useEffect(() => {
    if (userInfo != null) {
      axios.get(`http://localhost:8080/ticket?userId=${userInfo.userId}`, {
        withCredentials: true
      })
        .then(data => console.log(data));
    }
  }, [userInfo])

  return (
    <div className="grow flex flex flex-col overflow-hidden p-8 justify-center items-center gap-8">
      {isOpen && <Modal setIsOpen={setIsOpen}/>}
      <div className="grow overflow-scroll rounded-xl">
        <table className="table-fixed w-full bg-zinc-500 border-collapse grow">
          <TicketsTopRow />
          {/* [&>*:nth-child(odd)]:bg-zinc-400 [&>*:nth-child(odd)]:divide-zinc-300 [&>*:nth-child(even)]:bg-zinc-500 [&>*:nth-child(even)]:divide-zinc-300 text-white last:bg-red-500 */}
          <tbody className="divide-y divide-zinc-800">
            {[...Array(25).keys()].map((i) => {
              return (
                <Ticket id={1} amount={1233} description="Nice description yap yap yap" reimbursementType="TRAVEL" ticketStatus="PENDING" timeAdded="12:30" key={i}/>
              )
            })}
          </tbody>
        </table>
      </div>
      <button className="bg-zinc-200 p-4 w-fit rounded-xl px-8 text-xl" onClick={() => setIsOpen(true)}> Submit New Ticket </button>
    </div>
  )
}
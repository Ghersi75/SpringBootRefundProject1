import { useState } from "react"
import Ticket from "./Ticket";
import TicketsTableHeader from "./TicketsTableHeader";
import { TicketsProps } from "../../Types/TicketProps";
import EmployeeModalManager from "./EmployeeModalManager";

export default function Tickets({ tickets, setTickets }: TicketsProps) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="grow flex flex flex-col overflow-hidden p-8 justify-center items-center gap-8">
      {isOpen &&
        <EmployeeModalManager
          setIsOpen={setIsOpen}
          setTickets={setTickets} />}
      <div className="grow overflow-scroll rounded-xl">
        <table className="table-fixed w-full bg-zinc-500 border-collapse grow">
          <TicketsTableHeader />
          <tbody className="divide-y divide-zinc-800">
            {tickets.map((ticket, idx) => {
              return (
                <Ticket
                  id={ticket.id}
                  amount={ticket.amount}
                  description={ticket.description}
                  reimbursementType={ticket.reimbursementType}
                  ticketStatus={ticket.ticketStatus}
                  timeAdded={ticket.timeAdded}
                  key={idx} />
              )
            })}
          </tbody>
        </table>
      </div>
      <button className="bg-zinc-200 p-4 w-fit rounded-xl px-8 text-xl" onClick={() => setIsOpen(true)}> Submit New Ticket </button>
    </div>
  )
}
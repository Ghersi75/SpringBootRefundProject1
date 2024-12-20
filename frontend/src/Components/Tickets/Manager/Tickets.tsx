import Ticket from "./Ticket";
import TicketsTableHeader from "./TicketsTableHeader";
import { TicketsProps } from "../../../Types/TicketProps";

export default function Tickets({ tickets }: TicketsProps) {
  return (
    <div className="grow flex flex flex-col overflow-hidden p-8 justify-center items-center gap-8">
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
                  timeAdded={ticket.timeAdded}
                  key={idx} />
              )
            })}
          </tbody>
        </table>
      </div>
    </div>
  )
}
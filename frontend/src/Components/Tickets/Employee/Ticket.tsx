import { TicketProps } from "../../../Types/TicketProps";

export default function Ticket({
  id,
  amount,
  description,
  ticketStatus,
  timeAdded,
  reimbursementType,
}: TicketProps) {
  return (
    <tr className=" odd:bg-zinc-400 even:bg-zinc-500 text-white">
      <td className="p-4 text-center">{id}</td>
      <td className="p-4 w-[150px] text-center">{(amount / 100).toLocaleString("en-US", { style: "currency", currency: "USD" })}</td>
      <td className="p-4 text-left h-[100px] overflow-auto">{description}</td>
      <td className="p-4 w-[150px] text-center">{ticketStatus}</td>
      <td className="p-4 w-[150px] text-center">{timeAdded}</td>
      <td className="p-4 w-[150px] text-center">{reimbursementType}</td>
    </tr>
  );
}

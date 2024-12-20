import axios from "axios";
import { ManagerTicketProps } from "../../../Types/TicketProps";
import { useNavigate } from "react-router-dom";

export default function Ticket({
  id,
  amount,
  description,
  timeAdded,
  reimbursementType,
}: ManagerTicketProps ) {
  const navigate = useNavigate();

  const handleClick = (ticketStatus: string) => {
    axios.patch("http://localhost:8080/ticket", {
      "ticketId": id,
      "ticketStatus": ticketStatus
    }, {
      withCredentials: true
    }).then(res => {
      console.log(res)
      // Refresh page
      navigate(0);
    })
  }

  return (
    <tr className=" odd:bg-zinc-400 even:bg-zinc-500 text-white">
      <td className="p-4 text-center">{id}</td>
      <td className="p-4 w-[150px] text-center">{(amount / 100).toLocaleString("en-US", { style: "currency", currency: "USD" })}</td>
      <td className="p-4 text-left h-[100px] overflow-auto">{description}</td>
      <td className="p-4 text-center grid gap-2">
        <button className="p-2 bg-green-700 px-4 rounded-xl" onClick={() => {handleClick("APPROVED")}}> Approve </button>
        <button className="p-2 bg-red-700 px-4 rounded-xl" onClick={() => {handleClick("DENIED")}}> Deny </button>
      </td>
      <td className="p-4 w-[150px] text-center">{timeAdded}</td>
      <td className="p-4 w-[150px] text-center">{reimbursementType}</td>
    </tr>
  );
}

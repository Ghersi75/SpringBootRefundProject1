export default function TicketsTableHeader() {
  return (
    <thead className="sticky top-0 bg-zinc-700 text-white">
      <tr>
        <th className="p-4 w-[150px] text-center">Ticket ID</th>
        <th className="p-4 w-[150px] text-center">Amount</th>
        <th className="p-4 text-left">Description</th>
        <th className="p-4 w-[150px] text-center">Ticket Status</th>
        <th className="p-4 w-[200px] text-center">Time Added</th>
        <th className="p-4 w-[150px] text-center">Reimbursement Type</th>
      </tr>
    </thead>
  );
}

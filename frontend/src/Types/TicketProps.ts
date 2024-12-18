type TicketStatus = "PENDING" | "APPROVED" | "DENIED";
export type ReimbursementType = "TRAVEL" | "FOOD" | "LODGING" | "OTHER";

export interface TicketProps {
  id: number,
  amount: number,
  description: string,
  ticketStatus: TicketStatus,
  reimbursementType: ReimbursementType,
  timeAdded: string
}

export interface TicketsProps {
  tickets: TicketProps[],
  setTickets: React.Dispatch<React.SetStateAction<TicketProps[]>>
}
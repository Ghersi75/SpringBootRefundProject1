type TicketStatus = "PENDING" | "APPROVED" | "DENIED";
export type ReimbursementType = "TRAVEL" | "FOOD" | "LODGING" | "OTHER";

export interface ManagerTicketProps {
  id: number,
  amount: number,
  description: string,
  reimbursementType: ReimbursementType,
  timeAdded: string
}


export interface TicketProps extends ManagerTicketProps {
  ticketStatus: TicketStatus,
}
export interface TicketsProps {
  tickets: TicketProps[],
  setTickets: React.Dispatch<React.SetStateAction<TicketProps[]>>
}
type TicketStatus = "PENDING" | "APPROVED" | "DENIED";
export type reimbursementType = "TRAVEL" | "FOOD" | "LODGING" | "OTHER";

export interface TicketProps {
  id: number,
  amount: number,
  description: string,
  ticketStatus: TicketStatus,
  reimbursementType: reimbursementType,
  timeAdded: string
}
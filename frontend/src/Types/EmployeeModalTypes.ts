import { reimbursementType, TicketProps } from "./TicketProps";

export interface EmployeeModalManagerProps {
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>,
  setTickets: React.Dispatch<React.SetStateAction<TicketProps[]>>
}

export interface EmployeeModalProps {
  description: string,
  setDescription: React.Dispatch<React.SetStateAction<string>>,
  amount: number,
  setAmount: React.Dispatch<React.SetStateAction<number>>,
  reimbursementType: reimbursementType,
  setReimbursementType: React.Dispatch<React.SetStateAction<reimbursementType>>,
  createTicket: () => void,
  errorMessage: string,
  successMessage: string,
}
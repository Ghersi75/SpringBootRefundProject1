import { useState } from "react";
import axios from "axios";
import EmployeeModal from "./EmployeeModal";
import { useUserInfo } from "../../../Hooks/useUserInfo";
import { EmployeeModalManagerProps } from "../../../Types/EmployeeModalTypes";
import { ReimbursementType } from "../../../Types/TicketProps";

export default function EmployeeModalManager({ setIsOpen, setTickets }: EmployeeModalManagerProps) {
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState(0);
  const [reimbursementType, setReimbursementType] = useState<ReimbursementType>("OTHER");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const { userInfo } = useUserInfo();

  // https://stackoverflow.com/a/40187434
  const handleBackgroundClick = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (e.target == e.currentTarget) {
      setIsOpen(false);
    }
  }

  const createTicket = async () => {
    setErrorMessage("")
    setSuccessMessage("")
    if (userInfo == null) {
      return;
    }

    axios.post("http://localhost:8080/ticket", {
      "description": description,
      // This should not fail if the parseFloat worked fine above
      "amount": Math.trunc(amount * 100),
      "reimbursementType": reimbursementType
    }, {
      withCredentials: true
    })
      .then(res => {
        console.log(res.data);
        setTickets((prev) => [...prev, res.data])
        setSuccessMessage("Successfully created ticket")
      })
      .catch(err => {
        console.log(err.response.data.error)
        setErrorMessage(err.response.data.error)
      });
  }

  return (
    <div className="h-screen w-screen fixed inset-0 flex justify-center items-center backdrop-blur-xl z-10 cursor-pointer" onClick={handleBackgroundClick}>
      <EmployeeModal
        description={description}
        setDescription={setDescription}
        amount={amount}
        setAmount={setAmount}
        reimbursementType={reimbursementType}
        setReimbursementType={setReimbursementType}
        createTicket={createTicket}
        errorMessage={errorMessage}
        successMessage={successMessage} />
    </div>
  )
}
import { useState } from "react";
import { reimbursementType } from "../../Types/TicketProps";
import axios from "axios";
import { useUserInfo } from "../../Hooks/useUserInfo";

// TODO: Seperate logic into a parent component if there's time
// TODO: Create variables for reused styling here
// TODO: Write better logic for Radio components so they're not hardcoded
export default function Modal({ setIsOpen }: { setIsOpen: React.Dispatch<React.SetStateAction<boolean>> }) {
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [reimbursementType, setReimbursementType] = useState<reimbursementType>("OTHER");
  const { userInfo } = useUserInfo();

  const createTicket = async () => {
    if (userInfo == null) {
      return ;
    }
    let amountFloat;

    try {
      amountFloat = parseFloat(amount);
    } catch {
      return ;
    }

    axios.post("http://localhost:8080/ticket", {
      body: {
        
      }
    })
      .then(res => console.log(res));
    // TODO: Add logic in case user info is not loaded
  }

  // https://stackoverflow.com/a/40187434
  const handleBackgroundClick = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (e.target == e.currentTarget) {
      setIsOpen(false);
    }
  }

  return(
    <div className="h-screen w-screen fixed inset-0 flex justify-center items-center backdrop-blur-xl z-10 cursor-pointer" onClick={handleBackgroundClick}>
      <div className="h-1/2 w-1/2 bg-zinc-700 rounded-xl p-4 cursor-default">
        <h1 className="p-8 pb-4 text-white text-xl"> Create new ticket</h1>
        <h1 className="p-8 pt-0 text-white text-xl"> Description: </h1>
        <input className="ml-8 p-4 w-[90%] h-fit box-border rounded-xl whitespace-normal bg-zinc-500 text-white" type="text" value={description} onChange={(e) => {setDescription(e.target.value)}}/>
        <h1 className="p-8 text-white text-xl"> Amount: </h1>
        <input className="ml-8 p-4 w-fit h-fit box-border rounded-xl whitespace-normal bg-zinc-500 text-white" type="text" value={`${amount}`} onChange={(e) => {setAmount(`${e.target.value}`)}}  />
        <h1 className="p-8 text-white text-xl"> Reimbursement Type: </h1>
        <div className="flex flex-row gap-4 ml-8">
          <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
            <input className="cursor-pointer" type="radio" value="TRAVEL" onChange={() => {setReimbursementType("TRAVEL")}} checked={reimbursementType == "TRAVEL"}/>
            Travel
          </label>
          <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
            <input className="cursor-pointer" type="radio" id="Test" value="FOOD" onChange={() => {setReimbursementType("FOOD")}} checked={reimbursementType == "FOOD"}/>
            Food
          </label>
          <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
            <input className="cursor-pointer" type="radio" id="Test" value="LODGING" onChange={() => {setReimbursementType("LODGING")}} checked={reimbursementType == "LODGING"}/>
            Lodging
          </label>
          <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
            <input className="cursor-pointer" type="radio" id="Test" value="OTHER" onChange={() => {setReimbursementType("OTHER")}} checked={reimbursementType == "OTHER"}/>
            Other
          </label>
        </div>
        <button className="ml-8 mt-8 px-8 py-4 rounded-xl bg-zinc-300 "> Create New Ticket </button>
      </div>
    </div>
  )
}
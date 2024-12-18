import { EmployeeModalProps } from "../../Types/EmployeeModalTypes";

// TODO: Create variables for reused styling here
// TODO: Write better logic for Radio components so they're not hardcoded
export default function EmployeeModal({
  description,
  setDescription,
  amount,
  setAmount,
  reimbursementType,
  setReimbursementType,
  createTicket,
  errorMessage,
  successMessage
}: EmployeeModalProps) {

  return (
    <div className="h-fit w-1/2 bg-zinc-700 rounded-xl p-4 cursor-default p-8">
      <h1 className="p-8 pb-4 text-white text-xl"> Create new ticket</h1>
      <h1 className="p-8 pt-0 text-white text-xl"> Description: </h1>
      <input className="ml-8 p-4 w-[90%] h-fit box-border rounded-xl whitespace-normal bg-zinc-500 text-white" type="text" value={description} onChange={(e) => { setDescription(e.target.value) }} />
      <h1 className="p-8 text-white text-xl"> Amount: </h1>
      <input className="ml-8 p-4 w-fit h-fit box-border rounded-xl whitespace-normal bg-zinc-500 text-white" type="number" min={0.01} step="0.01" value={amount} onChange={(e) => { setAmount(parseFloat(e.target.value)) }} />
      <h1 className="p-8 text-white text-xl"> Reimbursement Type: </h1>
      <div className="flex flex-row gap-4 ml-8">
        <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
          <input className="cursor-pointer" type="radio" value="TRAVEL" onChange={() => { setReimbursementType("TRAVEL") }} checked={reimbursementType == "TRAVEL"} />
          Travel
        </label>
        <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
          <input className="cursor-pointer" type="radio" id="Test" value="FOOD" onChange={() => { setReimbursementType("FOOD") }} checked={reimbursementType == "FOOD"} />
          Food
        </label>
        <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
          <input className="cursor-pointer" type="radio" id="Test" value="LODGING" onChange={() => { setReimbursementType("LODGING") }} checked={reimbursementType == "LODGING"} />
          Lodging
        </label>
        <label className="flex gap-4 text-white justify-center items-center cursor-pointer">
          <input className="cursor-pointer" type="radio" id="Test" value="OTHER" onChange={() => { setReimbursementType("OTHER") }} checked={reimbursementType == "OTHER"} />
          Other
        </label>
      </div>
      <button className="m-8 px-8 py-4 mb-2 rounded-xl bg-zinc-300" onClick={createTicket}> Create New Ticket </button>
      {errorMessage != "" && <h1 className="pl-8 text-red-500"> Error: {errorMessage} </h1>}
      {successMessage != "" && <h1 className="pl-8 text-green-500"> {successMessage} </h1>}
    </div>
  )
}
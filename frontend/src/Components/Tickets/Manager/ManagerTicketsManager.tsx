import { useEffect, useState } from "react";
import { TicketProps } from "../../../Types/TicketProps"
import { useUserInfo } from "../../../Hooks/useUserInfo";
import axios from "axios";
import Tickets from "./Tickets";

export default function TicketsManager() {
  const [tickets, setTickets] = useState<TicketProps[]>([]);
  const { userInfo } = useUserInfo();

  useEffect(() => {
    if (userInfo != null) {
      axios.get(`http://localhost:8080/ticket`, {
        withCredentials: true
      })
        .then(res => setTickets(res.data));
    }
  }, [userInfo])

  return(
    <>
      <Tickets
        tickets={tickets}
        setTickets={setTickets}/>
    </>
  )
}
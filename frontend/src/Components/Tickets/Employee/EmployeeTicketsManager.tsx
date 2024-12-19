import { useEffect, useState } from "react";
import { TicketProps } from "../../../Types/TicketProps"
import { useUserInfo } from "../../../Hooks/useUserInfo";
import axios from "axios";
import Tickets from "./Tickets";
import RequireAuth from "../../RouteGuards/RequireAuth";

export default function EmployeeTicketsManager() {
  const [tickets, setTickets] = useState<TicketProps[]>([]);
  const { userInfo } = useUserInfo();

  useEffect(() => {
    if (userInfo != null) {
      axios.get(`http://localhost:8080/ticket?userId=${userInfo.userId}`, {
        withCredentials: true
      })
        .then(res => setTickets(res.data));
    }
  }, [userInfo])

  return(
    <RequireAuth>
      <Tickets
        tickets={tickets}
        setTickets={setTickets}/>
    </RequireAuth>
  )
}
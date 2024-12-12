import { useState } from "react"

export default function Tickets() {
  const [img, setImg] = useState<File | null>();
  console.log(img);
  return(
    <div className="bg-zinc-700 grow p-8">
      hello
      <input onChange={(e) => {
        if (e.target.files != null) {
          setImg(e.target.files[0]);
        }
      }} type="file"/>
    </div>
  )
}
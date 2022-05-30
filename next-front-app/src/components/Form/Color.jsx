import { useState } from "react"
import { v4 as uuidv4 } from "uuid"

const Color = (props) => {
  const [isChecked, setIsChecked] = useState(false)
  const { itemsArray, getIndex } = props
  return (
    <div
      onClick={() => {
        setIsChecked(!isChecked)

        !isChecked
          ? itemsArray.insert(props.id, {
              price: 1,
              stock: 1,
              color: {
                id: props.id,
                label: props.label,
              },
              images: [],
            })
          : itemsArray.remove(getIndex(props.id))
      }}
      key={uuidv4()}
      title={props.label}
      className={`p-5 rounded-full border-dashed border border-black  hover:cursor-pointer ${
        isChecked ? "border-4" : "hover:border-2"
      } `}
      style={{ backgroundColor: `${props.hexadecimalCode}` }}
    ></div>
  )
}
export default Color

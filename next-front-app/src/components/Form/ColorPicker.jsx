import useApi from "src/hooks/useApi"
import Color from "./Color"

const ColorPicker = (props) => {
  const { dataColor, itemsArray, getIndex } = props

  return (
    <div className="flex justify-around">
      {Object.values(dataColor).map((color) => (
        <Color {...color} itemsArray={itemsArray} getIndex={getIndex} />
      ))}
    </div>
  )
}
export default ColorPicker

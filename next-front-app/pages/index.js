import SetPosts from "@components/Posts/PostSet"
import useApi from "src/hooks/useApi"

const Home = () => {
  const [err, data] = useApi("get", "/furnitures")
  return <div></div>
}
export default Home

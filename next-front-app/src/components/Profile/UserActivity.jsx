import useApi from "src/hooks/useApi"
import { useState, useEffect } from "react"
import SetPosts from "@components/Posts/PostSet"
import RatingSet from "@components/Comments/CommentSet"

const UserActivity = (props) => {
  const { userId } = props
  const [errComments, dataComments] = useApi("get", `/users/${userId}/comments`)
  const [errPosts, dataPosts] = useApi(
    "get",
    `/users/${userId}/posts/published`
  )
  const [stateComments, setStateComments] = useState(dataComments)
  const [statePosts, setStatePosts] = useState(dataPosts)

  useEffect(() => {
    setStateComments(dataComments)
  }, [dataComments])

  useEffect(() => {
    setStatePosts(dataPosts)
  }, [dataPosts])
  return (
    <div className="container mx-auto flex flex-wrap py-6">
      <div className="w-full md:w-2/3 flex flex-col items-center px-3 shadow-xl border-double border-4 border-sky-500 w-auto">
        <h2 className="text-2xl font-normal leading-normal mb-2 text-pink-800">
          Latest orders
        </h2>
        {!statePosts.length ? (
          <div className="flex justify-center">
            <p className="bg-stone-400 text-white text-center font-bold px-4 py-2 w-auto mt-10">
              No orders yet
            </p>
          </div>
        ) : (
          <SetPosts {...props} />
        )}
      </div>
      <aside className="w-full md:w-1/3 flex flex-col items-center px-3 shadow-xl border-double border-4 border-sky-500 w-auto">
        <h2 className="text-2xl font-normal leading-normal mb-2 text-pink-800 ">
          Latest ratings
        </h2>
        <div
          className={`w-full ${
            !stateComments.length ? "" : "bg-white"
          }  flex flex-col my-4 p-6`}
        >
          {!stateComments || !stateComments.length ? (
            <div className="flex justify-center">
              <p className="bg-stone-400 text-white text-center font-bold px-4 py-2 w-auto">
                No rating yet
              </p>
            </div>
          ) : (
            <RatingSet
              commentState={stateComments.slice(0, 5)}
              setCommentState={setStateComments}
            />
          )}
        </div>
      </aside>
    </div>
  )
}
export default UserActivity

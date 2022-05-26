import { MdAccountCircle } from "react-icons/md"
import NavbarField from "./NavbarField.jsx"
import { useContext } from "react"
import { AppContext } from "@components/Context/AppContext.jsx"
import { MdOutlineMessage } from "react-icons/md"
import { MdAdminPanelSettings } from "react-icons/md"
import SearchBar from "./SearchBar.jsx"

const Navbar = () => {
  const { sessionUserId, sessionRightUser } = useContext(AppContext)

  return typeof window === "undefined" ? null : (
    <nav className="flex items-center flex-wrap bg-white p-3">
      <NavbarField href="/">
        <div className="text-md text-black font-bold uppercase tracking-wide">
          Sirioitalia
        </div>
      </NavbarField>
      <NavbarField href="/" className="bg-black lg:w-1/2">
        <SearchBar />
      </NavbarField>
      <div className="w-full lg:inline-flex lg:flex-grow lg:w-auto">
        <div className="lg:inline-flex lg:flex-row lg:ml-auto lg:w-auto w-full lg:items-center items-center  flex flex-col lg:h-auto">
          {sessionRightUser && sessionRightUser !== "reader" ? (
            <NavbarField href="/posts/write">
              <MdOutlineMessage title="Post write" size={16} />
            </NavbarField>
          ) : null}
          {sessionRightUser && sessionRightUser === "admin" ? (
            <NavbarField href="/admin/panel">
              <MdAdminPanelSettings title="Admin panel" size={16} />
            </NavbarField>
          ) : null}
          <NavbarField
            href={
              !sessionUserId
                ? "/authentication/sign-in"
                : `/users/profil/${sessionUserId}`
            }
          >
            <MdAccountCircle size={32} />
          </NavbarField>
        </div>
      </div>
    </nav>
  )
}
export default Navbar

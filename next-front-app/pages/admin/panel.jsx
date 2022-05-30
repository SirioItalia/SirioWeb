import AdminPanel from "@components/Admin/AdminPanel"
import Dashboard from "@components/Admin/Dashboard"

const AdminPanelPage = ({ applications }) => {
  return (
    <>
      <Dashboard />
      <AdminPanel />
    </>
  )
}
export default AdminPanelPage

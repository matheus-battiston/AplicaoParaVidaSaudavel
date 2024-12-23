import useGlobalUser from "../../../context/user.context";
import { axiosInstance } from "../_base/axiosInstance";

export function useLogout() {
   const [, setUser] = useGlobalUser()
   async function logout() {
      await axiosInstance.post('/logout')
      setUser(null)
   }
   return {logout}
}
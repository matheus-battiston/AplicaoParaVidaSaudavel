import { Navigate } from "react-router-dom";
import useGlobalUser from "../context/user.context";
import { InitialInformation } from "../ui/screens/initialInformations/initialInformations.screen";

export function PrivateRoute({ Screen }) {
    const [user] = useGlobalUser()
    if (user) {
        if (user.informado)
            return <Screen />

        return <InitialInformation />
    }

    return <Navigate to={'/login'} />
}
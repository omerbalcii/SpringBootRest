import { ITokenInfo } from "../model/ITokenInfo";
import { Button, Stack } from "react-bootstrap";
import axiosconfig from "../utils/axiosconfig";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../context/UsernameContext";
import { useContext } from "react";

export default function LoginComponent() {
  const mynavigate = useNavigate();

  const ourUserContext = useContext(UserContext);

  function handleSubmitAdmin() {
    axiosconfig.post("/login", { username: "admin", password: "1234" }).then((response) => {
      const tokenInfo: ITokenInfo = response.data;
      const username = tokenInfo.username;
      const userjwt = tokenInfo.token;
      const authorities = tokenInfo.authorities;
      localStorage.setItem("username", username);
      localStorage.setItem("userjwt", userjwt);
      localStorage.setItem("authorities", authorities);
      // bu bütün sayfayı refresh eder context 'e gerek kalmaz, ama güzel bir çözüm değil
      // window.location.replace("/");
      ourUserContext.setterforusername(username);
      mynavigate("/");
    });
  }
  function handleSubmitUser() {
    axiosconfig.post("/login", { username: "user", password: "1234" }).then((response) => {
      const tokenInfo: ITokenInfo = response.data;
      const username = tokenInfo.username;
      const userjwt = tokenInfo.token;
      const authorities = tokenInfo.authorities;
      localStorage.setItem("username", username);
      localStorage.setItem("userjwt", userjwt);
      localStorage.setItem("authorities", authorities);
      // bu bütün sayfayı refresh eder context 'e gerek kalmaz, ama güzel bir çözüm değil
      // window.location.replace("/");
      ourUserContext.setterforusername(username);
      mynavigate("/");
    });
  }

  return (
    <Stack gap={3}>
      <Button variant="outline-success" onClick={handleSubmitAdmin}>
        Admin Login
      </Button>
      <Button variant="outline-success" onClick={handleSubmitUser}>
        User Login
      </Button>
    </Stack>
  );
}

import { useContext } from "react";
import { Button, Stack } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import { UserContext } from "../context/UsernameContext";

export default function MenuComponent() {
  const ourUserContext = useContext(UserContext);
  const mynavigate = useNavigate();

  return (
    <Stack>
      <h4>{ourUserContext.usernameeee}</h4>
      <Stack gap={2}>
        {/* react-router-dom 'dan gelen navlik olacak */}
        <NavLink to="/">Anasayfa</NavLink>
        <NavLink className={({ isActive }) => (isActive ? "bg-info" : "")} to="/ogretmen" end>
          Öğretmenler
        </NavLink>
        {/* context içerisindeki state bu componentin render edilmesini sağlıyor */}
        {localStorage.getItem("authorities")?.includes("ADMIN") && (
          <NavLink to="/ogretmen/kaydet" className={({ isActive }) => (isActive ? "bg-info" : "")}>
            Öğretmen Kaydet
          </NavLink>
        )}
        <NavLink to="/ogrenci" className={({ isActive }) => (isActive ? "bg-info" : "")} end>
          Öğrenciler
        </NavLink>
        <NavLink to="/ogrenci/kaydet" className={({ isActive }) => (isActive ? "bg-info" : "")}>
          Öğrenci Kaydet
        </NavLink>
        <NavLink to="/konu" className={({ isActive }) => (isActive ? "bg-info" : "")} end>
          Konular
        </NavLink>
        <NavLink to="/konu/kaydet" className={({ isActive }) => (isActive ? "bg-info" : "")}>
          Konu Kaydet
        </NavLink>
        <NavLink to="/ders" end className={({ isActive }) => (isActive ? "bg-info" : "")}>
          Dersler
        </NavLink>
        <NavLink to="/ders/kaydet" className={({ isActive }) => (isActive ? "bg-info" : "")}>
          Ders Kaydet
        </NavLink>
        <NavLink to="/dersogrenci" className={({ isActive }) => (isActive ? "bg-info" : "")} end>
          Ders Kayıtları
        </NavLink>
        <NavLink to="/dersogrenci/kaydet" className={({ isActive }) => (isActive ? "bg-info" : "")}>
          Ders Öğrenci Kaydet
        </NavLink>
        {localStorage.getItem("userjwt") === null && (
          <NavLink to="/login" className={({ isActive }) => (isActive ? "bg-info" : "")}>
            Login
          </NavLink>
        )}
        {localStorage.getItem("userjwt") !== null && (
          <Button
            variant="outline-danger"
            onClick={() => {
              localStorage.removeItem("username");
              localStorage.removeItem("userjwt");
              localStorage.removeItem("authorities");
              ourUserContext.setterforusername("");
              // replace çalışmadı
              mynavigate("/ogretmen", { replace: true });
            }}
          >
            Logout
          </Button>
        )}
      </Stack>
    </Stack>
  );
}

import React from "react";
import ReactDOM from "react-dom/client";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Col, Container, Row } from "react-bootstrap";
import MenuComponent from "./components/menu/MenuComponent";
import OgretmenlerComponent from "./components/ogretmen/OgretmenlerComponent";
import OgretmenDetayComponent from "./components/ogretmen/OgretmenDetayComponent";
import OgrencilerComponent from "./components/ogrenci/OgrencilerComponent";
import OgrenciDetayComponent from "./components/ogrenci/OgrenciDetayComponent";
import KonularComponent from "./components/konu/KonularComponent";
import KonuDetayComponent from "./components/konu/KonuDetayComponent";
import DerslerComponent from "./components/ders/DerslerComponent";
import DersDetayComponent from "./components/ders/DersDetayComponent";
import OgretmenKaydetComponent from "./components/ogretmen/OgretmenKaydetComponent";
import OgretmenGuncelleComponent from "./components/ogretmen/OgretmenGuncelleComponent";
import DersKaydetComponent from "./components/ders/DersKaydetComponent";
import DersGuncelleComponent from "./components/ders/DersGuncelleComponent";
import DersOgrencilerComponent from "./components/dersogrenci/DersOgrencilerComponent";
import DersOgrenciDetayComponent from "./components/dersogrenci/DersOgrenciDetayComponent";
import DersOgrenciKaydetComponent from "./components/dersogrenci/DersOgrenciKaydetComponent";
import DersOgrenciGuncelleComponent from "./components/dersogrenci/DersOgrenciGuncelleComponent";
import LoginComponent from "./components/user/LoginComponent";
import IndexComponent from "./components/IndexComponent";
import KonuKaydetComponent from "./components/konu/KonuKaydetComponent";
import KonuGuncelleComponent from "./components/konu/KonuGuncelleComponent";
import { UserContextProvider } from "./components/context/UsernameContext";

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement);
root.render(
  <React.StrictMode>
    <UserContextProvider>
      <BrowserRouter>
        <Container className="pt-3" fluid>
          <Row>
            <Col xs="2">
              <MenuComponent />
            </Col>
            <Col xs="10">
              <Routes>
                {/* {path lere dikkat} */}
                <Route path="/" element={<IndexComponent />}/>
                <Route path="ogretmen" element={<OgretmenlerComponent />} />
                <Route path="ogretmen/detay" element={<OgretmenDetayComponent />} />
                <Route path="ogretmen/kaydet" element={<OgretmenKaydetComponent />} />
                <Route path="ogretmen/guncelle" element={<OgretmenGuncelleComponent />} />
                <Route path="konu" element={<KonularComponent />} />
                <Route path="konu/detay" element={<KonuDetayComponent />} />
                <Route path="konu/kaydet" element={<KonuKaydetComponent />} />
                <Route path="konu/guncelle" element={<KonuGuncelleComponent />} />
                <Route path="ogrenci" element={<OgrencilerComponent />} />
                <Route path="ogrenci/detay" element={<OgrenciDetayComponent />} />
                <Route path="ders" element={<DerslerComponent />} />
                <Route path="ders/detay" element={<DersDetayComponent />} />
                <Route path="ders/kaydet" element={<DersKaydetComponent />} />
                <Route path="ders/guncelle" element={<DersGuncelleComponent />} />
                <Route path="dersogrenci" element={<DersOgrencilerComponent />} />
                <Route path="dersogrenci/detay" element={<DersOgrenciDetayComponent />} />
                <Route path="dersogrenci/kaydet" element={<DersOgrenciKaydetComponent />} />
                <Route path="dersogrenci/guncelle" element={<DersOgrenciGuncelleComponent />} />
                <Route path="login" element={<LoginComponent />} />
                {/*/>
          <Route path="ogrenci/kaydet" element={<OgrenciKaydetComponent />} />
          <Route path="ogrenci/guncelle" element={<OgrenciGuncelleComponent />} /> 
          <Route path="logout" element={<LogoutComponent />} /> 
          */}
              </Routes>
            </Col>
          </Row>
        </Container>
      </BrowserRouter>
    </UserContextProvider>
  </React.StrictMode>
);

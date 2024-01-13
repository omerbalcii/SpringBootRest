import { EffectCallback, MouseEventHandler, useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Button, Row, Spinner, Table } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IOgretmen } from "../model/IOgretmen";
import { getAxiosHeaders } from "../utils/Utils";

// [] dependency deniyor ve önemlidir
export default function OgretmenlerComponent() {
  // Iogretmen array 'i boş olarak oluşturuldu ve state 'e yüklendi
  // state kullandığım için ogretmenler her güncellendiğinde aşağıdaki table ve spinner da güncelleniyor
  const [ogretmenler, setOgretmenler] = useState<IOgretmen[]>([]);
  // [] dependency deniyor ve önemlidir [ogretmenler] yazılırsa ogertmenler listesi değiştiğinde tekrar çalışır
  useEffect(initialize(), []);

  function initialize(): EffectCallback {
    return () => {
      axiosconfig.get("ogretmen/getall", getAxiosHeaders()).then((response) => {
        setOgretmenler(response.data);
      });
    };
  }

  function btnSilClick(id: number): MouseEventHandler<HTMLButtonElement> {
    return () => {
      setOgretmenler([]);
      const url = "ogretmen/deletebyid/" + id;
      axiosconfig.delete(url, getAxiosHeaders()).then(initialize());
    };
  }

  return (
    <div>
      {ogretmenler.length === 0 && (
        <Row className="justify-content-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}
      {ogretmenler.length !== 0 && (
        <Table responsive striped bordered>
          <thead className="table-dark">
            <tr>
              <th className="col-auto">id</th>
              <th className="col-auto">name</th>
              <th className="col-auto">is_gicik</th>
              <th className="col-auto">Guncelle</th>
              {localStorage.getItem("authorities")?.includes("ADMIN") && <th className="col-auto">Sil</th>}
              <th className="col-auto">Detay</th>
            </tr>
          </thead>
          <tbody>
            {
              // burası dinamik <tr> 'ler oluşturucak
              // ogretmenler.forEach(ogr => { }); // yazmadık çünkü foreach return edemezdi ve appendchild yapmak zorunda kalırdık
              ogretmenler.map((ogretmen, index) => {
                return (
                  // key önemlidir çünkü unique olmalıdır
                  <tr key={index}>
                    <td className="col-auto">{ogretmen.id}</td>
                    <td className="col-auto">{ogretmen.name}</td>
                    <td className="col-auto">{ogretmen.is_GICIK ? "Gıcık" : "Normal"}</td>
                    <td className="col-auto">
                      <NavLink className="btn btn-outline-success" to={"/ogretmen/guncelle?ogrId=" + ogretmen.id}>
                        Güncelle
                      </NavLink>
                    </td>
                    {localStorage.getItem("authorities")?.includes("ADMIN") && (
                      <td className="col-auto">
                        <Button type="button" variant="outline-danger" onClick={btnSilClick(ogretmen.id)}>
                          Sil
                        </Button>
                      </td>
                    )}
                    <td className="col-auto">
                      <NavLink className="btn btn-outline-primary" to={"/ogretmen/detay?ogrId=" + ogretmen.id}>
                        Detay
                      </NavLink>
                    </td>
                  </tr>
                );
              })
            }
          </tbody>
        </Table>
      )}
    </div>
  );
}

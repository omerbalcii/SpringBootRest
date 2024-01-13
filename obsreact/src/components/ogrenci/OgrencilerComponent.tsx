import { EffectCallback, MouseEventHandler, useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Button, Row, Spinner, Table } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IOgrenci } from "../model/IOgrenci";
import { getAxiosHeaders } from "../utils/Utils";

// [] dependency deniyor ve önemlidir
export default function OgrencilerComponent() {
  // Iogretmen array 'i boş olarak oluşturuldu ve state 'e yüklendi
  // state kullandığım için ogretmenler her güncellendiğinde aşağıdaki table ve spinner da güncelleniyor
  const [ogrenciler, setOgrenciler] = useState<IOgrenci[]>([]);
  // [] dependency deniyor ve önemlidir [ogretmenler] yazılırsa ogertmenler listesi değiştiğinde tekrar çalışır
  useEffect(initialize(), []);

  function initialize(): EffectCallback {
    return () => {
      axiosconfig.get("ogrenci/getall", getAxiosHeaders()).then((response) => {
        setOgrenciler(response.data);
      });
    };
  }

  function btnSilClick(id: number): MouseEventHandler<HTMLButtonElement> {
    return () => {
      setOgrenciler([]);
      const url = "ogrenci/deletebyid/" + id;
      axiosconfig.delete(url, getAxiosHeaders()).then(initialize());
    };
  }

  return (
    <div>
      {ogrenciler.length === 0 && (
        <Row className="justify-content-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}
      {ogrenciler.length !== 0 && (
        <Table responsive striped bordered>
          <thead className="table-dark">
            <tr>
              <th className="col-auto">id</th>
              <th className="col-auto">name</th>
              <th className="col-auto">number</th>
              <th className="col-auto">year</th>
              <th className="col-auto">Guncelle</th>
              <th className="col-auto">Sil</th>
              <th className="col-auto">Detay</th>
            </tr>
          </thead>
          <tbody>
            {
              // burası dinamik <tr> 'ler oluşturucak
              // ogretmenler.forEach(ogr => { }); // yazmadık çünkü foreach return edemezdi ve appendchild yapmak zorunda kalırdık
              ogrenciler.map((ogrenci, index) => {
                return (
                  // key önemlidir çünkü unique olmalıdır
                  <tr key={index}>
                    <td className="col-auto">{ogrenci.id}</td>
                    <td className="col-auto">{ogrenci.name}</td>
                    <td className="col-auto">{ogrenci.ogr_NUMBER}</td>
                    <td className="col-auto">{ogrenci.year}</td>
                    <td className="col-auto">
                      <NavLink
                        className="btn btn-outline-success"
                        to={"/ogrenci/guncelle?ogrenciId=" + ogrenci.id}
                      >
                        Güncelle
                      </NavLink>
                    </td>
                    <td className="col-auto">
                      <Button
                        type="button"
                        variant="outline-danger"
                        onClick={btnSilClick(ogrenci.id)}
                      >
                        Sil
                      </Button>
                    </td>
                    <td className="col-auto">
                      <NavLink
                        className="btn btn-outline-primary"
                        to={"/ogrenci/detay?ogrenciId=" + ogrenci.id}
                      >
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

import { EffectCallback, MouseEventHandler, useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Button, Row, Spinner, Table } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IKonu } from "../model/IKonu";
import { getAxiosHeaders } from "../utils/Utils";

// [] dependency deniyor ve önemlidir
export default function KonularComponent() {
  // Ikonu array 'i boş olarak oluşturuldu ve state 'e yüklendi
  // state kullandığım için konular her güncellendiğinde aşağıdaki table ve spinner da güncelleniyor
  const [konular, setKonular] = useState<IKonu[]>([]);
  // [] dependency deniyor ve önemlidir [konular] yazılırsa ogertmenler listesi değiştiğinde tekrar çalışır
  useEffect(initialize(), []);

  function initialize(): EffectCallback {
    return () => {
      axiosconfig.get("konu/getall", getAxiosHeaders()).then((response) => {
        setKonular(response.data);
      });
    };
  }

  function btnSilClick(id: number): MouseEventHandler<HTMLButtonElement> {
    return () => {
      setKonular([]);
      const url = "konu/deletebyid/" + id;
      axiosconfig.delete(url, getAxiosHeaders()).then(initialize());
    };
  }

  return (
    <div>
      {konular.length === 0 && (
        <Row className="justify-content-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}
      {konular.length !== 0 && (
        <Table responsive striped bordered>
          <thead className="table-dark">
            <tr>
               {/*
                id : number;
                name : string;  */}
              <th className="col-auto">id</th>
              <th className="col-auto">name</th>
              <th className="col-auto">Guncelle</th>
              <th className="col-auto">Sil</th>
              <th className="col-auto">Detay</th>
            </tr>
          </thead>
          <tbody>
            {
              // burası dinamik <tr> 'ler oluşturucak
              // konular.forEach(konu => { }); // yazmadık çünkü foreach return edemezdi ve appendchild yapmak zorunda kalırdık
              konular.map((konu, index) => {
                return (
                  // key önemlidir çünkü unique olmalıdır
                  <tr key={index}>
                    <td className="col-auto">{konu.id}</td>
                    <td className="col-auto">{konu.name}</td>
                    <td className="col-auto">
                      <NavLink
                        className="btn btn-outline-success"
                        to={"/konu/guncelle?konuId=" + konu.id}
                      >
                        Güncelle
                      </NavLink>
                    </td>
                    <td className="col-auto">
                      <Button
                        type="button"
                        variant="outline-danger"
                        onClick={btnSilClick(konu.id)}
                      >
                        Sil
                      </Button>
                    </td>
                    <td className="col-auto">
                      <NavLink
                        className="btn btn-outline-primary"
                        to={"/konu/detay?konuId=" + konu.id}
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

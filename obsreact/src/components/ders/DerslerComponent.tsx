import { EffectCallback, MouseEventHandler, useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Button, Row, Spinner, Table } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IDersDTO } from "../model/IDersDTO";
import { getAxiosHeaders } from "../utils/Utils";

export default function DerslerComponent() {
  const [dersler, setDersler] = useState<IDersDTO[]>([]);

  useEffect(initialize(), []);

  function initialize(): EffectCallback {
    return () => {
      axiosconfig.get("ders/getalldto", getAxiosHeaders()).then((response) => {
        setDersler(response.data);
      });
    };
  }

  function btnSilClick(id: number): MouseEventHandler<HTMLButtonElement> {
    return () => {
      setDersler([]);
      const url = "ders/deletebyid/" + id;
      axiosconfig.delete(url, getAxiosHeaders()).then(initialize());
    };
  }

  return (
    <div>
      {dersler.length === 0 && (
        <Row className="justify-content-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}
      {dersler.length !== 0 && (
        <Table responsive striped bordered>
          <thead className="table-dark">
            <tr>
               
              <th className="col-auto">ID</th>
              <th className="col-auto">OGRETMEN</th>
              <th className="col-auto">KONU</th>
              <th className="col-auto">Guncelle</th>
              <th className="col-auto">Sil</th>
              <th className="col-auto">Detay</th>
            </tr>
          </thead>
          <tbody>
            {dersler.map((ders, index) => (
              <tr key={index}>
                <td className="col-auto">{ders.id}</td>
                <td className="col-auto">{ders.ogretmen.name}</td>
                <td className="col-auto">{ders.konu.name}</td>
                <td className="col-auto">
                  <NavLink
                    className="btn btn-outline-success"
                    to={"/ders/guncelle?dersId=" + ders.id}
                  >
                    Güncelle
                  </NavLink>
                </td>
                <td className="col-auto">
                  <Button
                    type="button"
                    variant="outline-danger"
                    onClick={btnSilClick(ders.id)}
                  >
                    Sil
                  </Button>
                </td>
                <td className="col-auto">
                  <NavLink
                    className="btn btn-outline-primary"
                    to={"/ders/detay?dersId=" + ders.id}
                  >
                    Detay
                  </NavLink>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </div>
  );
}

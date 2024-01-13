import React, { EffectCallback, MouseEventHandler, useEffect, useState } from "react";
import { IDersOgrenciDTO } from "../model/IDersOgrenciDTO";
import axiosconfig from "../utils/axiosconfig";
import { Button, Row, Spinner, Table } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { getAxiosHeaders } from "../utils/Utils";

export default function DersOgrencilerComponent() {
  const [dersogrenciler, setDersOgrenciler] = useState<IDersOgrenciDTO[]>([]);

  useEffect(initialize(), []);

  function initialize(): EffectCallback {
    return () => {
      axiosconfig.get("dersogrenci/getalldto", getAxiosHeaders()).then((response) => {
        setDersOgrenciler(response.data);
      });
    };
  }

  function btnSilClick(id: number): MouseEventHandler<HTMLButtonElement> {
    return () => {
      setDersOgrenciler([]);
      const url = "dersogrenci/deletebyid/" + id;
      axiosconfig.delete(url, getAxiosHeaders()).then(initialize());
    };
  }

  return (
    <div>
      {dersogrenciler.length === 0 && (
        <Row className="justify-content-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}
      {dersogrenciler.length !== 0 && (
        <Table responsive striped bordered>
          <thead className="table-dark">
            <tr>
              <th className="col-auto">ID</th>
              <th className="col-auto">OGRETMEN</th>
              <th className="col-auto">KONU</th>
              <th className="col-auto">OGRENCI</th>
              <th className="col-auto">OGR_NUMBER</th>
              <th className="col-auto">OGR_YEAR</th>
              <th className="col-auto">DEVAMSIZLIK</th>
              <th className="col-auto">NOTE</th>
              <th className="col-auto">Guncelle</th>
              <th className="col-auto">Sil</th>
              <th className="col-auto">Detay</th>
            </tr>
          </thead>
          <tbody>
            {dersogrenciler.map((dersogrenci, index) => (
              <tr key={index}>
                <td className="col-auto">{dersogrenci.id}</td>
                <td className="col-auto">{dersogrenci.ders.ogretmen.name}</td>
                <td className="col-auto">{dersogrenci.ders.konu.name}</td>
                <td className="col-auto">{dersogrenci.ogrenci.name}</td>
                <td className="col-auto">{dersogrenci.ogrenci.ogr_NUMBER}</td>
                <td className="col-auto">{dersogrenci.ogrenci.year}</td>
                <td className="col-auto">{dersogrenci.devamsizlik}</td>
                <td className="col-auto">{dersogrenci.note}</td>
                <td className="col-auto">
                  <NavLink className="btn btn-outline-success" to={"/dersogrenci/guncelle?dersOgrenciId=" + dersogrenci.id}>
                    Güncelle
                  </NavLink>
                </td>
                <td className="col-auto">
                  <Button type="button" variant="outline-danger" onClick={btnSilClick(dersogrenci.id)}>
                    Sil
                  </Button>
                </td>
                <td className="col-auto">
                  <NavLink className="btn btn-outline-primary" to={"/dersogrenci/detay?dersOgrenciId=" + dersogrenci.id}>
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

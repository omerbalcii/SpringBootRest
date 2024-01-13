import { useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Card, Row, Spinner, Stack } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IOgrenci } from "../model/IOgrenci";
import { getAxiosHeaders } from "../utils/Utils";

export default function OgrenciDetayComponent() {
  const [ogrenci, setOgrenci] = useState<IOgrenci | null>(null); // Başlangıç değerini belirt

  // react router ile aşağıdaki gibi alınabilir ? ile gelen parametreler
  let ogrenciId = new URLSearchParams(window.location.search).get("ogrenciId"); // Parametre adını "ogrId" olarak değiştir

  useEffect(() => {
    if (ogrenciId) {
      // Eğer ogrenciId varsa API isteğini yap
      axiosconfig.get("ogrenci/getbyid/" + ogrenciId, getAxiosHeaders()).then((response) => {
        try {
          setOgrenci(response.data);
        } catch (err) {
          console.error(err); // Hata durumunda konsola yazdır
        }
      });
    }
  }, [ogrenciId]); // useEffect'in bağımlılığını ogrenciId'ye ayarla

  return (
    <div>
      {ogrenci != null ? (
        <Stack gap={3}>
          <Card>
            <Card.Body className="shadow">
              <Card.Title>{ogrenci.name}</Card.Title>
              <Card.Text>{ogrenci.year + " - " + ogrenci.ogr_NUMBER}</Card.Text>
            </Card.Body>
          </Card>
          <NavLink className="btn btn-danger" to="/ogrenci">
            Geri
          </NavLink>
        </Stack>
      ) : (
        <Row className="justify-content-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}
    </div>
  );
}

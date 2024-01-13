import { useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Card, FormLabel, Row, Spinner, Stack } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IDersDTO } from "../model/IDersDTO";
import { getAxiosHeaders } from "../utils/Utils";

export default function OgretmenDetayComponent() {
  const [ders, setDers] = useState<IDersDTO>();
  let dersId = new URLSearchParams(window.location.search).get("dersId");

  useEffect(() => {
    axiosconfig.get("ders/getbyid/dto/" + dersId, getAxiosHeaders()).then((response) => {
      try {
        setDers(response.data);
      } catch (err) {}
    });
  }, []);

  return (
    <div>
      {ders != null ? (
        <Stack gap={3}>
          <Card>
            <Card.Body className="shadow">
              <Card.Title>{"Ders Id = " + ders.id}</Card.Title>
              <Card.Text>
                <Stack>
                  <FormLabel>{ders.ogretmen.name}</FormLabel>
                  <FormLabel>{ders.konu.name}</FormLabel>
                </Stack>
              </Card.Text>
            </Card.Body>
          </Card>
          <NavLink className="btn btn-danger" to="/ders">
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

import { useEffect, useState } from "react";
import { IOgretmen } from "../model/IOgretmen";
import axiosconfig from "../utils/axiosconfig";
import { Card, Row, Spinner, Stack } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { getAxiosHeaders } from "../utils/Utils";

export default function OgretmenDetayComponent() {
    const [ogretmen, setOgretmen] = useState<IOgretmen>();

    // react router ile aşağıdaki gibi alınabilir ? ile gelen parametreler
    // let [searchParams, setSearchParams] = useSearchParams();
    // let ogrId = searchParams.get("ogrId");

    let ogrId = (new URLSearchParams(window.location.search)).get("ogrId");

    useEffect(() => {
      axiosconfig.get("ogretmen/getbyid/" + ogrId, getAxiosHeaders()).then((response) => {
        try {
          setOgretmen(response.data);
        } catch (err) {}
      });
    }, []);
  
    return (
      <div>
        {ogretmen != null ? (
          <Stack gap={3}>
            <Card>
              <Card.Body className="shadow">
                <Card.Title>{ogretmen.name}</Card.Title>
                <Card.Text>
                  {ogretmen.is_GICIK ? "Gıcık bir hocadır" : "Normal bir hocadır"}
                </Card.Text>
              </Card.Body>
            </Card>
            <NavLink className="btn btn-danger" to="/ogretmen">Geri</NavLink>
          </Stack>
        ) : (
          <Row className="justify-content-center">
            <Spinner animation="border" variant="primary"></Spinner>
          </Row>
        )}
      </div>
    );
  }
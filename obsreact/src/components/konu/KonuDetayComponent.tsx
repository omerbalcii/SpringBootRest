import { useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Card, Row, Spinner, Stack } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { IKonu } from "../model/IKonu";
import { getAxiosHeaders } from "../utils/Utils";

export default function KonuDetayComponent() {
  const [konu, setKonu] = useState<IKonu>();

  // react router ile aşağıdaki gibi alınabilir ? ile gelen parametreler
  // let [searchParams, setSearchParams] = useSearchParams();
  // let konuId = searchParams.get("konuId");

  let konuId = new URLSearchParams(window.location.search).get("konuId");

  useEffect(() => {
    axiosconfig.get("konu/getbyid/" + konuId, getAxiosHeaders()).then((response) => {
      try {
        setKonu(response.data);
      } catch (err) {}
    });
  }, []);

  return (
    <div>
      {konu != null ? (
        <Stack gap={3}>
          <Card>
            <Card.Body className="shadow">
              <Card.Title>{konu.name}</Card.Title>
            </Card.Body>
          </Card>

          <NavLink className="btn btn-danger" to="/konu">
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

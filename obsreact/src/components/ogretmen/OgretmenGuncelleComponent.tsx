import React, { FormEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosconfig from "../utils/axiosconfig";
import {
  Button,
  Card,
  Form,
  FormCheck,
  FormControl,
  Row,
  Spinner,
} from "react-bootstrap";
import { IOgretmen } from "../model/IOgretmen";
import { getAxiosHeaders } from "../utils/Utils";

export default function OgretmenGuncelleComponent() {
  let ogrId = new URLSearchParams(window.location.search).get("ogrId");
  const [ogrName, setOgrName] = useState<string>("");
  const [ogrIsGicik, setOgrIsGicik] = useState<boolean>(false);
  const [isloading, setisloading] = useState<boolean>(true);

  const mynavigate = useNavigate();

  useEffect(() => {
    axiosconfig.get("ogretmen/getbyid/" + ogrId, getAxiosHeaders()).then((response) => {
      try {
        const ogretmen: IOgretmen = response.data;
        setOgrName(ogretmen.name);
        setOgrIsGicik(ogretmen.is_GICIK);
        setisloading(false);
      } catch (err) {}
    });
  }, []);

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    var ogretmen: IOgretmen = { id: 0, name: "", is_GICIK: false };
    ogretmen.id = parseInt(ogrId + "");
    ogretmen.name = ogrName;
    ogretmen.is_GICIK = ogrIsGicik;
    axiosconfig.post("ogretmen/update", ogretmen, getAxiosHeaders()).then((res) => {
      if (res.status === 200) {
        mynavigate("/ogretmen");
      }
    });
  }

  return (
    <div>
      {!isloading ? (
        <Card>
          <Card.Body className="shadow">
            <Form method="post" onSubmit={(e) => handleSubmit(e)}>
              <FormControl
                type="text"
                placeholder="İsim"
                value={ogrName}
                onChange={(e) => {
                  setOgrName(e.target.value);
                }}
              ></FormControl>
              <FormCheck
                checked={ogrIsGicik}
                label="Is Gıcık ?"
                onChange={(e) => setOgrIsGicik(e.target.checked)}
              ></FormCheck>
              <Button variant="outline-primary" type="submit">
                Güncelle
              </Button>
            </Form>
          </Card.Body>
        </Card>
      ) : (
        <Row className="justify-content-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}
    </div>
  );
}

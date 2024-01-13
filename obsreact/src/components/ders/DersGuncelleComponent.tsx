import { FormEvent, useEffect, useState } from "react";
import { IKonu } from "../model/IKonu";
import { IOgretmen } from "../model/IOgretmen";
import { useNavigate } from "react-router-dom";
import axiosconfig from "../utils/axiosconfig";
import {
  Button,
  Card,
  Form,
  FormSelect,
  Row,
  Spinner,
  Stack,
  Toast,
} from "react-bootstrap";
import { IDersDTO } from "../model/IDersDTO";
import { getAxiosHeaders } from "../utils/Utils";

export default function DersGuncelleComponent() {
  let dersId = new URLSearchParams(window.location.search).get("dersId");
  const [selectedKonuID, setSelectedKonuID] = useState<number>(-1);
  const [selectedOgretmenID, setselectedOgretmenID] = useState<number>(-1);
  const [konular, setKonular] = useState<IKonu[]>([]);
  const [ogretmenler, setOgretmenler] = useState<IOgretmen[]>([]);
  const mynavigate = useNavigate();
  const [ogretmenToast, setOgretmenToast] = useState<boolean>(false);
  const [konuToast, setKonuToast] = useState<boolean>(false);

  useEffect(() => {
    axiosconfig.get("/ders/getbyid/dto/" + dersId, getAxiosHeaders()).then((response) => {
      try {
        const ders: IDersDTO = response.data;
        setselectedOgretmenID(ders.ogretmen.id);
        setSelectedKonuID(ders.konu.id);
        axiosconfig.get("/ogretmen/getall", getAxiosHeaders()).then((response) => {
          try {
            setOgretmenler(response.data);
          } catch (err) {}
        });
        axiosconfig.get("/konu/getall", getAxiosHeaders()).then((response) => {
          try {
            setKonular(response.data);
          } catch (err) {}
        });
      } catch (err) {}
    });
  }, []);

  function showToastOgretmen() {
    return (
      <Toast
        onClose={() => setOgretmenToast(false)}
        show={ogretmenToast}
        delay={2000}
        autohide
      >
        <Toast.Header>
          <strong className="me-auto">Bootstrap</strong>
        </Toast.Header>
        <Toast.Body>Öğretmen seçiniz</Toast.Body>
      </Toast>
    );
  }
  function showToastKonu() {
    return (
      <Toast
        onClose={() => setKonuToast(false)}
        show={konuToast}
        delay={2000}
        autohide
      >
        <Toast.Header>
          <strong className="me-auto">Bootstrap</strong>
        </Toast.Header>
        <Toast.Body>Konu seçiniz</Toast.Body>
      </Toast>
    );
  }

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (selectedOgretmenID === -1) {
      setOgretmenToast(true);
      return;
    }
    if (selectedKonuID === -1) {
      setKonuToast(true);
      return;
    }
    const ders =
      '{"id":' +
      dersId +
      ',"konu_ID":' +
      selectedKonuID +
      ',"ogretmen_ID":' +
      selectedOgretmenID +
      "}";
    axiosconfig.post("/ders/update", ders).then((res) => {
      if (res.status === 200) {
        mynavigate("/ders");
      }
    });
  }

  return (
    <Stack gap={3}>
      <Card>
        <Card.Body className="shadow">
          <Form method="post" onSubmit={(e) => handleSubmit(e)}>
            {ogretmenler.length > 0 ? (
              <FormSelect
                onChange={(e) =>
                  setselectedOgretmenID(Number.parseInt(e.target.value))
                }
                defaultValue={selectedOgretmenID}
              >
                <option key={-1} value={-1}>
                  Öğretmen seçiniz
                </option>
                {ogretmenler.map((ogretmen, i) => (
                  <option key={i} value={ogretmen.id}>
                    {ogretmen.name}
                  </option>
                ))}
              </FormSelect>
            ) : (
              <Row className="justify-content-center">
                <Spinner animation="border" variant="primary"></Spinner>
              </Row>
            )}
            {konular.length > 0 ? (
              <FormSelect
                onChange={(e) =>
                  setSelectedKonuID(Number.parseInt(e.target.value))
                }
                defaultValue={selectedKonuID}
              >
                <option key={-1} value={-1}>
                  Konu seçiniz
                </option>
                {konular.map((konu, i) => (
                  <option key={i} value={konu.id}>
                    {konu.name}
                  </option>
                ))}
              </FormSelect>
            ) : (
              <Row className="justify-content-center">
                <Spinner animation="border" variant="primary"></Spinner>
              </Row>
            )}
            <Button type="submit">Güncelle</Button>
          </Form>
        </Card.Body>
      </Card>
      {showToastOgretmen()}
      {showToastKonu()}
    </Stack>
  );
}

import React, { FormEvent, useEffect, useState } from "react";
import { Button, Card, Form, FormSelect, Row, Spinner, Stack, Toast } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axiosconfig from "../utils/axiosconfig";
import { IKonu } from "../model/IKonu";
import { IOgretmen } from "../model/IOgretmen";
import { getAxiosHeaders } from "../utils/Utils";

export default function DersKaydetComponent() {
  const [selectedKonuID, setSelectedKonuID] = useState<number>(-1);
  const [selectedOgretmenID, setselectedOgretmenID] = useState<number>(-1);
  const [konular, setKonular] = useState<IKonu[]>([]);
  const [ogretmenler, setOgretmenler] = useState<IOgretmen[]>([]);
  const mynavigate = useNavigate();
  const [ogretmenToast, setOgretmenToast] = useState<boolean>(false);
  const [konuToast, setKonuToast] = useState<boolean>(false);

  useEffect(() => {
    // iki sitek asenkron çalışıyor ve paralel sonuç bekliyor
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
  }, []);

  function showToastOgretmen() {
    return (
      <Toast onClose={() => setOgretmenToast(false)} show={ogretmenToast} delay={2000} autohide>
        <Toast.Header>
          <strong className="me-auto">Uyarı</strong>
        </Toast.Header>
        <Toast.Body>Öğretmen seçiniz</Toast.Body>
      </Toast>
    );
  }
  function showToastKonu() {
    return (
      <Toast onClose={() => setKonuToast(false)} show={konuToast} delay={2000} autohide>
        <Toast.Header>
          <strong className="me-auto">Uyarı</strong>
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
    // içerisinde _ olan json verileri serialize edilemiyor
    // buna çözüm bulunması gerekiyor, daha hoş bir çözüm de vardır
    // const sendData = {
    //  {"ogretmen_ID":2,"konu_ID":2}
    // };
    const sendData = '{"ogretmen_ID" : ' + selectedOgretmenID + ', "konu_ID" : ' + selectedKonuID + "}";
    axiosconfig.post("ders/save", sendData, getAxiosHeaders()).then((res) => {
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
              <FormSelect onChange={(e) => setselectedOgretmenID(Number.parseInt(e.target.value))}>
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
              <FormSelect onChange={(e) => setSelectedKonuID(Number.parseInt(e.target.value))}>
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
            <Button type="submit">Kaydet</Button>
          </Form>
        </Card.Body>
      </Card>
      {showToastOgretmen()}
      {showToastKonu()}
    </Stack>
  );
}

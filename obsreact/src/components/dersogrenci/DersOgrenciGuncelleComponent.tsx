import React, { FormEvent, useEffect, useState } from "react";
import { IDersDTO } from "../model/IDersDTO";
import { IOgrenci } from "../model/IOgrenci";
import { useNavigate } from "react-router-dom";
import axiosconfig from "../utils/axiosconfig";
import { Button, Card, Col, Form, FormControl, FormLabel, FormSelect, Row, Spinner, Stack, Toast } from "react-bootstrap";
import { IDersOgrenci } from "../model/IDersOgrenci";
import { getAxiosHeaders } from "../utils/Utils";

export default function DersOgrenciGuncelleComponent() {
  let dersOgrenciId = new URLSearchParams(window.location.search).get("dersOgrenciId");
  const [selectedDersDtoId, setSelectedDersDtoId] = useState<number>(-1);
  const [selectedOgrenciId, setselectedOgrenciId] = useState<number>(-1);
  const [devamsizlik, setDevamsizlik] = useState<number>(0);
  const [note, setNote] = useState<number>(0);
  const [dersler, setDersler] = useState<IDersDTO[]>([]);
  const [ogrenciler, setogrenciler] = useState<IOgrenci[]>([]);
  const mynavigate = useNavigate();
  const [dersToast, setDersToast] = useState<boolean>(false);
  const [ogrenciToast, setOgrenciToast] = useState<boolean>(false);

  useEffect(() => {
    axiosconfig.get("/dersogrenci/getbyid/" + dersOgrenciId, getAxiosHeaders()).then((response) => {
      const dersOgrenci: IDersOgrenci = response.data;
      setNote(dersOgrenci.note);
      setDevamsizlik(dersOgrenci.devamsizlik);
      setSelectedDersDtoId(dersOgrenci.ders_ID);
      setselectedOgrenciId(dersOgrenci.ogrenci_ID);
      axiosconfig.get("/ders/getalldto", getAxiosHeaders()).then((response) => {
        try {
          setDersler(response.data);
        } catch (err) {}
      });
      axiosconfig.get("/ogrenci/getall", getAxiosHeaders()).then((response) => {
        try {
          setogrenciler(response.data);
        } catch (err) {}
      });
    });
  }, []);

  function showToastOgretmen() {
    return (
      <Toast onClose={() => setDersToast(false)} show={dersToast} delay={2000} autohide>
        <Toast.Header>
          <strong className="me-auto">Uyarı</strong>
        </Toast.Header>
        <Toast.Body>Ders seçiniz</Toast.Body>
      </Toast>
    );
  }
  function showToastKonu() {
    return (
      <Toast onClose={() => setOgrenciToast(false)} show={ogrenciToast} delay={2000} autohide>
        <Toast.Header>
          <strong className="me-auto">Uyarı</strong>
        </Toast.Header>
        <Toast.Body>Öğrenci seçiniz</Toast.Body>
      </Toast>
    );
  }

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (selectedDersDtoId === -1) {
      setDersToast(true);
      return;
    }
    if (selectedOgrenciId === -1) {
      setOgrenciToast(true);
      return;
    }
    const sendData = '{"id" : ' + dersOgrenciId + ', "ders_ID" : ' + selectedDersDtoId + ', "ogrenci_ID" : ' + selectedOgrenciId + ', "devamsizlik" : ' + devamsizlik + ', "note" : ' + note + "}";
    axiosconfig.post("dersogrenci/update", sendData).then((res) => {
      if (res.status === 200) {
        mynavigate("/dersogrenci");
      }
    });
  }

  return (
    <Stack gap={3}>
      <Card>
        <Card.Body className="shadow">
          <Form method="post" onSubmit={(e) => handleSubmit(e)}>
            <Stack gap={3}>
              {dersler.length > 0 ? (
                <FormSelect onChange={(e) => setSelectedDersDtoId(Number.parseInt(e.target.value))} defaultValue={selectedDersDtoId}>
                  <option key={-1} value={-1}>
                    Ders seçiniz
                  </option>
                  {dersler.map((ders, i) => (
                    <option key={i} value={ders.id}>
                      {ders.ogretmen.name + " - " + ders.konu.name}
                    </option>
                  ))}
                </FormSelect>
              ) : (
                <Row className="justify-content-center">
                  <Spinner animation="border" variant="primary"></Spinner>
                </Row>
              )}
              {ogrenciler.length > 0 ? (
                <FormSelect onChange={(e) => setselectedOgrenciId(Number.parseInt(e.target.value))} defaultValue={selectedOgrenciId}>
                  <option key={-1} value={-1}>
                    Öğrenci seçiniz
                  </option>
                  {ogrenciler.map((ogrenci, i) => (
                    <option key={i} value={ogrenci.id}>
                      {ogrenci.name + " - " + ogrenci.ogr_NUMBER + " - " + ogrenci.year}
                    </option>
                  ))}
                </FormSelect>
              ) : (
                <Row className="justify-content-center">
                  <Spinner animation="border" variant="primary"></Spinner>
                </Row>
              )}
              <Col className="auto">
                <FormLabel>Devamsizlık</FormLabel>
                <FormControl
                  type="number"
                  placeholder="Devamsızlık"
                  value={devamsizlik}
                  onChange={(e) => {
                    setDevamsizlik(parseInt(e.target.value));
                  }}
                ></FormControl>
              </Col>
              <Col className="auto">
                <FormLabel>Note</FormLabel>
                <FormControl
                  type="number"
                  placeholder="Not"
                  value={note}
                  onChange={(e) => {
                    setNote(parseInt(e.target.value));
                  }}
                ></FormControl>
              </Col>
              <Button type="submit">Güncelle</Button>
            </Stack>
          </Form>
        </Card.Body>
      </Card>
      {showToastOgretmen()}
      {showToastKonu()}
    </Stack>
  );
}

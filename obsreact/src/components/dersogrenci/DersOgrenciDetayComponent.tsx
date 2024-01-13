import React, { useEffect, useState } from 'react'
import { IDersOgrenciDTO } from '../model/IDersOgrenciDTO';
import axiosconfig from '../utils/axiosconfig';
import { Card, FormLabel, Row, Spinner, Stack } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';
import { getAxiosHeaders } from '../utils/Utils';

export default function DersOgrenciDetayComponent() {
    const [dersOgrenciDTO, setDersOgrenciDTO] = useState<IDersOgrenciDTO>();
    let dersOgrenciId = new URLSearchParams(window.location.search).get("dersOgrenciId");
  
    useEffect(() => {
      axiosconfig.get("dersogrenci/getbyid/dto/" + dersOgrenciId, getAxiosHeaders()).then((response) => {
        try {
          setDersOgrenciDTO(response.data);
        } catch (err) {}
      });
    }, []);
  
    return (
      <div>
        {dersOgrenciDTO != null ? (
          <Stack gap={3}>
            <Card>
              <Card.Body className="shadow">
                <Card.Title>{"Ders Kayıt Id = " + dersOgrenciDTO.id + " - Devamsızlık " + dersOgrenciDTO.devamsizlik + " - Note " + dersOgrenciDTO.note}</Card.Title>
                <Card.Text>
                  <Stack>
                    <FormLabel>{dersOgrenciDTO.ogrenci.name}</FormLabel>
                    <FormLabel>{dersOgrenciDTO.ders.ogretmen.name}</FormLabel>
                    <FormLabel>{dersOgrenciDTO.ders.konu.name}</FormLabel>
                  </Stack>
                </Card.Text>
              </Card.Body>
            </Card>
            <NavLink className="btn btn-danger" to="/dersogrenci">
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
  
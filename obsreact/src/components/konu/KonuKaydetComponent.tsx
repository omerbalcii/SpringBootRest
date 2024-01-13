import React, { FormEvent, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import axiosconfig from '../utils/axiosconfig';
import { getAxiosHeaders } from '../utils/Utils';
import { Button, Card, Form, FormControl } from 'react-bootstrap';

export default function KonuKaydetComponent() {
    const [konuName, setkonuName] = useState<string>("");
  
    const mynavigate = useNavigate();
  
    function handleSubmit(event: FormEvent<HTMLFormElement>) {
      event.preventDefault();
      const sendData = {
        name: konuName,
      };
      axiosconfig.post("konu/save", sendData, getAxiosHeaders()).then((res) => {
        if (res.status === 201) {
          mynavigate("/konu");
        }
      });
    }
  
    return (
      <Card>
        <Card.Body className="shadow">
          <Form method="post" onSubmit={(e) => handleSubmit(e)}>
            <FormControl
              type="text"
              placeholder="Konu adı"
              onChange={(e) => {
                setkonuName(e.target.value);
              }}
            ></FormControl>
            <Button variant="outline-primary" type="submit">
              Kaydet
            </Button>
          </Form>
        </Card.Body>
      </Card>
    );
  }
  
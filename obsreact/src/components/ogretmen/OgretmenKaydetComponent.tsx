import { FormEvent, useState } from "react";
import { Button, Card, Form, FormCheck, FormControl } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axiosconfig from "../utils/axiosconfig";
import { getAxiosHeaders } from "../utils/Utils";

export default function OgretmenKaydetComponent() {
  // state olmasa da gayet normal olabilirdi
  // bu örneği state siz yapmaya çalışabilirsiniz, mesela useref veya form kütüphaneleri
  const [ogrName, setOgrName] = useState<string>("");
  const [ogrIsGicik, setOgrIsGicik] = useState<boolean>(false);

  const mynavigate = useNavigate();

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    // form sayfayı post ettirmesin diye
    event.preventDefault();
    // burası görünemse de bir json veya bir class
    // burada IOgretmen de kullanılabilir ama id 'ye ihtiyacım yok
    const sendData = {
      name: ogrName,
      is_GICIK: ogrIsGicik,
    };
    axiosconfig.post("ogretmen/save", sendData, getAxiosHeaders()).then((res) => {
      if (res.status === 200) {
        // navlink yerine router üzerinden navigate
        // /ogretmen linkine tıklamış gibi
        mynavigate("/ogretmen");
      }
    });
  }

  return (
    <Card>
      <Card.Body className="shadow">
        <Form method="post" onSubmit={(e) => handleSubmit(e)}>
          <FormControl
            type="text"
            placeholder="İsim"
            onChange={(e) => {
              setOgrName(e.target.value);
            }}
          ></FormControl>
          <FormCheck
            label="Is Gıcık ?"
            onChange={(e) => setOgrIsGicik(e.target.checked)}
          ></FormCheck>
          <Button variant="outline-primary" type="submit">
            Ekle
          </Button>
        </Form>
      </Card.Body>
    </Card>
  );
}

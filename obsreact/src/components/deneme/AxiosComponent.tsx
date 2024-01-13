import axios, { AxiosResponse } from "axios";
import { log } from "console";
import { EffectCallback, useEffect, useState } from "react";
import { Button, Spinner, Table } from "react-bootstrap";

export default function AxiosComponent() {
  interface Ogretmen {
    id: string;
    name: string;
    is_GICIK: boolean;
  }

  const [ogretmenler, setOgretmenler] = useState<Ogretmen[]>([]);

  // useeffect 'in tekrar çalışmasını isterseniz [] içine değişkenler koyabilirsiniz
  // useEffect(myConstructor(), []);
  useEffect(() => {
    return () => {
      axios.get("http://localhost:8080/ogretmen/getall").then(thenfunction);
    };
  }, []);

  // function myConstructor(): EffectCallback
  // {
  //   return () => {
  //     axios.get("http://localhost:8080/ogretmen/getall").then(thenfunction);
  //   }
  // }

  function thenfunction(response: AxiosResponse) {
    setOgretmenler(response.data);
    console.log(response.status);
    
  }

  return (
    <>
      {ogretmenler.length == 0 && <Spinner></Spinner>}
      <Table>
        <thead>
          <tr>
            <td>ID</td>
            <td>NAME</td>
            <td>IS_GICIK</td>
            <td>detay</td>
            <td>sil</td>
          </tr>
        </thead>
        <tbody>
          {
            ogretmenler.length == 0 && <tr><td><Spinner></Spinner></td><td><Spinner></Spinner></td><td><Spinner></Spinner></td><td><Spinner></Spinner></td><td><Spinner></Spinner></td></tr>
          }
          {ogretmenler.map((ogretmen) => {
            return (
              <tr key={ogretmen.id}>
                <td>{ogretmen.id}</td>
                <td>{ogretmen.name}</td>
                <td>{ogretmen.is_GICIK ? "true" : "false"}</td>
                <td>
                  <Button>detay</Button>
                </td>
                <td>
                  <Button>sil</Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </>
  );
}

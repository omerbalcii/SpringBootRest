import { Button, FormLabel } from "react-bootstrap";
import { JsxElement } from "typescript";

function DenemeComponent() {
  return (
    <div className="container">
      {
      Husamettin("faruk")
      }
      <Abuziddin isim="my btn" yas={788}></Abuziddin>
    </div>
  );
}

function Serafettin()
{
}

function Husamettin(isim: string) {
  return (
    <div>
      <p>{isim}</p>
      <a href="https://reactjs.org" target="_blank" rel="noopener noreferrer">
        Learn React
      </a>
    </div>
  );
}

interface AbuziddinProps
{
  isim : string;
  yas : number;
}

function Abuziddin({ isim, yas } : AbuziddinProps) {
  return (
    <div>
      <br />
      <Button>{isim}</Button>
      <FormLabel>{yas}</FormLabel>
    </div>
  );
}

// fonksiyonu dışarıya aktar
export default DenemeComponent;

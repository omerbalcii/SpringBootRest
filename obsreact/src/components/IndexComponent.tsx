export default function IndexComponent() {
  return <div>Hello React JS {<h4>{localStorage.getItem("username")}</h4>}</div>;
}

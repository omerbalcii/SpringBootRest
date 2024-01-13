import { AxiosRequestConfig } from "axios";

export function getAxiosHeaders(): AxiosRequestConfig {
  return {
    headers: localStorage.getItem("userjwt") !== null ? { Authorization: "Bearer " + localStorage.getItem("userjwt"), "Content-Type": "application/json" } : { "Content-Type": "application/json" },
  };
}

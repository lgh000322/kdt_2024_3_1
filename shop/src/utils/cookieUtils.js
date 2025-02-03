import { Cookies } from "react-cookie";

const cookies = new Cookies();

export const getCookies = (name) => {
  return cookies.get(name);
};

export const removeCookie = (name, path = "/") => {
  cookies.remove(name, { path: path });
};

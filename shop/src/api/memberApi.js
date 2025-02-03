import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/member`;

export const getMemberInfo = async (accessToken) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  console.log("accessToken= ", accessToken);
  const res = await axios.get(`${preFix}`, header);

  return res.data;
};

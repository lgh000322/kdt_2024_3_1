import axios from "axios";
import { ApiHost } from "./ApiConst";

const prefix = `${ApiHost}/refresh`;

export const refreshTokens = async () => {
  const header = {
    withCredentials: true,
  };

  try {
    const res = await axios.get(prefix, header);
    return res.data;
  } catch (error) {
    console.log("토큰 재발행 실패");
    throw new Error("토큰 갱신 실패");
  }
};

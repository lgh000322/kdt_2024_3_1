import axios from "axios";
import { ApiHost } from "./ApiConst";

const prefix = `${ApiHost}/likes`;

export const getWishList = async (accessToken, page, size) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.get(
    `${prefix}/items?page=${page}&size=${size}`,
    header
  );
  return res.data;
};

export const deleteWishItem = async (accessToken, likesItemId, productId) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.delete(
    `${prefix}/item/${likesItemId}?productId=${productId}`,
    header
  );
  return res.data;
};

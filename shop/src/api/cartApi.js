import axios from "axios";
import { ApiHost } from "./ApiConst";

const prefix = `${ApiHost}/cart`;

// 장바구니 목록 조회
export const getCartItem = async (accessToken, page, size) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.get(`${prefix}?page=${page}&size=${size}`, header);
  return res.data;
};

// 장바구니에 상품 추가
export const addCartItem = async (accessToken, productId, quantity) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.post(
    `${prefix}`,
    { productId, quantity },
    header
  );
  return res.data;
};

// 장바구니 아이템 수량 변경
export const updateCartItemQuantity = async (accessToken, cartItemId, quantity) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.put(
    `${prefix}/${cartItemId}`,
    { quantity },
    header
  );
  return res.data;
};

// 장바구니 아이템 삭제
export const removeCartItem = async (accessToken, cartItemId) => {
    const header = {
      headers: { Authorization: `Bearer ${accessToken}` },
      withCredentials: true,
    };
  
    const res = await axios.delete(`${prefix}/${cartItemId}`, header);
    return res.data;
};

// 복수의 장바구니 아이템 삭제
export const deleteMultipleCartItems = async (accessToken, cartItemIds) => {
    const header = {
        headers: { Authorization: `Bearer ${accessToken}` },
        withCredentials: true,
    };

    const res = await axios.delete(`${prefix}/items`, {
        data: { cartItemIds },  // DELETE 요청 바디에 데이터 전달
        ...header
    });
    
    return res.data;
};  
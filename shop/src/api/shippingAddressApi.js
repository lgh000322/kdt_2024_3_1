import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/destination`;

// 배송지 추가 API
export const addShippingAddress = async (addressData, token) => {
  try {
    const response = await axios.post(preFix, addressData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};

// 배송지 수정 API
export const updateShippingAddress = async (addressId, updatedData, token) => {
  try {
    const response = await axios.put(`${preFix}/${addressId}`, updatedData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};

import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/destination`;

// 배송지 목록 조회 API
export const getShippingAddresses = async (token) => {
  try {
    const response = await axios.get(preFix, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("✅ 배송지 응답 데이터: ", response.data);
    return response.data.data;  // ✅ 여기서 data 안의 data 배열을 반환해야 함
  } catch (error) {
    console.error("❌ 배송지 목록 불러오기 실패:", error.response?.data || error.message);
    throw error.response?.data || error.message;
  }
};

// 배송지 추가 API
export const addShippingAddress = async (newAddress, token) => {
  try {
    const response = await axios.post(preFix, newAddress, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    console.error("❌ 배송지 추가 실패:", error.response?.data || error.message);
    throw error.response?.data || error.message;
  }
};

// 배송지 수정 API
export const updateShippingAddress = async (destinationId, updatedData, token) => {
  try {
    const response = await axios.put(`${preFix}/${destinationId}`, updatedData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    console.error("❌ 배송지 수정 실패:", error.response?.data || error.message);
    throw error.response?.data || error.message;
  }
};

//배송지 삭제 API
export const deleteShippingAddress = async (destinationId, token) => {
  try {
    const response = await axios.delete(`${preFix}/${destinationId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    console.error("❌ 배송지 삭제 실패:", error.response?.data || error.message);
    throw error.response?.data || error.message;
  }
};

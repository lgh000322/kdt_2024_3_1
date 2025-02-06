import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/destination`;

// 배송지 목록 조회 API
export const getShippingAddresses = async (token) => {
  try {
    console.log("🚀 배송지 목록 요청: ", preFix);
    console.log("🛠️ Authorization 헤더: ", token);

    const response = await axios.get(preFix, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("✅ 배송지 응답 데이터: ", response.data);
    return response.data.data; // 서버 응답에서 data 부분 추출
  } catch (error) {
    console.error("❌ 배송지 목록 불러오기 실패:", error.response?.data || error.message);
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
    throw error.response?.data || error.message;
  }
};

import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/orderHistory`;

// 주문내역 목록 조회 API
export const getOrderHistory = async (token, page = 0, size = 10) => {
  try {
    console.log("📡 서버 요청 데이터:", { page, size });

    const response = await axios.get(preFix, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: { page, size },
    });

    console.log("✅ 서버 응답 데이터:", response.data);

    return response.data?.data || [];
  } catch (error) {
    console.error(
      "❌ 주문내역 목록 불러오기 실패:",
      error.response?.data || error.message
    );
    throw error.response?.data || error.message;
  }
};

export const deleteOrderHistory = async (orderHistoryId, token) => {
  try {
    console.log("삭제할 주문내역 ID값:", orderHistoryId);

    const response = await axios.delete(`${preFix}/${orderHistoryId}`, {
      headers: {
        Authorization: `Bearer ${token}`, // ✅ JWT 포함
      },
    });
    console.log("✅ 주문내역 삭제 성공:", response.data);
    return response.data;
  } catch (error) {
    console.error(
      "❌ 주문내역 삭제 실패:",
      error.response?.data || error.message
    );
    throw error.response?.data || error.message;
  }
};

export const postOrderHistory = async (accessToken, orderRequest) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.post(`${preFix}`, orderRequest, header);
  return res.data;
};

export const postOrderHistoryAfterPay = async (
  accessToken,
  orderHistoryId,
  deliveryStatus
) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.put(
    `${preFix}/${orderHistoryId}`,
    deliveryStatus,
    header
  );
  return res.data;
};

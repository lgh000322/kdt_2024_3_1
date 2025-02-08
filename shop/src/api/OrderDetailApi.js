import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/orderProduct`;

export const showOrderDetail = async (orderHistoryId, token) => {
  if (!orderHistoryId || !token) {
    console.log("주문내역 Id; ", orderHistoryId);
    console.error("❌ 필수 정보 누락: orderHistoryId 또는 token이 없습니다.");
    return;
  }

  try {
    const response = await axios.get(`${preFix}/${orderHistoryId}`, {
      headers: {
        Authorization: `Bearer ${token}`, // ✅ JWT 포함
      },
      withCredentials: true, // ✅ 필요한 경우 추가 (CORS 문제 해결)
    });

    console.log("✅ 주문 상세 조회 성공:", response.data);
    return response.data?.data || null;
  } catch (error) {
    console.error(
      "❌ 주문 상세 조회 실패:",
      error.response?.data || error.message
    );
    throw error.response?.data || error.message;
  }
};

//주문상품 배송 업데이트
// function updateDeliveryState() {}

//주문상품 배송 조회
// function searchDeliveryState() {}

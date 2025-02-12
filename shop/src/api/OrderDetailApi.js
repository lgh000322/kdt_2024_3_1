import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/orderProduct`;

export const showOrderDetail = async (orderHistoryId, accessToken) => {
  const header = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    withCredentials: true,
  };

  const res = await axios.get(`${preFix}/${orderHistoryId}`, header);
  return res.data;
};

//주문상품 배송 업데이트
// function updateDeliveryState() {}

//주문상품 배송 조회
// function searchDeliveryState() {}

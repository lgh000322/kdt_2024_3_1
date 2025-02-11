import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import useCustomMove from "../hook/useCustomMove";
import { postOrderHistoryAfterPay } from "../api/OrderListApi";
import { useSelector } from "react-redux";

function CheckoutHandler() {
  const [searchParams] = useSearchParams();
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;
  const orderHistoryId = searchParams.get("customId");
  const { moveToProductAbs } = useCustomMove();

  useEffect(() => {
    const deliveryStatus = {
      deliveryStatus: "BEFORE_DELIVERY",
    };
    postOrderHistoryAfterPay(accessToken, orderHistoryId, deliveryStatus).then(
      (res) => {
        if (res.code == 200) {
          alert("결제 성공");
          moveToProductAbs();
        }
      }
    );
  }, []);
}

export default CheckoutHandler;

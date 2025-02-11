import React, { useEffect } from "react";
import useCustomMove from "../hook/useCustomMove";

function CheckoutFailHandler() {
  const { moveToProductAbs } = useCustomMove();

  useEffect(() => {
    moveToProductAbs();
  }, []);
}

export default CheckoutFailHandler;

import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import useCustomMove from "../hook/useCustomMove";

function LoginHandler() {
  const [searchParams] = useSearchParams();
  const { moveToProductAbs, moveToAuthenticated } = useCustomMove();

  const isAuthenticated = searchParams.get("isAuthenticated");

  useEffect(() => {
    if (isAuthenticated === true) {
      moveToProductAbs();
    } else {
      moveToAuthenticated();
    }
  }, []);
}

export default LoginHandler;

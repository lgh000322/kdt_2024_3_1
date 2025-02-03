import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import useCustomMove from "../hook/useCustomMove";
import { getCookies } from "../utils/cookieUtils";
import { getRoleFromAccessToken } from "../utils/jwtUtils";

function LoginHandler() {
  const [searchParams] = useSearchParams();
  const { moveToProductAbs, moveToAuthenticated, doLogin } = useCustomMove();
  const isAuthenticated = searchParams.get("isAuthenticated");
  const accessToken = getCookies("accessToken");
  const role = getRoleFromAccessToken(accessToken);

  useEffect(() => {
    if (accessToken && role) {
      // accessToken과 role이 있는 경우에만 로그인 처리
      doLogin(accessToken, role);
    }
    if (isAuthenticated === "true") {
      moveToProductAbs();
    } else {
      moveToAuthenticated();
    }
  }, []);
}

export default LoginHandler;

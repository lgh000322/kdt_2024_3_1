import { createSlice } from "@reduxjs/toolkit";
import { getCookies, removeCookie, setCookie } from "../utils/cookieUtils";
import { getRoleFromAccessToken } from "../utils/jwtUtils";

const initState = {
  accessToken: "",
  role: [],
};

const loadState = () => {
  const accessToken = getCookies("accessToken");

  if (!accessToken) return initState;

  // JWT의 Payload 부분 가져오기
  const base64Url = accessToken.split(".")[1];

  if (!base64Url) return initState;

  // Base64 디코딩
  try {
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const decodedPayload = JSON.parse(atob(base64));

    // role 정보가 배열일 가능성 고려
    const role = Array.isArray(decodedPayload.role)
      ? decodedPayload.role
      : [decodedPayload.role]; // 배열로 변환

    return { accessToken, role };
  } catch (error) {
    console.error("Failed to decode JWT:", error);
    return initState;
  }
};

const loginSlice = createSlice({
  name: "loginSlice",
  initialState: loadState() || initState,
  reducers: {
    login: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.role = Array.isArray(action.payload.role)
        ? action.payload.role
        : [action.payload.role]; // role이 배열인지 확인 후 배열로 설정
    },
    logout: (state) => {
      removeCookie("accessToken");
      state.accessToken = "";
      state.role = [];
    },
    refresh: (state, action) => {
      removeCookie("accessToken");
      removeCookie("refreshToken");

      state.accessToken = action.payload.accessToken;
      state.role = getRoleFromAccessToken(state.accessToken);

      setCookie("accessToken", action.payload.refreshToken, 10 / 1440);
      setCookie("refreshToken", action.payload.accessToken, 1);
    },
  },
});

export const { login, logout } = loginSlice.actions;
export default loginSlice.reducer;

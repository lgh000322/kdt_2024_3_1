import { createSlice } from "@reduxjs/toolkit";
import { getCookies, removeCookie, setCookie } from "../utils/cookieUtils";
import { getRoleFromAccessToken } from "../utils/jwtUtils";

const initState = {
  accessToken: "",
  role: "",
};

const loadState = () => {
  const accessToken = getCookies("accessToken");
  if (!accessToken) return initState;
  const role = getRoleFromAccessToken(accessToken);
  return { accessToken, role };
};

const loginSlice = createSlice({
  name: "loginSlice",
  initialState: loadState(),
  reducers: {
    login: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.role = action.payload.role;
    },
    logout: (state) => {
      removeCookie("accessToken");
      state.accessToken = ""; // initState로 상태 재설정
      state.role = "";
    },
    refresh: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.role = Array.isArray(action.payload.role)
        ? action.payload.role
        : [action.payload.role]; // role이 배열인지 확인 후 배열로 설정
    },
  },
});

export const { login, logout, refresh } = loginSlice.actions;
export default loginSlice.reducer;

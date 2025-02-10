import axios from "axios";
import { ApiHost } from "./ApiConst";
import { refreshTokens } from "./refreshApi";
import store from "../store";
import { refresh } from "../slice/loginSlice";
import { getCookies } from "../utils/cookieUtils";
import { getRoleFromAccessToken } from "../utils/jwtUtils";

const preFix = `${ApiHost}/member`;
const adminlist = `${ApiHost}/members`;
const selleraccept = `${ApiHost}/authority`;  
const selleracceptsubmit = `${ApiHost}/authority`;
const selleracceptfile = `${ApiHost}/authority/file`;



// 처음 로그인 했을 때 로그인 한 회원의 정보 조회
export const getMemberInfo = async (accessToken) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  try {
    // 처음 api 호출
    const res = await axios.get(`${preFix}`, header);
    return res.data;
  } catch (error) {
    // accessToken이 만료되었을 때 refreshToken으로 토큰 2개 재발급 후 이전 요청 재시도
    if (error.response?.data?.message === "만료된 JWT입니다.") {
      try {
        await refreshTokens();

        store.dispatch(refresh());

        // 새로운 accessToken으로 원래 요청 재시도
        const newAccessToken = getCookies("accessToken");

        const newHeader = {
          headers: { Authorization: `Bearer ${newAccessToken}` },
          withCredentials: true,
        };

        const retryRes = await axios.get(`${preFix}`, newHeader);
        return retryRes.data;
      } catch (refreshError) {
        throw new Error("토큰 갱신 요청 재시도 실패");
      }
    }
  }
};

// 회원 업데이트
export const updateMember = async (updateMemberInfo, accessToken) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  if (updateMemberInfo.gender === "남자") {
    updateMemberInfo.gender = "MALE";
  } else {
    updateMemberInfo.gender = "FEMALE";
  }

  const res = await axios.put(`${preFix}`, updateMemberInfo, header);
  return res.data;
};

// 로그아웃 시 호출
export const logoutRefresh = async () => {
  const header = {
    withCredentials: true,
  };

  const res = await axios.post(`${preFix}/logout`, null, header);

  return res.data;
};

// 전체 회원 조회
export const getMembers = async (accessToken, page, size,  role, email , name) => {
  const params = new URLSearchParams();

  if (page !== null && page !== undefined) params.append("page", page);
  if (size !== null && size !== undefined) params.append("size", size);
  if (role !== null && role !== undefined){
    let submitRole;
    if(role === '관리자'){
      submitRole='ADMIN';
    }
    if(role === '일반 회원'){
      submitRole='USER';
    }
    if(role === '판매자'){
      submitRole='SELLER';
    }
    params.append("role", submitRole);
  }
    
  if (email !== null && email !== undefined)
    params.append("email", email);
  if (name !== null && name !== undefined)
    params.append("name", name);

  const queryString = params.toString(); // URLSearchParams를 문자열로 변환

  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  try {
    const res = await axios.get(`${adminlist}?${queryString}`, header);
    return res.data;
  } catch (error) {
    if (error.response?.data?.message === "만료된 JWT입니다.") {
      try {
        const refreshResponse = await refreshTokens();

        if (refreshResponse.code === 200) {
          const newAccessToken = getCookies("accessToken");
          const role = getRoleFromAccessToken(newAccessToken);

          store.dispatch(refresh({ accessToken: newAccessToken, role: role }));

          const newHeader = {
            headers: { Authorization: `Bearer ${newAccessToken}` },
            withCredentials: true,
          };

          const retryRes = await axios.get(`${adminlist}`, newHeader);
          return retryRes.data;
        }
      } catch (refreshError) {
        throw new Error("토큰 갱신 요청 재시도 실패");
      }
    }
    throw new Error("회원 전체 조회 실패");
  }
};

export const sellerAccept = async (accessToken, page, size, name) => {
  const params = new URLSearchParams();

  if (page !== null && page !== undefined) params.append("page", page);
  if (size !== null && size !== undefined) params.append("size", size);
  if (name !== null && name !== undefined)
    params.append("name", name);

  const queryString = params.toString(); // URLSearchParams를 문자열로 변환

  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  try {
    const res = await axios.get(`${selleraccept}?${queryString}`, header);
    return res.data;
  } catch (error) {
    if (error.response?.data?.message === "만료된 JWT입니다.") {
      try {
        const refreshResponse = await refreshTokens();

        if (refreshResponse.code === 200) {
          const newAccessToken = getCookies("accessToken");
          const role = getRoleFromAccessToken(newAccessToken);

          store.dispatch(refresh({ accessToken: newAccessToken, role: role }));

          const newHeader = {
            headers: { Authorization: `Bearer ${newAccessToken}` },
            withCredentials: true,
          };

          const retryRes = await axios.get(`${selleraccept}`, newHeader);
          return retryRes.data;
        }
      } catch (refreshError) {
        throw new Error("토큰 갱신 요청 재시도 실패");
      }
    }
    throw new Error("회원 전체 조회 실패");
  }
};

export const sellerAcceptFile = async (accessToken, authorityId) => {
  try {
    const res = await axios.put(
      `${selleracceptfile}/${authorityId}`,
      {},
      {
        headers: { Authorization: `Bearer ${accessToken}` }, 
        withCredentials: true, 
      }
    );
    return res.data;
  } catch (error) {
    console.error("API 요청 실패:", error);
    throw error;
  }
};

export const sellerAcceptSubmit = async (accessToken, authorityId) => {
  try {
    const res = await axios.put(
      `${selleracceptsubmit}/${authorityId}`,
      {},
      {
        headers: { Authorization: `Bearer ${accessToken}` }, 
        withCredentials: true, 
      }
    );
    return res.data;
  } catch (error) {
    console.error("API 요청 실패:", error);
    throw error;
  }
};

export const sellerAcceptDelete = async (accessToken, authorityId) => {
  try {
    const res = await axios.delete(
      `${selleracceptsubmit}/${authorityId}`,
      {
        headers: { Authorization: `Bearer ${accessToken}` }, 
        withCredentials: true, 
      }
    );
    return res.data;
  } catch (error) {
    console.error("API 요청 실패:", error);
    throw error;
  }
};

export const searchMemberData = async (accessToken, { page, size, email }) => {
  try {
    const res = await axios.get(`${adminlist}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        page,
        size,
        email,
      },
    });
    return res.data;
  } catch (error) {
    console.error("회원 검색 실패:", error);
    throw error;
  }
};
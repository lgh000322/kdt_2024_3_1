import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/member`;
const adminlist = `${ApiHost}/members`;
const selleraccept = `${ApiHost}/authority`;

export const getMemberInfo = async (accessToken) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.get(`${preFix}`, header);

  return res.data;
};

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

export const logoutRefresh = async () => {
  const header = {
    withCredentials: true,
  };

  const res = await axios.post(`${preFix}/logout`, null, header);

  return res.data;
};

export const getMembers = async (accessToken) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  console.log("Request URL:", adminlist);
  console.log("Request Headers:", header);

  const res = await axios.get(`${adminlist}`, header);

  return res.data;
};

export const sellerAccept = async (accessToken) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  console.log("Request URL:", adminlist);
  console.log("Request Headers:", header);

  const res = await axios.get(`${selleraccept}`, header);

  return res.data;
};

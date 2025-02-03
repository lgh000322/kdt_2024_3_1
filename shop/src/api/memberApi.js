import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/member`;

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

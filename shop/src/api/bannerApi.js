import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFixBanner = `${ApiHost}/banner`;
const preFixBanners = `${ApiHost}/banners`;

// 배너 사진 저장
export const postBanners = async (accessToken, files) => {
  const formData = new FormData();

  files.forEach((file) => {
    formData.append("file", file);
  });

  const header = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "multipart/form-data",
    },
    withCredentials: true,
  };

  const res = await axios.post(preFixBanner, formData, header);

  return res.data;
};

// 배너 사진 전체 조회
export const getBanners = async () => {
  const res = await axios.get(preFixBanners);
  return res.data;
};

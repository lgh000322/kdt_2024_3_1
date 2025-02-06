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

// 배너 삭제
export const deleteBanner = async (accessToken, bannerId) => {
  const header = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    withCredentials: true,
  };

  const res = await axios.delete(`${preFixBanner}/${bannerId}`, header);
  return res.data;
};

// 배너 업데이트
export const updateBanners = async (accessToken, saveFiles, bannerIds) => {
  const formData = new FormData();

  const header = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "multipart/form-data",
    },
    withCredentials: true,
  };

  // 파일들을 formData에 추가
  saveFiles.forEach((file) => {
    formData.append("saveFile", file); // "saveFile"로 파일을 전송
  });

  // bannerIds (삭제할 배너 ID) 추가
  bannerIds.forEach((bannerId, index) => {
    formData.append(`deleteBannerId[${index}].bannerId`, bannerId); // "deleteBannerId"로 ID 전송
  });

  const res = await axios.put("/banner", formData, header);
  return res.data;
};

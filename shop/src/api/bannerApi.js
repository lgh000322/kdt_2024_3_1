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
export const updateBanners = async (
  accessToken,
  saveFiles = [],
  bannerIds = []
) => {
  const formData = new FormData();

  const header = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "multipart/form-data",
    },
    withCredentials: true,
  };

  // 파일들을 formData에 추가 (빈 배열일 경우 빈 배열로 전송)
  if (saveFiles.length === 0) {
    formData.append("saveFile", JSON.stringify([])); // 빈 배열을 문자열로 전송
  } else {
    saveFiles.forEach((file) => {
      formData.append("saveFile", file); // "saveFile"로 파일을 전송
    });
  }

  // bannerIds (삭제할 배너 ID) 추가 (빈 배열일 경우 빈 배열로 전송)
  if (bannerIds.length === 0) {
    const empty = [];
    formData.append(
      "deleteBannerId",
      new Blob([JSON.stringify(empty)], { type: "application/json" })
    );
  } else {
    // 삭제할 배너 ID들을 객체로 감싸서 전송
    const deleteBannerIds = bannerIds.map((bannerId) => ({ bannerId }));
    formData.append(
      "deleteBannerId",
      new Blob([JSON.stringify(deleteBannerIds)], { type: "application/json" })
    );
  }

  const res = await axios.put(`${preFixBanner}`, formData, header);
  return res.data;
};

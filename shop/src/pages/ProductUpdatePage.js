import React, { useState, useRef, useEffect } from "react";
import UploadLayout from "../layouts/UploadLayout";
import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css"; // Editor styles
import "@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css"; // Plugin styles
import colorSyntax from "@toast-ui/editor-plugin-color-syntax";
import { getProductDetails } from "../api/productApi";
import { useSelector } from "react-redux";
import useCustomMove from "../hook/useCustomMove";
import { getBanners } from "../api/bannerApi";

const productEnum = {
  SLIPPERS: { description: "슬리퍼" },
  SANDALS: { description: "샌들" },
  SNEAKERS: { description: "스니커즈" },
  RUNNING_SHOES: { description: "운동화" },
  LOAFERS: { description: "로퍼" },
  HIGH_HEELS: { description: "하이힐" },
  FLAT_SHOES: { description: "플랫슈즈" },
  BOOTS: { description: "부츠" },
  WALKERS: { description: "워커" },
  SLIP_ON: { description: "슬립온" },
  CHELSEA_BOOTS: { description: "첼시부츠" },
  OXFORD_SHOES: { description: "옥스퍼드 슈즈" },
  WINTER_BOOTS: { description: "방한화" },
  RAIN_BOOTS: { description: "레인부츠" },
  AQUA_SHOES: { description: "아쿠아슈즈" },
  DRESS_SHOES: { description: "드레스 신발" },
};

function ProductUpdatePage() {
  useEffect(() => {
    getBanners().then((res) => {
      const data = res.data;

      if (data.length === 3) {
        // 받은 데이터가 3개일 때 그대로 설정
        const updateBanners = data.map((bannerData) => {
          return {
            bannerId: bannerData.bannerId,
            imageUrl: bannerData.imageUrl,
            file: "",
          };
        });
        setBanners(updateBanners);
      } else if (data.length === 1 || data.length === 2) {
        // 받은 데이터가 1개 또는 2개일 때, 나머지 빈 배너로 추가
        const updatedBanners = data.map((bannerData) => {
          return {
            bannerId: bannerData.bannerId,
            imageUrl: bannerData.imageUrl,
            file: "",
          };
        });
        const additionalBanners = Array.from(
          { length: 3 - data.length },
          (_, i) => ({
            bannerId: initState[data.length + i].bannerId,
            imageUrl: null,
            file: "",
          })
        );
        setBanners([...updatedBanners, ...additionalBanners]);
      } else {
        // 받은 데이터가 없을 때는 빈 배너 3개 설정
        setBanners(initState);
      }
    });

    // 초기 file input 참조 설정
    fileInputRefs.current = fileInputRefs.current.slice(0, banners.length);
  }, []);
}

export default ProductUpdatePage;

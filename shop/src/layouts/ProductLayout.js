import React, { useEffect, useState } from "react";
import HeaderComponent from "../components/HeaderComponent";
import { children } from "react";
import BannerComponent from "../components/BannerComponent";
import ProductCardComponent from "../components/ProductCardComponent";
import { useSelector } from "react-redux";
import { getBanners } from "../api/bannerApi";

// 전체 상품 화면의 기본 레이아웃
function ProductLayout({ children }) {
  const loginState = useSelector((state) => state.loginSlice);
  const [banner, setBanners] = useState([]);

  useEffect(() => {
    getBanners().then((res) => {
      const data = res.data;
      setBanners(res.data);
    });
  }, []);
  return (
    <>
      <HeaderComponent></HeaderComponent>
      <BannerComponent bannerList={banner}></BannerComponent>
      <div className="flex justify-center items-center mt-8">{children}</div>
    </>
  );
}

export default ProductLayout;

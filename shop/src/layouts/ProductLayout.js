import React from "react";
import HeaderComponent from "../components/HeaderComponent";
import { children } from "react";
import BannerComponent from "../components/BannerComponent";
import ProductCardComponent from "../components/ProductCardComponent";

// 전체 상품 화면의 기본 레이아웃
function ProductLayout({ children }) {
  return (
    <>
      <HeaderComponent></HeaderComponent>
      <BannerComponent></BannerComponent>
      <div className="flex justify-center items-center mt-8">{children}</div>
    </>
  );
}

export default ProductLayout;
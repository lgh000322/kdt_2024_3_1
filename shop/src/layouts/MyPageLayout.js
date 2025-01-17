import React from "react";
import HeaderComponent from "../components/HeaderComponent";

// 마이페이지의 기본레이아웃
//aside 추가해야함
function MyPageLayout({ children }) {
  return (
    <>
      <HeaderComponent></HeaderComponent>
      <div>{children}</div>
    </>
  );
}

export default MyPageLayout;

import React, { useState } from "react";
import HeaderComponent from "../components/HeaderComponent";

function SubProductLayout({ children }) {
  return (
    <div className="min-h-screen flex flex-col">
      {/* 헤더 영역 */}
      <HeaderComponent />
      {/* 메인 콘텐츠 */}
      <div className="flex justify-center items-center mt-8">
        {/* 자식 컴포넌트 렌더링 */}
        {children}
      </div>
    </div>
  );
}

export default SubProductLayout;

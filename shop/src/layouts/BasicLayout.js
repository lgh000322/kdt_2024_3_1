import React, { useState } from "react";
import HeaderComponent from "../components/HeaderComponent";
import Sidebar from "../components/SideBar";
import { useSelector } from "react-redux";

function BasicLayout({ children }) {
  const [selectedMenu, setSelectedMenu] = useState(null);
  const loginState = useSelector((state) => state.loginSlice);

  let role;


  if (loginState.role == "ROLE_ADMIN") {
    role = "manager";
  } else if (loginState.role == "ROLE_SELLER") {
    role = "seller";
  } else {
    role = "consumer";
  }

  // 사이드바 메뉴 클릭 핸들러
  const handleMenuClick = (item) => {
    setSelectedMenu(item);
  };

  return (
    <div className="min-h-screen flex flex-col">
      {/* 헤더 영역 */}
      <HeaderComponent />

      {/* 본문과 사이드바 레이아웃 */}
      <div className="flex flex-1">
        {/* 사이드바 */}
        <div className="w-64 bg-gray-50 pt-16 fixed left-0 top-30 h-full shadow-md">
          <Sidebar role={role} onMenuClick={handleMenuClick} />
        </div>

        {/* 메인 콘텐츠 */}
        <main className="flex-1 p-4 ml-64">
          {/* 자식 컴포넌트 렌더링 */}
          {children}
        </main>
      </div>
    </div>
  );
}

export default BasicLayout;

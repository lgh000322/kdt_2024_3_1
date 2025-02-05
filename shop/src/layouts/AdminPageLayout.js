import React, { useState } from "react";
import HeaderComponent from "../components/HeaderComponent";
import Sidebar from "../components/SideBar";

// 마이페이지의 기본 레이아웃
function AdminPageLayout({ children, role }) {
  const [selectedMenu, setSelectedMenu] = useState(null);

  // 사이드바 메뉴 항목 클릭 시 해당 항목의 내용을 상태로 설정
  const handleMenuClick = (item) => {
    setSelectedMenu(item);
  };

  return (
    <>
      {/* 헤더 컴포넌트 */}
      <HeaderComponent />

      {/* 본문 내용과 사이드바를 감싸는 div */}
      <div className="flex">
        {/* 사이드바 (헤더 아래 고정) */}
        <div className="w-64 min-h-screen bg-gray-50 pt-16 fixed left-0 top-16.1">
          {/* fixed로 사이드바를 헤더 아래로 배치 (top-16은 헤더의 높이를 고려한 여백) */}
          <Sidebar onMenuClick={handleMenuClick} />
        </div>
        <main className="flex-1 p-4 ml-64">{children}</main>
      </div>
    </>
  );
}

export default AdminPageLayout;

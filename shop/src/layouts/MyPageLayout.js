import React, { useState } from "react";
import HeaderComponent from "../components/HeaderComponent";
import Sidebar from "../components/SideBar";

// 마이페이지의 기본 레이아웃
function MyPageLayout({ children, role }) {
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
          <Sidebar role={role} onMenuClick={handleMenuClick} />
        </div>

        {/* 본문 내용 */}
        <div className="flex-1 p-4 ml-64 md:ml-64">
          {/* 선택된 메뉴에 맞는 aside 콘텐츠 */}
          <aside className="mt-4 p-4 bg-gray-100 border rounded-md">
            {selectedMenu ? (
              <div>
                <h2 className="text-lg font-semibold">{selectedMenu.title}</h2>
                <p>{selectedMenu.content}</p> {/* 클릭된 메뉴 항목에 맞는 콘텐츠 */}
              </div>
            ) : (
              <p>사이드바 메뉴를 선택하세요.</p>
            )}
          </aside>
        </div>
      </div>
    </>
  );
}

export default MyPageLayout;

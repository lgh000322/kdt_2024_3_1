import React ,{ useEffect } from "react";

import HeaderComponent from "../components/HeaderComponent";

function UploadLayout({ children }) {

  useEffect(() => {
    // 페이지가 로드되었을 때 스크롤을 맨 위로 올립니다.
    setTimeout(() => {
      window.scrollTo(0, 0);
    }, 0);  // 0ms 후에 실행 (렌더링 이후에 실행)
  }, []);

  return (
    <div className="pt-14">
      <HeaderComponent />
      {/* 외부 박스 */}
      <div className="max-w-5xl mx-auto p-8 bg-gray-50 shadow-lg rounded-lg border">
        <main>{children}</main>
      </div>
    </div>
  );
}

export default UploadLayout;

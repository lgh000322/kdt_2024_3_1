import React from "react";
import HeaderComponent from "../components/HeaderComponent";

function SubProductLayout({ children, setSearchParams, setNoMoreProducts }) {
  return (
    <>
      <HeaderComponent
        setSearchParams={setSearchParams}
        setNoMoreProducts={setNoMoreProducts}
      />
      {/* max-w-7xl 클래스 추가하고 전체 너비 제한 */}
      <div className="max-w-7xl mx-auto px-4 mt-52">{children}</div>
    </>
  );
}

export default SubProductLayout;

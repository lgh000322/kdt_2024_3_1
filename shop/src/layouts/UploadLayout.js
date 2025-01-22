import React from "react";
import HeaderComponent from "../components/HeaderComponent";

function UploadLayout({ children }) {
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

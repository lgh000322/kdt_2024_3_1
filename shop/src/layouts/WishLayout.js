import React from "react";
import HeaderComponent from "../components/HeaderComponent";

function WishLayout({ children }) {
  return (
    <div className="pt-14">
      <HeaderComponent />
      {/* 외부 박스 */}
        <main>{children}</main>
    </div>
  );
}

export default WishLayout;

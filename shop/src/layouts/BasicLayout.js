import React, { children } from "react";
import HeaderComponent from "../components/HeaderComponent";

function BasicLayout({ children }) {
  return (
    <>
      <HeaderComponent></HeaderComponent>
      <div>{children}</div>
    </>
  );
}

export default BasicLayout;

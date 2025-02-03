import React from "react";
import MyPageLayout from "../layouts/MyPageLayout";
import { useSelector } from "react-redux";

function Mypage() {
  let userRole; // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할
  const loginState = useSelector((state) => state.loginState);

  if (loginState.role === "ROLE_ADMIN") {
    userRole = "manager";
  } else if (loginState.role === "ROLE_USER") {
    userRole = "consumer";
  } else {
    userRole = "seller";
  }

  return (
    <>
      <MyPageLayout role={userRole}></MyPageLayout>
    </>
  );
}

export default Mypage;

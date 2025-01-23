import React from "react";
import MyPageLayout from "../layouts/MyPageLayout";

function Mypage() {

  const userRole = "consumer";  // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할

  return (
    <>

    <MyPageLayout role={userRole}>
    </MyPageLayout>

    </>
  );
}

export default Mypage;

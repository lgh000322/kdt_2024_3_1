import React from "react";
import useCustomMove from "../hook/useCustomMove";

function LoginPage() {
  const { moveToProduct } = useCustomMove();

  return (
    <div className="flex flex-col items-center justify-center h-screen pt-16 bg-white">
      {/* 로고 이미지 */}
      <div className="mb-12 cursor-pointer" onClick={moveToProduct}>
        <img
          src="img/logo.png"
          alt="Shoe Store Logo"
          className="w-full max-w-lg h-auto"
        />
      </div>

      {/* 네이버 로그인 버튼 */}
      <button className="flex items-center justify-center w-full max-w-lg h-16 mb-4 bg-green-500 text-white font-semibold rounded-xl shadow-lg hover:bg-green-600 transition-colors text-xl">
        <img
          src="img/naver.JPG" // 네이버 로고 이미지
          alt="네이버 로고"
          className="w-8 h-8 mr-3"
        />
        네이버로 시작하기
      </button>

      {/* 구글 로그인 버튼 */}
      <button className="flex items-center justify-center w-full max-w-lg h-16 bg-white border border-gray-300 text-gray-600 font-semibold rounded-xl shadow-lg hover:bg-gray-100 transition-colors text-xl">
        <img src="img/google.JPG" alt="구글 로고" className="w-8 h-8 mr-3" />
        구글로 시작하기
      </button>
    </div>
  );
}

export default LoginPage;

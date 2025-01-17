import React, { useState } from "react";
import { Search, User, ShoppingCart } from "lucide-react";
import { Link } from "react-router-dom";

const HeaderComponent = () => {
  // 검색 쿼리를 관리하는 상태
  const [searchQuery, setSearchQuery] = useState("");

  // 검색 처리 함수
  const handleSearch = () => {
    console.log("검색 실행", searchQuery);
    // 여기에 검색 로직 추가
  };

  return (
    <div className="w-full">
      {/* 메인 헤더 - 상단에 고정 */}
      <div className="fixed top-0 left-0 w-full z-50 bg-white shadow-md">
        <div className="flex items-center p-4 w-3/4 justify-center mx-auto">
          {/* 로고 - 좌측 정렬 */}
          <div className="flex-shrink-0">
            <Link to="/">
              <img src="img/logo.png" alt="신발 로고" className="w-40 h-20" />
            </Link>
          </div>

          {/* 검색 바 - 로고 다음부터 아이콘 전까지 채우기 */}
          <div className="flex-1 mx-4">
            <div className="relative">
              <input
                type="text"
                placeholder="검색"
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-200"
                value={searchQuery} // 입력 값 바인딩
                onChange={(e) => setSearchQuery(e.target.value)} // 입력 값 변경 시 상태 업데이트
              />
              <button
                onClick={handleSearch}
                className="absolute right-3 top-2.5 text-gray-400 hover:text-black transition-colors"
                aria-label="검색" // 접근성 향상
              >
                <Search size={20} />
              </button>
            </div>
          </div>

          {/* 아이콘 - 우측 정렬 */}
          <div className="flex items-center gap-4 flex-shrink-0">
            <Link to="/mypage">
              <User className="w-6 h-6 cursor-pointer hover:text-black" />
            </Link>
            <Link to="/cart">
              <ShoppingCart className="w-6 h-6 cursor-pointer hover:text-black" />
            </Link>
          </div>
        </div>

        {/* 내비게이션 바 */}
        <nav className="flex items-center justify-between p-5 bg-gray-100">
          <div className="flex space-x-6 text-sm overflow-x-auto">
            <Link
              to="/summer"
              className="whitespace-nowrap text-xl text-gray-600 hover:text-black transition-colors"
            >
              여름
            </Link>
            <Link
              to="/winter"
              className="whitespace-nowrap text-xl text-gray-600 hover:text-black transition-colors"
            >
              겨울
            </Link>
            <Link
              to="/men"
              className="whitespace-nowrap text-xl text-gray-600 hover:text-black transition-colors"
            >
              남성
            </Link>
            <Link
              to="/women"
              className="whitespace-nowrap text-xl text-gray-600 hover:text-black transition-colors"
            >
              여성
            </Link>
            <Link
              to="/kids"
              className="whitespace-nowrap text-xl text-gray-600 hover:text-black transition-colors"
            >
              유아
            </Link>
          </div>
          <Link
            to="/login"
            className="whitespace-nowrap text-xl text-gray-600 hover:text-black transition-colors"
          >
            로그인/로그아웃
          </Link>
        </nav>
      </div>

      {/* 여백 추가 - 헤더가 고정되어 있기 때문에 콘텐츠가 가려지지 않도록 */}
      <div className="mt-32" />
    </div>
  );
};

export default HeaderComponent;

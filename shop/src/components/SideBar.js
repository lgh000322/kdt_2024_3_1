import React from 'react';
import { Link } from 'react-router-dom'; // Link 컴포넌트 import

const Sidebar = ({ onMenuClick }) => {  // onMenuClick prop을 받아옴
  const menuItems = [
    { id: 1, title: '배송관리(판매자)', path: '/mypage/shipping-management', content: '배송관리 관련 콘텐츠' },
    { id: 2, title: '주문관리(판매자)', path: '/mypage/order-management', content: '주문관리 관련 콘텐츠' },
    { id: 3, title: '주문목록 / 배송조회', path: '/mypage/order-list', content: '주문목록 및 배송조회 관련 콘텐츠' },
    { id: 4, title: '배송지 관리', path: '/mypage/address-management', content: '배송지 관리 관련 콘텐츠' },
    { id: 5, title: '문의내역', path: '/mypage/inquiry', content: '문의내역 관련 콘텐츠' },
    { id: 6, title: '찜리스트', path: '/mypage/wishlist', content: '찜리스트 관련 콘텐츠' },
    { id: 7, title: '판매자 등록 신청', path: '/mypage/seller-registration', content: '판매자 등록 신청 관련 콘텐츠' }
  ];

  return (
    <div className="w-64 min-h-screen bg-gray-50">
      {/* 상단 카테고리 메뉴 */}
      <div className="p-4 text-gray-700 font-semibold">마이페이지</div>
        
      {/* 사이드바 메뉴 */}
      <nav className="p-4">
        {menuItems.map((item) => (
          <Link key={item.id} to={item.path}>  {/* Link 컴포넌트로 변경 */}
            <button
              className="w-full text-left px-4 py-2.5 text-gray-700
                       hover:bg-white hover:text-blue-600
                       rounded-lg transition-colors duration-200"
              onClick={() => onMenuClick(item)}  // 클릭 시 해당 항목을 부모에 전달
            >
              {item.title}
            </button>
          </Link>
        ))}
      </nav>
      
      {/* 로그인/로그아웃 */}
      <div className="p-4 border-t border-gray-200">
        <button className="w-full text-left px-4 py-2.5 text-gray-700
                          hover:bg-white hover:text-blue-600 
                          rounded-lg transition-colors duration-200">
          로그인/로그아웃
        </button>
      </div>
    </div>
  );
};

export default Sidebar;

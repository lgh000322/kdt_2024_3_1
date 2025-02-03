import React from 'react';
import { Link, useNavigate } from 'react-router-dom'; // useNavigate 훅 import

const Sidebar = ({ onMenuClick, role }) => {
  const navigate = useNavigate(); // useNavigate 훅 사용

  const sellerMenuItems = [
    { id: 1, title: '배송관리(판매자)', path: '/mypage/shipping-management', content: '배송관리 관련 콘텐츠' },
    { id: 2, title: '주문관리(판매자)', path: '/mypage/order-management', content: '주문관리 관련 콘텐츠' },
    { id: 3, title: '문의내역', path: '/mypage/inquiry', content: '문의내역 관련 콘텐츠' },
    { id: 4, title: '찜리스트', path: '/mypage/wishlist', content: '찜리스트 관련 콘텐츠' },
    { id: 5, title: '주문목록 / 배송조회', path: '/mypage/order-list', content: '주문목록 및 배송조회 관련 콘텐츠' },
    { id: 6, title: '배송지 관리', path: '/mypage/address-management', content: '배송지 관리 관련 콘텐츠' },
  ];

  const consumerMenuItems = [
    { id: 1, title: '주문목록 / 배송조회', path: '/mypage/order-list', content: '주문목록 및 배송조회 관련 콘텐츠' },
    { id: 2, title: '배송지 관리', path: '/mypage/address-management', content: '배송지 관리 관련 콘텐츠' },
    { id: 3, title: '문의내역', path: '/mypage/inquiry', content: '문의내역 관련 콘텐츠' },
    { id: 4, title: '찜리스트', path: '/mypage/wishlist', content: '찜리스트 관련 콘텐츠' },
    { id: 5, title: '판매자 등록 신청', path: '/mypage/seller-registration', content: '판매자 등록 신청 관련 콘텐츠' }
  ];

  const managerMenuItems = [
    { id: 1, title: '회원관리', path: '/admin_user', content: '회원관리 관련 콘텐츠' },
    { id: 2, title: '판매자 목록', path: '/admin_seller', content: '판매자 목록 관련 콘텐츠' },
    { id: 3, title: '판매자 승인', path: '/admin_accept', content: '판매자 승인 관련 콘텐츠' },
    { id: 4, title: '통계분석', path: '/admin_statistic', content: '통계분석 관련 콘텐츠' },
    { id: 5, title: '고객지원(1대1 상담)', path: '/mypage/counsel', content: '고객지원(1대1 상담) 관련 콘텐츠' },
    { id: 6, title: '배너 관리', path: '/admin_banner', content: '배너관리 관련 콘텐츠' },
  ];

  // 역할에 따른 메뉴 항목을 선택
  let menuItems = [];
  if (role === 'seller') {
    menuItems = sellerMenuItems;
  } else if (role === 'consumer') {
    menuItems = consumerMenuItems;
  } else if (role === 'manager') {
    menuItems = managerMenuItems;
  }

  return (
    <div className="w-64 min-h-screen bg-gray-50">
      {/* 마이페이지 텍스트 */}
      <div className="p-4 text-black font-semibold">마이페이지</div>

      {/* 구분선 */}
      <hr className="border-gray-300" />

      {/* 사이드바 메뉴 */}
      <nav className="p-4">
        {menuItems.map((item) => (
          <div key={item.id}>
            <Link to={item.path}>
              <button
                className="w-full text-left px-4 py-2.5 text-gray-700
                          hover:bg-white hover:text-blue-600
                          rounded-lg transition-colors duration-200"
                onClick={() => onMenuClick(item)} // 클릭 시 해당 항목을 부모에 전달
              >
                {item.title}
              </button>
            </Link>
          </div>
        ))}
      </nav>

      {/* 로그인/로그아웃 구분선 */}
      <div className="p-4 border-t border-gray-200">
        <button
          className="w-full text-left px-4 py-2.5 text-gray-700
                          hover:bg-white hover:text-blue-600 
                          rounded-lg transition-colors duration-200"
          onClick={() => navigate('/login')} // 버튼 클릭 시 /login 경로로 이동
        >
          로그인/로그아웃
        </button>
      </div>
    </div>
  );
};

export default Sidebar;

import React, { useState, useEffect } from "react";
import HeaderComponent from "../components/HeaderComponent";
import CartItemComponent from "../components/CartItemComponent";

const Cart = () => {
  const [cartItems, setCartItems] = useState([
    {
      id: 1,
      imageUrl: "/api/placeholder/96/96",
      makerName: "메이커홀딩",
      productName: "프리미엄 상품",
      initialQuantity: 1,
      basePrice: 48000,
      isSelected: false, // 기본 선택된 상태
    },
    {
      id: 2,
      imageUrl: "/api/placeholder/96/96",
      makerName: "메이커홀딩",
      productName: "스탠다드 상품",
      initialQuantity: 1,
      basePrice: 89000,
      isSelected: false,
    },
  ]);

  const [totalPrice, setTotalPrice] = useState(0); // 총 주문 금액
  const deliveryFeeThreshold = 50000; // 무료 배송 기준 금액
  const baseDeliveryFee = 3000; // 기본 배송비

  // 총 주문 금액 계산 함수
  const calculateTotalPrice = () => {
    const total = cartItems
      .filter((item) => item.isSelected) // 선택된 아이템만 포함
      .reduce((sum, item) => sum + item.basePrice * item.initialQuantity, 0);
    setTotalPrice(total); // 총 주문 금액 업데이트
  };

  // cartItems 상태가 변경될 때마다 총 주문 금액 재계산
  useEffect(() => {
    calculateTotalPrice();
  }, [cartItems]);

  // 전체 선택/해제
  const handleSelectAll = () => {
    const allSelected = cartItems.every((item) => item.isSelected);
    setCartItems((prevItems) =>
      prevItems.map((item) => ({ ...item, isSelected: !allSelected }))
    );
  };

  // 개별 선택
  const handleSelectItem = (id) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id ? { ...item, isSelected: !item.isSelected } : item
      )
    );
  };

  // 아이템 삭제
  const handleRemoveItem = (id) => {
    setCartItems((prevItems) => prevItems.filter((item) => item.id !== id));
  };

  // 수량 증가
  const handleIncreaseQuantity = (id) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id
          ? { ...item, initialQuantity: item.initialQuantity + 1 }
          : item
      )
    );
  };

  // 수량 감소
  const handleDecreaseQuantity = (id) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id && item.initialQuantity > 1
          ? { ...item, initialQuantity: item.initialQuantity - 1 }
          : item
      )
    );
  };

  // 배송비 계산 (5만원 이상이면 무료)
  const deliveryFee = totalPrice >= deliveryFeeThreshold ? 0 : baseDeliveryFee;

  // 최종 결제 금액 계산
  const finalPrice = totalPrice + deliveryFee;

  return (
    <div>
      {/* 헤더 컴포넌트 */}
      <HeaderComponent />

      {/* 메인 콘텐츠 */}
      <div style={{ marginTop: "180px", maxWidth: "1200px", margin: "auto" }}>
        <div style={{ display: "grid", gridTemplateColumns: "2fr 1fr", gap: "24px" }}>
          {/* 왼쪽 컬럼 */}
          <div style={{ backgroundColor: "#f9fafb", padding: "16px", borderRadius: "8px" }}>
            <h1 className="text-2xl font-bold mb-6">장바구니</h1>

            {/* 전체 선택 */}
            <div className="flex items-center mb-4">
              <input
                type="checkbox"
                checked={cartItems.every((item) => item.isSelected)}
                onChange={handleSelectAll}
                className="w-5 h-5 align-middle"
              />
              <label className="ml-2 text-gray-700">전체 선택</label>
            </div>

            {/* 장바구니 아이템 리스트 */}
            {cartItems.length > 0 ? (
              cartItems.map((item) => (
                <CartItemComponent
                  key={item.id}
                  imageUrl={item.imageUrl}
                  makerName={item.makerName}
                  productName={item.productName}
                  initialQuantity={item.initialQuantity}
                  basePrice={item.basePrice}
                  isSelected={item.isSelected}
                  onRemove={() => handleRemoveItem(item.id)}
                  onSelect={() => handleSelectItem(item.id)}
                  onIncrease={() => handleIncreaseQuantity(item.id)} // 수량 증가 핸들러 전달
                  onDecrease={() => handleDecreaseQuantity(item.id)} // 수량 감소 핸들러 전달
                />
              ))
            ) : (
              <p className="text-center text-gray-500">장바구니가 비어 있습니다.</p>
            )}
          </div>

          {/* 오른쪽 컬럼 */}
          <div style={{ marginTop: "100px", backgroundColor: "#f9fafb", padding: "16px", borderRadius: "8px" }}>
            <h2 className="text-xl font-bold mb-4">주문 요약</h2>
            <div className="flex justify-between mb-4">
              <span>총 주문 금액</span>
              <span>{totalPrice.toLocaleString()}원</span>
            </div>
            <div className="flex justify-between mb-4">
              <span>
                배송비{" "}
                <span style={{ fontSize: "12px", color: "#6b7280" }}>
                  (5만원 이상 구매 시, 배송비 무료)
                </span>
              </span>
              <span>{deliveryFee.toLocaleString()}원</span>
            </div>
            <hr className="my-4" />
            <div className="flex justify-between font-bold text-lg">
              <span>최종 결제 금액</span>
              <span>{finalPrice.toLocaleString()}원</span>
            </div>
            <button style={{ width: "100%", backgroundColor: "#dc2626", color: "#fff", padding: "12px", borderRadius: "8px", textAlign: "center" }}>
              구매하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;

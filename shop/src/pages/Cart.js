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
      basePrice: 159000,
      isSelected: false,
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
  const deliveryFee = 3000;

  // 총 주문 금액 계산 함수
  const calculateTotalPrice = () => {
    const total = cartItems
      .filter((item) => item.isSelected)
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

  // 최종 결제 금액 계산
  const finalPrice = totalPrice > 0 ? totalPrice + deliveryFee : 0;

  // 인라인 스타일 정의
  const styles = {
    headerSpacing: {
      marginTop: "180px", // HeaderComponent 높이에 맞게 조정
    },
    container: {
      display: "grid",
      gridTemplateColumns: "2fr 1fr",
      gap: "24px",
      maxWidth: "1200px",
      margin: "auto",
    },
    leftColumn: {
      backgroundColor: "#f9fafb",
      padding: "16px",
      borderRadius: "8px",
    },
    rightColumn: {
      backgroundColor: "#f9fafb",
      padding: "16px",
      borderRadius: "8px",
    },
    summaryRow: {
      display: "flex",
      justifyContent: "space-between",
      marginBottom: "12px",
    },
    totalRow: {
      display: "flex",
      justifyContent: "space-between",
      fontWeight: "bold",
      fontSize: "18px",
    },
    purchaseButton: {
      width: "100%",
      backgroundColor: "#dc2626",
      color: "#fff",
      padding: "12px",
      borderRadius: "8px",
      textAlign: "center",
      cursor: "pointer",
    },
    purchaseButtonHover: {
      backgroundColor: "#b91c1c", // 호버 시 색상 변경
    },
  };

  return (
    <div>
      {/* 헤더 컴포넌트 */}
      <HeaderComponent />

      {/* 메인 콘텐츠 */}
      <div style={styles.headerSpacing}>
        <div style={styles.container}>
          {/* 왼쪽 컬럼 */}
          <div style={styles.leftColumn}>
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
                  {...item}
                  onRemove={() => handleRemoveItem(item.id)}
                  onSelect={() => handleSelectItem(item.id)}
                />
              ))
            ) : (
              <p className="text-center text-gray-500">장바구니가 비어 있습니다.</p>
            )}
          </div>

          {/* 오른쪽 컬럼 */}
          <div style={styles.rightColumn}>
            <h2 className="text-xl font-bold mb-4">주문 요약</h2>
            <div style={styles.summaryRow}>
              <span>총 주문 금액</span>
              <span>{totalPrice.toLocaleString()}원</span>
            </div>
            <div style={styles.summaryRow}>
              <span>배송비</span>
              <span>{deliveryFee.toLocaleString()}원</span>
            </div>
            <hr className="my-4" />
            <div style={styles.totalRow}>
              <span>최종 결제 금액</span>
              <span>{finalPrice.toLocaleString()}원</span>
            </div>
            <button
              style={{
                ...styles.purchaseButton,
                marginTop: "16px", // 버튼 상단 여백 추가
              }}
              onMouseOver={(e) =>
                (e.target.style.backgroundColor =
                  styles.purchaseButtonHover.backgroundColor)
              }
              onMouseOut={(e) =>
                (e.target.style.backgroundColor =
                  styles.purchaseButton.backgroundColor)
              }
            >
              구매하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;

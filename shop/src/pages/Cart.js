import React, { useState, useEffect } from "react";
import HeaderComponent from "../components/HeaderComponent";
import CartItemComponent from "../components/CartItemComponent";
import { getCartItem, removeCartItem, updateCartItemQuantity } from "../api/cartApi"; // 🛒 API 추가
import { useSelector } from "react-redux";

const Cart = () => {

  const loginSlice = useSelector((state) => state.loginSlice);

  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const deliveryFeeThreshold = 50000;
  const baseDeliveryFee = 3000;
  
  const accessToken = loginSlice.accessToken;
  //const accessToken = localStorage.getItem("accessToken"); // ✅ 토큰 가져오기

  // 🔹 장바구니 데이터 불러오기 (API 요청)
  useEffect(() => {

    
    const fetchCartItems = async () => {
      console.log("토큰" , accessToken);
      try {
        const response = await getCartItem(accessToken, 0, 10); // 첫 페이지 기준
        setCartItems(response.data); // API 응답 데이터 설정
      } catch (error) {
        console.error("장바구니 데이터를 불러오는 중 오류 발생:", error);
      }
    };

    fetchCartItems();
  }, []);

  // 🔹 총 주문 금액 계산 함수
  useEffect(() => {
    const total = cartItems
      .filter((item) => item.isSelected)
      .reduce((sum, item) => sum + item.basePrice * item.initialQuantity, 0);
    setTotalPrice(total);
  }, [cartItems]);

  // 🔹 개별 선택
  const handleSelectItem = (id) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id ? { ...item, isSelected: !item.isSelected } : item
      )
    );
  };

  // 🔹 장바구니 상품 삭제 (API 연동)
  const handleRemoveItem = async (id) => {
    try {
      await removeCartItem(accessToken, id);
      setCartItems((prevItems) => prevItems.filter((item) => item.id !== id)); // UI 반영
    } catch (error) {
      console.error("상품 삭제 중 오류 발생:", error);
    }
  };

  // 🔹 수량 증가 (API 연동)
  const handleIncreaseQuantity = async (id) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id ? { ...item, initialQuantity: item.initialQuantity + 1 } : item
      )
    );

    try {
      const item = cartItems.find((item) => item.id === id);
      await updateCartItemQuantity(accessToken, id, item.initialQuantity + 1);
    } catch (error) {
      console.error("수량 증가 중 오류 발생:", error);
    }
  };

  // 🔹 수량 감소 (API 연동)
  const handleDecreaseQuantity = async (id) => {
    const item = cartItems.find((item) => item.id === id);
    if (item.initialQuantity > 1) {
      setCartItems((prevItems) =>
        prevItems.map((item) =>
          item.id === id ? { ...item, initialQuantity: item.initialQuantity - 1 } : item
        )
      );

      try {
        await updateCartItemQuantity(accessToken, id, item.initialQuantity - 1);
      } catch (error) {
        console.error("수량 감소 중 오류 발생:", error);
      }
    }
  };

  // 배송비 계산
  const deliveryFee = totalPrice >= deliveryFeeThreshold ? 0 : baseDeliveryFee;
  const finalPrice = totalPrice + deliveryFee;

  return (
    <div>
      <HeaderComponent />

      <div style={{ marginTop: "180px", maxWidth: "1200px", margin: "auto" }}>
        <div style={{ display: "grid", gridTemplateColumns: "2fr 1fr", gap: "24px" }}>
          <div style={{ backgroundColor: "#f9fafb", padding: "16px", borderRadius: "8px" }}>
            <h1 className="text-2xl font-bold mb-6">장바구니</h1>

            {/* 전체 선택 */}
            <div className="flex items-center mb-4">
              <input
                type="checkbox"
                checked={cartItems.every((item) => item.isSelected)}
                onChange={() =>
                  setCartItems((prevItems) =>
                    prevItems.map((item) => ({ ...item, isSelected: !cartItems.every((i) => i.isSelected) }))
                  )
                }
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
                  onIncrease={() => handleIncreaseQuantity(item.id)}
                  onDecrease={() => handleDecreaseQuantity(item.id)}
                />
              ))
            ) : (
              <p className="text-center text-gray-500">장바구니가 비어 있습니다.</p>
            )}
          </div>

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
            <button
              style={{
                width: "100%",
                backgroundColor: "#dc2626",
                color: "#fff",
                padding: "12px",
                borderRadius: "8px",
                textAlign: "center",
              }}
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

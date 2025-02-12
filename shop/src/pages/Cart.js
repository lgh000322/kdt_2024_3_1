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

  // 🔹 장바구니 데이터 불러오기 (API 요청)
  useEffect(() => {
    const fetchCartItems = async () => {
      try {
        const response = await getCartItem(accessToken, 0, 10);
        const cartItemList = response.data.map(item => ({ ...item, isSelected: false }));
        setCartItems(cartItemList);
      } catch (error) {
        console.error("장바구니 데이터를 불러오는 중 오류 발생:", error);
      }
    };
    fetchCartItems();
  }, [accessToken]);

  // 🔹 총 주문 금액 계산 함수
  useEffect(() => {
    const total = cartItems
      .filter((item) => item.isSelected)
      .reduce((sum, item) => sum + item.cartItemPrice * item.cartItemCount, 0);
    setTotalPrice(total);
  }, [cartItems]);

  // 🔹 개별 선택
  const handleSelectItem = (id) => {
    setCartItems((prevItems) =>
      prevItems.map((item) =>
        item.cartItemId === id ? { ...item, isSelected: !item.isSelected } : item
      )
    );
  };

  // 🔹 전체 선택
  const handleSelectAll = () => {
    const allSelected = cartItems.every(item => item.isSelected);
    setCartItems(prevItems => prevItems.map(item => ({ ...item, isSelected: !allSelected })));
  };

  // 🔹 장바구니 상품 삭제 (API 연동)
  const handleRemoveItem = async (id) => {
    try {
      await removeCartItem(accessToken, id);
      setCartItems((prevItems) => prevItems.filter((item) => item.cartItemId !== id));
    } catch (error) {
      console.error("상품 삭제 중 오류 발생:", error);
    }
  };

  const handleIncreaseQuantity = async (id) => {
    setCartItems(prevItems => prevItems.map(item =>
      item.cartItemId === id ? { ...item, cartItemCount: item.cartItemCount + 1 } : item
    ));
    try {
      await updateCartItemQuantity(accessToken, id, cartItems.find(item => item.cartItemId === id).cartItemCount + 1);
    } catch (error) {
      console.error("수량 증가 중 오류 발생:", error);
    }
  };

  const handleDecreaseQuantity = async (id) => {
    setCartItems(prevItems => prevItems.map(item =>
      item.cartItemId === id && item.cartItemCount > 1 ? { ...item, cartItemCount: item.cartItemCount - 1 } : item
    ));
    try {
      await updateCartItemQuantity(accessToken, id, cartItems.find(item => item.cartItemId === id).cartItemCount - 1);
    } catch (error) {
      console.error("수량 감소 중 오류 발생:", error);
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
            <div className="flex items-center mb-4">
              <input
                type="checkbox"
                checked={cartItems.every(item => item.isSelected)}
                onChange={handleSelectAll}
                className="w-5 h-5 align-middle"
              />
              <label className="ml-2 text-gray-700">전체 선택</label>
            </div>
            {cartItems.length > 0 ? (
              cartItems.map((item) => (
                <div className="bg-gray-100 rounded-lg p-4 mb-4 shadow-sm flex items-start space-x-4">
                  <CartItemComponent
                    key={item.cartItemId}
                    imageUrl={item.imgUrl}
                    productName={item.productName}
                    initialQuantity={item.cartItemCount}
                    basePrice={item.cartItemPrice}
                    isSelected={item.isSelected}
                    onRemove={() => handleRemoveItem(item.cartItemId)}
                    onSelect={() => handleSelectItem(item.cartItemId)}
                    onIncrease={() => handleIncreaseQuantity(item.cartItemId)}
                    onDecrease={() => handleDecreaseQuantity(item.cartItemId)}
                  />
                </div>
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
              <span>배송비 (5만원 이상 구매 시 무료)</span>
              <span>{deliveryFee.toLocaleString()}원</span>
            </div>
            <hr className="my-4" />
            <div className="flex justify-between font-bold text-lg">
              <span>최종 결제 금액</span>
              <span>{finalPrice.toLocaleString()}원</span>
            </div>
            <button className="w-full bg-red-600 text-white py-3 rounded-lg">구매하기</button>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Cart;

import React, { useState, useEffect } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useParams, useNavigate } from "react-router-dom";
import { showOrderDetail } from "../api/OrderDetailApi";
import { deleteOrderHistory } from "../api/OrderListApi";
import { useSelector } from "react-redux";

function OrderDetailPage() {
  const navigate = useNavigate();
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  const { orderHistoryId } = useParams();
  const [orderDetail, setOrderDetail] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    showOrderDetail(orderHistoryId, accessToken).then((res) => {
      const data = res.data;
      console.log(data);
      setOrderDetail(data);
      setLoading(false);
    });
  }, [orderHistoryId]);

  const handleDeleteOrder = async () => {
    if (!window.confirm("정말 주문을 취소하시겠습니까?")) {
      return;
    }

    try {
      console.log("📡 [클라이언트] 주문 삭제 요청:", { orderHistoryId });

      await deleteOrderHistory(orderHistoryId, accessToken);
      alert("주문이 취소되었습니다.");

      navigate("/mypage/order-list");
    } catch (error) {
      alert("주문 취소에 실패했습니다.");
      console.error("🚨 주문 취소 실패:", error);
    }
  };

  const handleDeliveryStatus = (id) => {
    const data = orderDetail.find((order) => order.orderId == id);

    if (data.deliveryStatus == "BEFORE_DELIVERY") {
      return "배송 전";
    } else if (data.deliveryStatus == "BEFORE_PAY") {
      return "결제 전";
    } else if (data.deliveryStatus == "START_DELIVERY") {
      return "배송 중";
    } else if (data.deliveryStatus == "END_DELIVERY") {
      return "배송 후";
    } else {
      return "주문 취소";
    }
  };

  if (loading) {
    return (
      <BasicLayout>
        <p className="text-center p-4">🔄 주문 상세 정보를 불러오는 중...</p>
      </BasicLayout>
    );
  }

  return (
    <BasicLayout>
      <div className="px-6 py-10 bg-gray-50 min-h-screen font-sans">
        {/* 헤더 섹션 */}
        <header className="mb-12 text-center">
          <h1 className="text-3xl font-bold text-gray-800 mb-2 tracking-tight">
            주문 상세 정보
          </h1>
        </header>

        {/* 주문 정보 카드 */}
        <section className="bg-white rounded-2xl p-8 mb-6 shadow-sm">
          <h2 className="text-xl font-semibold text-gray-800 mb-7 pb-4 border-b-2 border-gray-100">
            배송 정보
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <DetailItem label="수령인" value={orderDetail[0].orderName} />
            <DetailItem label="연락처" value={orderDetail[0].phoneNumber} />
            <DetailItem label="주소" value={orderDetail[0].deliveryAddress} />
          </div>
        </section>

        {/* 상품 정보 카드 */}
        <section className="bg-white rounded-2xl p-8 mb-6 shadow-sm">
          <h2 className="text-xl font-semibold text-gray-800 mb-7 pb-4 border-b-2 border-gray-100">
            주문 상품
          </h2>
          {orderDetail.length > 0 ? (
            orderDetail.map((order) => (
              <div
                key={order.orderId}
                className="flex gap-8 items-center p-5 bg-gray-100 rounded-xl mb-4"
              >
                <div className="flex-shrink-0">
                  <img
                    src={order.imgUrl}
                    alt={order.productName}
                    className="w-40 h-40 rounded-lg object-cover"
                  />
                </div>
                <div className="flex-grow">
                  <h3 className="text-xl font-bold text-gray-800 mb-3">
                    {order.productName}
                  </h3>
                  <p className="text-lg text-gray-800 font-semibold mb-2">
                    수량: {order.orderProductCount}개
                  </p>
                  <p className="text-lg text-gray-800 font-semibold mb-2">
                    가격: {order.totalPrice.toLocaleString()} 원
                  </p>
                  <p className="text-lg text-gray-800 font-semibold">
                    배송 상태: {handleDeliveryStatus(order.orderId)}
                  </p>
                </div>
              </div>
            ))
          ) : (
            <p className="text-center text-gray-600">
              📦 주문한 상품이 없습니다.
            </p>
          )}
        </section>

        {/* 액션 버튼 그룹 */}
        <div className="flex justify-center gap-5 mt-10">
          <button
            className="px-10 py-4 border-2 border-red-500 text-red-500 rounded-lg font-bold 
                       hover:bg-red-50 transition-colors duration-200"
            onClick={handleDeleteOrder}
          >
            주문내역 삭제
          </button>
        </div>
      </div>
    </BasicLayout>
  );
}

// 재사용 가능한 디테일 아이템 컴포넌트
function DetailItem({ label, value }) {
  return (
    <div className="flex justify-stretch items-center py-4 border-b border-gray-100">
      <span className="text-gray-500 text-sm font-medium">{label}</span>
      <span className={"text-base font-semibold ml-20"}>{value}</span>
    </div>
  );
}

export default OrderDetailPage;

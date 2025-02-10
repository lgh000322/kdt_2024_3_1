import React, { useState, useEffect } from "react";
import styled from "styled-components";
import BasicLayout from "../layouts/BasicLayout";
import { useSelector } from "react-redux";
import { getOrderHistory, deleteOrderHistory } from "../api/OrderListApi";
import useCustomMove from "../hook/useCustomMove";

const PageContainer = styled.div`
  padding: 50px 20px;
  font-family: "Arial", sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
`;

const PageTitle = styled.h1`
  margin-bottom: 20px;
  color: #333;
  font-size: 28px;
  font-weight: bold;
`;

const OrderCard = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  background-color: #ffffff;
  padding: 25px;
  border-radius: 12px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s ease;

  &:hover {
    transform: translateY(-2px);
  }
`;

const OrderInfo = styled.div`
  flex-grow: 1;
`;

const OrderDate = styled.p`
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 12px;
  color: #2c3e50;
`;

const OrderStatus = styled.p`
  margin-bottom: 12px;
  color: #666;
  font-size: 14px;
`;

const ProductPreview = styled.div`
  display: flex;
  align-items: center;
`;

const ProductImage = styled.div`
  width: 120px;
  height: 120px;
  background-color: #f8f9fa;
  margin-right: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  color: #666;
  font-weight: 500;
`;

const ProductDetails = styled.div`
  p {
    margin-bottom: 8px;
    color: #4a4a4a;
    font-size: 14px;

    &:last-child {
      margin-bottom: 0;
    }
  }
`;

const ActionButtons = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-left: 20px;
`;

const ActionButton = styled.button`
  padding: 12px 24px;
  background-color: ${(props) => (props.$primary ? "#4a90e2" : "#ffffff")};
  color: ${(props) => (props.$primary ? "#ffffff" : "#4a4a4a")};
  border: 1px solid ${(props) => (props.$primary ? "#4a90e2" : "#e0e0e0")};
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;

  &:hover {
    background-color: ${(props) => (props.$primary ? "#357abd" : "#f5f5f5")};
  }
`;

const orders = [
  {
    id: "ORD001",
    date: "2025-01-20",
    status: "배송중",
    items: "상품 이름 외 2개",
    price: "₩30,000",
  },
  {
    id: "ORD002",
    date: "2025-01-21",
    status: "구매완료",
    items: "상품 이름 외 1개",
    price: "₩15,000",
  },
  {
    id: "ORD003",
    date: "2025-01-22",
    status: "구매완료",
    items: "상품 이름 외 1개",
    price: "₩15,000",
  },
];

// 주문 취소 함수
const handleDeleteOrder = async (orderId, token, setOrders) => {
  if (!window.confirm("정말 주문을 취소하시겠습니까?")) {
    return;
  }

  try {
    await deleteOrderHistory(orderId, token);
    alert("주문이 취소되었습니다.");
    setOrders((prevOrders) =>
      prevOrders.filter((order) => order.orderId !== orderId)
    );
  } catch (error) {
    alert("주문 취소에 실패했습니다.");
    console.error("🚨 주문 취소 실패:", error);
  }
};

const renderOrderCard = (
  order,
  moveToOrderDetailPage,
  accessToken,
  setOrders
) => (
  <OrderCard key={order.orderId}>
    <OrderInfo>
      <OrderDate>주문일시: {order.createdAt}</OrderDate>
      <OrderStatus>주문상태: {order.status || "확인 중"}</OrderStatus>
      <ProductPreview>
        <ProductImage>
          <img src={order.imageUrl} alt="상품 이미지" width="80" height="80" />
        </ProductImage>
        <ProductDetails>
          <p>주문번호 : {order.orderId}</p>
          <p>주문물품 : {order.content}</p>
          <p>주문가격 : {order.price.toLocaleString()} 원</p>
        </ProductDetails>
      </ProductPreview>
    </OrderInfo>
    <ActionButtons>
      <ActionButton
        $primary
        onClick={() => moveToOrderDetailPage(order.orderId, accessToken)}
      >
        주문 상세 내역
      </ActionButton>
      <ActionButton>배송 조회</ActionButton>
      <ActionButton
        onClick={() => handleDeleteOrder(order.orderId, accessToken, setOrders)}
      >
        취소
      </ActionButton>
      <ActionButton>리뷰 작성</ActionButton>
    </ActionButtons>
  </OrderCard>
);

function OrderListPage() {
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  const [orders, setOrders] = useState([]); // 주문 목록 상태 추가
  const [page, setPage] = useState(0); // 현재 페이지 상태
  const size = 10; // 한 페이지당 개수 (고정값)

  const { moveToOrderDetailPage } = useCustomMove();

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await getOrderHistory(accessToken, page, size);
        setOrders(Array.isArray(response) ? response : []);
      } catch (error) {
        console.error("🚨 주문 목록 불러오기 실패:", error);
        setOrders([]);
      }
    };

    if (accessToken) {
      fetchOrders();
    }
  }, [accessToken, page]); // 페이지 변경 시 다시 호출

  return (
    <BasicLayout>
      <PageContainer>
        <PageTitle>주문 목록</PageTitle>
        {orders.length > 0 ? (
          orders.map((order) =>
            renderOrderCard(
              order,
              moveToOrderDetailPage,
              accessToken,
              setOrders
            )
          )
        ) : (
          <p>주문 내역이 없습니다.</p>
        )}

        {/* 페이지네이션 버튼 */}
        <div style={{ marginTop: "20px", textAlign: "center" }}>
          <button
            disabled={page === 0}
            onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
          >
            이전 페이지
          </button>
          <span style={{ margin: "0 15px" }}>현재 페이지: {page + 1}</span>
          <button onClick={() => setPage((prev) => prev + 1)}>
            다음 페이지
          </button>
        </div>
      </PageContainer>
    </BasicLayout>
  );
}

export default OrderListPage;

import React from "react";
import styled from "styled-components";
import BasicLayout from "../layouts/BasicLayout";

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

const renderOrderCard = (order) => (
  <OrderCard key={order.id}>
    <OrderInfo>
      <OrderDate>주문일시 ({order.date})</OrderDate>
      <OrderStatus>주문상태 ({order.status})</OrderStatus>
      <ProductPreview>
        <ProductImage>이미지</ProductImage>
        <ProductDetails>
          <p>주문번호 : {order.id}</p>
          <p>주문물품 : {order.items}</p>
          <p>주문가격 : {order.price}</p>
        </ProductDetails>
      </ProductPreview>
    </OrderInfo>
    <ActionButtons>
      <ActionButton $primary>주문 상세 내역</ActionButton>
      <ActionButton>배송 조회</ActionButton>
      <ActionButton>취소</ActionButton>
      <ActionButton>리뷰 작성</ActionButton>
    </ActionButtons>
  </OrderCard>
);

function OrderListPage() {
  return (
    <BasicLayout>
      <PageContainer>
        <PageTitle>주문목록</PageTitle>
        {orders.map(renderOrderCard)}
      </PageContainer>
    </BasicLayout>
  );
}

export default OrderListPage;

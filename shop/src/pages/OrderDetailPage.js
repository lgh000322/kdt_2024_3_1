import React, { useState, useEffect } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useParams, useSearchParams } from "react-router-dom";
import { showOrderDetail } from "../api/OrderDetailApi";

function OrderDetailPage() {
  const { orderHistoryId } = useParams();
  const [searchParams] = useSearchParams();
  const accessToken = searchParams.get("token");

  const [orderDetail, setOrderDetail] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchOrderDetail = async () => {
      if (!accessToken) {
        setError("❌ 인증 오류: 로그인 후 이용해주세요.");
        setLoading(false);
        return;
      }

      try {
        const response = await showOrderDetail(orderHistoryId, accessToken);
        setOrderDetail(response || {});

        // ✅ 상품 목록 콘솔 출력
        if (response?.orderDetailProducts) {
          console.log(
            "📦 가져온 주문 상품 리스트:",
            response.orderDetailProducts
          );
        } else {
          console.log("⚠️ 주문 상품이 없습니다.");
        }
      } catch (error) {
        setError("🚨 주문 정보를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };

    if (orderHistoryId) {
      fetchOrderDetail();
    }
  }, [orderHistoryId, accessToken]);

  if (loading) {
    return (
      <BasicLayout>
        <p>🔄 주문 상세 정보를 불러오는 중...</p>
      </BasicLayout>
    );
  }

  return (
    <BasicLayout>
      <div style={styles.container}>
        {/* 헤더 섹션 */}
        <header style={styles.header}>
          <h1 style={styles.title}>주문 상세 정보</h1>
          <p style={styles.orderNumber}>
            주문번호: {orderDetail.orderHistoryId}
          </p>
        </header>

        {/* 주문 정보 카드 */}
        <section style={styles.card}>
          <h2 style={styles.cardTitle}>배송 정보</h2>
          <div style={styles.detailGrid}>
            <DetailItem label="수령인" value={orderDetail.orderName} />
            <DetailItem label="연락처" value={orderDetail.phoneNumber} />
            <DetailItem label="주소" value={orderDetail.deliveryAddress} />
            {/* 상품 마다 배송 상태가 다를 수 있어서 주문상품마다 배송상태를 보이게 구현함. 
            <DetailItem
              label="배송 상태"
              value={orderDetail.delivaeryState}
              status="active"
            /> */}
          </div>
        </section>

        {/* 상품 정보 카드 */}
        <section style={styles.card}>
          <h2 style={styles.cardTitle}>주문 상품</h2>
          {orderDetail.orderDetailProducts &&
          orderDetail.orderDetailProducts.length > 0 ? (
            orderDetail.orderDetailProducts.map((product) => (
              <div key={product.orderProductId} style={styles.productCard}>
                <div style={styles.imageContainer}>
                  <img
                    src={product.imageUrl}
                    alt={product.productName}
                    style={{
                      width: "160px",
                      height: "160px",
                      borderRadius: "8px",
                    }}
                  />
                </div>
                <div style={styles.productInfo}>
                  <h3 style={styles.productName}>{product.mainProductName}</h3>
                  <p style={styles.productPrice}>수량: {product.count}개</p>
                  <p style={styles.productPrice}>
                    가격: {product.totalPrice.toLocaleString()} 원
                  </p>
                  <p style={styles.productPrice}>
                    배송 상태: {product.orderProductDeliveryInfo.deliveryStatus}
                  </p>
                </div>
              </div>
            ))
          ) : (
            <p>📦 주문한 상품이 없습니다.</p>
          )}
        </section>

        {/* 액션 버튼 그룹 */}
        <div style={styles.buttonGroup}>
          <button style={styles.secondaryButton}>주문내역 삭제</button>
        </div>
      </div>
    </BasicLayout>
  );
}

// 재사용 가능한 디테일 아이템 컴포넌트
function DetailItem({ label, value, status }) {
  return (
    <div style={styles.detailItem}>
      <span style={styles.detailLabel}>{label}</span>
      <span
        style={
          status
            ? { ...styles.detailValue, ...styles[status] }
            : styles.detailValue
        }
      >
        {value}
      </span>
    </div>
  );
}

////////////////////////////////////////////////// 개선된 스타일 정의
const styles = {
  container: {
    padding: "40px 24px",
    backgroundColor: "#f9fafb",
    minHeight: "100vh",
    fontFamily: "'Noto Sans KR', sans-serif",
  },
  header: {
    marginBottom: "48px",
    textAlign: "center",
  },
  title: {
    fontSize: "32px",
    fontWeight: "700",
    color: "#1f2937",
    marginBottom: "8px",
    letterSpacing: "-0.5px",
  },
  orderNumber: {
    color: "#6b7280",
    fontSize: "14px",
  },
  card: {
    backgroundColor: "white",
    borderRadius: "16px",
    padding: "32px",
    marginBottom: "24px",
    boxShadow: "0 4px 6px -1px rgba(0, 0, 0, 0.05)",
  },
  cardTitle: {
    fontSize: "22px",
    fontWeight: "600",
    color: "#1f2937",
    marginBottom: "28px",
    paddingBottom: "16px",
    borderBottom: "2px solid #f3f4f6",
  },
  detailGrid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))",
    gap: "24px",
  },
  detailItem: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "16px 0",
    borderBottom: "1px solid #f3f4f6",
  },
  detailLabel: {
    color: "#6b7280",
    fontSize: "15px",
    fontWeight: "500",
  },
  detailValue: {
    color: "#1f2937",
    fontWeight: "600",
    fontSize: "16px",
  },
  active: {
    color: "#3b82f6",
    fontWeight: "700",
  },
  productCard: {
    display: "flex",
    gap: "32px",
    alignItems: "center",
    padding: "20px",
    backgroundColor: "#f3f4f6",
    borderRadius: "12px",
  },
  imageContainer: {
    flexShrink: 0,
    position: "relative",
  },
  imagePlaceholder: {
    width: "160px",
    height: "160px",
    backgroundColor: "#e5e7eb",
    borderRadius: "8px",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    transition: "transform 0.2s ease",
    ":hover": {
      transform: "scale(1.02)",
    },
  },
  imageText: {
    color: "#6b7280",
    fontSize: "14px",
    fontWeight: "500",
  },
  productInfo: {
    flexGrow: 1,
  },
  productName: {
    fontSize: "20px",
    fontWeight: "700",
    color: "#1f2937",
    marginBottom: "12px",
  },
  productPrice: {
    fontSize: "18px",
    color: "#1f2937",
    fontWeight: "600",
    letterSpacing: "-0.5px",
  },
  buttonGroup: {
    display: "flex",
    gap: "20px",
    justifyContent: "center",
    marginTop: "40px",
  },
  secondaryButton: {
    padding: "16px 40px",
    backgroundColor: "transparent",
    color: "#ef4444",
    borderRadius: "8px",
    border: "2px solid #ef4444",
    cursor: "pointer",
    fontSize: "16px",
    fontWeight: "700",
    transition: "all 0.2s ease",
    ":hover": {
      backgroundColor: "#fee2e2",
    },
  },
};

export default OrderDetailPage;

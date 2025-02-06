import React from "react";
import BasicLayout from "../layouts/BasicLayout";

function OrderDetailPage() {
  return (
    <BasicLayout>
      <div style={styles.container}>
        {/* 헤더 섹션 */}
        <header style={styles.header}>
          <h1 style={styles.title}>주문 상세 정보</h1>
          <p style={styles.orderNumber}>주문번호: #20250202-001</p>
        </header>

        {/* 주문 정보 카드 */}
        <section style={styles.card}>
          <h2 style={styles.cardTitle}>배송 정보</h2>
          <div style={styles.detailGrid}>
            <DetailItem label="수령인" value="홍길동" />
            <DetailItem label="연락처" value="010-1234-5678" />
            <DetailItem label="주소" value="서울특별시 강남구 테헤란로 427" />
            <DetailItem label="배송 상태" value="배송중" status="active" />
          </div>
        </section>

        {/* 상품 정보 카드 */}
        <section style={styles.card}>
          <h2 style={styles.cardTitle}>주문 상품</h2>
          <div style={styles.productCard}>
            {/* 개선된 상품 이미지 영역 */}
            <div style={styles.imageContainer}>
              <div style={styles.imagePlaceholder}>
                <span style={styles.imageText}>상품 이미지</span>
              </div>
            </div>
            <div style={styles.productInfo}>
              <h3 style={styles.productName}>프리미엄 커피 세트 외 2개</h3>
              <p style={styles.productPrice}>총 결제 금액: 65,000원</p>
            </div>
          </div>
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

// 개선된 스타일 정의
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

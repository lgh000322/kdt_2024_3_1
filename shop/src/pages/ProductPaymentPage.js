import React from "react";
import BasicLayout from "../layouts/BasicLayout";

function ProductPaymentPage() {
  return (
    <BasicLayout>
      <div style={{ 
        maxWidth: "600px", 
        margin: "40px auto", 
        padding: "30px",
        fontFamily: "'Noto Sans KR', sans-serif",
        backgroundColor: "#ffffff",
        borderRadius: "12px",
        boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
      }}>
        <h2 style={{ 
          fontSize: "24px", 
          fontWeight: "600", 
          marginBottom: "30px",
          color: "#333"
        }}>
          주문/결제
        </h2>
        
        <form>
          <div style={{ marginBottom: "24px" }}>
            <label style={labelStyle}>받는 사람 이름</label>
            <input
              type="text"
              name="recipientName"
              placeholder="배송지 이름을 입력해주세요."
              style={inputStyle}
            />
          </div>

          <div style={{ marginBottom: "24px" }}>
            <label style={labelStyle}>전화번호</label>
            <input
              type="tel"
              name="phoneNumber"
              placeholder="전화번호를 입력해주세요."
              style={inputStyle}
            />
          </div>

          <div style={{ marginBottom: "24px" }}>
            <label style={labelStyle}>주소</label>
            <select
              name="address"
              style={selectStyle}
            >
              <option value="">주소를 선택해주세요</option>
              <option value="서울특별시">서울특별시</option>
              <option value="부산광역시">부산광역시</option>
              <option value="대구광역시">대구광역시</option>
              <option value="인천광역시">인천광역시</option>
              <option value="광주광역시">광주광역시</option>
              <option value="대전광역시">대전광역시</option>
              <option value="울산광역시">울산광역시</option>
              <option value="경기도">경기도</option>
            </select>
          </div>

          <div style={{ marginBottom: "24px" }}>
            <label style={labelStyle}>배송 메시지</label>
            <input
              type="text"
              name="deliveryMessage"
              placeholder="배송 메시지를 입력해주세요."
              style={inputStyle}
            />
          </div>

          <div style={orderInfoStyle}>
            <h3 style={{ 
              fontSize: "18px", 
              fontWeight: "600", 
              marginBottom: "16px",
              color: "#333"
            }}>
              주문 상품 정보
            </h3>
            <div style={{ display: "flex", alignItems: "center" }}>
              <div style={productImageStyle}>
                이미지
              </div>
              <div style={{ flex: 1 }}>
                <p style={{ fontSize: "16px", fontWeight: "500", marginBottom: "8px" }}>
                  상품 이름 외 x개
                </p>
                <p style={{ fontSize: "18px", fontWeight: "600", color: "#2C5282" }}>
                  ₩ 50,000
                </p>
              </div>
            </div>
          </div>

          <button type="submit" style={buttonStyle}>
            결제하기
          </button>
        </form>
      </div>
    </BasicLayout>
  );
}

// 스타일 상수
const labelStyle = {
  display: "block",
  marginBottom: "8px",
  fontSize: "14px",
  fontWeight: "500",
  color: "#4A5568"
};

const inputStyle = {
  width: "100%",
  padding: "12px",
  marginTop: "4px",
  boxSizing: "border-box",
  border: "1px solid #E2E8F0",
  borderRadius: "6px",
  fontSize: "14px",
  transition: "all 0.3s ease",
  ":focus": {
    borderColor: "#4299E1",
    outline: "none",
    boxShadow: "0 0 0 3px rgba(66, 153, 225, 0.5)"
  }
};

const selectStyle = {
  ...inputStyle,
  appearance: "none",
  backgroundRepeat: "no-repeat",
  backgroundPosition: "right 12px center",
  backgroundSize: "12px auto"
};

const orderInfoStyle = {
  marginBottom: "24px",
  backgroundColor: "#F7FAFC",
  padding: "20px",
  borderRadius: "8px",
  border: "1px solid #EDF2F7"
};

const productImageStyle = {
  width: "80px",
  height: "80px",
  backgroundColor: "#EDF2F7",
  marginRight: "20px",
  borderRadius: "8px",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  fontSize: "12px",
  color: "#718096"
};

const buttonStyle = {
  width: "100%",
  padding: "16px",
  backgroundColor: "#4A5568",
  color: "#ffffff",
  border: "none",
  borderRadius: "8px",
  fontSize: "16px",
  fontWeight: "600",
  cursor: "pointer",
  transition: "background-color 0.3s ease",
  ":hover": {
    backgroundColor: "#2D3748"
  }
};

export default ProductPaymentPage;

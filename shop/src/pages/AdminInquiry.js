import React from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";

const userRole = "manager";  // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할

function AdminSellerPage() {
  return (
      <AdminPageLayout role={userRole}>
        {/* 검색 필터 섹션 */}
        <div
          style={{
            padding: "80px", // 동일한 패딩
            border: "1px solid #ddd",
            margin: "60px auto", // 동일한 마진
            width: "80%", // 동일한 너비
            backgroundColor: "#f7faff",
            borderRadius: "10px", // 둥근 모서리
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)", // 그림자 효과
          }}
        >
          <h2
            style={{
              marginBottom: "20px",
              color: "#333",
              fontSize: "20px",
              fontWeight: "bold",
            }}
          >
            1:1 상담 문의
          </h2>
          <div style={{ display: "flex", alignItems: "center", marginBottom: "20px" }}>
            <label
              htmlFor="sellerName"
              style={{
                marginRight: "10px",
                fontWeight: "bold",
                color: "#555",
                minWidth: "80px",
              }}
            >
              아이디
            </label>
            <input
              type="text"
              id="sellerName"
              placeholder="판매자명을 입력하세요"
              style={{
                flexGrow: 1,
                padding: "10px",
                border: "1px solid #ccc",
                borderRadius: "5px", // 둥근 모서리
                width: "250px",
              }}
            />
            <button
              style={{
                marginLeft: "10px",
                padding: "10px 20px",
                backgroundColor: "#007BFF",
                color: "#fff",
                borderRadius: "5px", // 둥근 모서리
                border: "none",
                cursor: "pointer",
                fontSize: "16px",
              }}
            >
              검색
            </button>
          </div>
        </div>

        {/* 테이블 섹션 */}
        <div
          style={{
            padding: "20px", // 동일한 패딩
            border: "1px solid #ddd",
            margin: "60px auto", // 동일한 마진
            width: "80%", // 동일한 너비
            backgroundColor: "#fff",
            borderRadius: "10px", // 둥근 모서리
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)", // 그림자 효과
            minHeight: "50vh", // 세로 길이 늘리기
          }}
        ><h2 style={{
            marginBottom: "20px",
            color: "#333",
            fontSize: "20px",
            fontWeight: "bold"
          }}>문의 목록</h2>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr style={{ backgroundColor: "#f7faff", textAlign: "center" }}>
                {["번호", "등록자", "제목", "등록일", "작성자ID"].map((header) => (
                  <th
                    key={header}
                    style={{
                      borderBottom: "2px solid #ddd",
                      padding: "10px",
                      fontWeight: "bold",
                      color: "#555",
                    }}
                  >
                    {header}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {/* 데이터가 없을 경우 빈 상태 유지 */}
              <tr>
                <td colSpan="5" style={{ textAlign: "center", paddingTop: "20px", color: "#999" }}>
                  데이터가 없습니다.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </AdminPageLayout>
  );
}

export default AdminSellerPage;

import React, { useState, useEffect } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";

const userRole = "manager";  // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할

// 더미 데이터 추가
const dummyData = [
  { id: 1, name: "김철수", userId: "seller1", companyName: "철수네 상회", phone: "010-1234-5678" },
  { id: 2, name: "이영희", userId: "seller2", companyName: "영희 마켓", phone: "010-2345-6789" },
  { id: 3, name: "박지성", userId: "seller3", companyName: "지성 상점", phone: "010-3456-7890" },
  { id: 4, name: "최동욱", userId: "seller4", companyName: "동욱이네", phone: "010-4567-8901" },
  { id: 5, name: "정수민", userId: "seller5", companyName: "수민 농장", phone: "010-5678-9012" },
  { id: 6, name: "강민호", userId: "seller6", companyName: "민호 식품", phone: "010-6789-0123" },
  { id: 7, name: "윤서연", userId: "seller7", companyName: "서연 공방", phone: "010-7890-1234" },
  { id: 8, name: "임재현", userId: "seller8", companyName: "재현 전자", phone: "010-8901-2345" },
  { id: 9, name: "한지원", userId: "seller9", companyName: "지원 문구", phone: "010-9012-3456" },
  { id: 10, name: "오승훈", userId: "seller10", companyName: "승훈 가구", phone: "010-0123-4567" },
  { id: 11, name: "서민재", userId: "seller11", companyName: "민재 베이커리", phone: "010-1111-2222" },
  { id: 12, name: "노유진", userId: "seller12", companyName: "유진 화장품", phone: "010-2222-3333" },
  { id: 13, name: "류현진", userId: "seller13", companyName: "현진 스포츠", phone: "010-3333-4444" },
  { id: 14, name: "백승호", userId: "seller14", companyName: "승호 서점", phone: "010-4444-5555" },
  { id: 15, name: "조아라", userId: "seller15", companyName: "아라 패션", phone: "010-5555-6666" },
  { id: 16, name: "장미란", userId: "seller16", companyName: "미란 헬스케어", phone: "010-6666-7777" },
  { id: 17, name: "김연아", userId: "seller17", companyName: "연아 아이스크림", phone: "010-7777-8888" },
  { id: 18, name: "손흥민", userId: "seller18", companyName: "흥민 축구용품", phone: "010-8888-9999" },
  { id: 19, name: "박태환", userId: "seller19", companyName: "태환 수영용품", phone: "010-9999-0000" },
  { id: 20, name: "이상화", userId: "seller20", companyName: "상화 스케이트", phone: "010-0000-1111" },
];

function AdminSellerPage() {
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredData, setFilteredData] = useState(dummyData);

  useEffect(() => {
    const filtered = dummyData.filter((seller) =>
      seller.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      seller.userId.toLowerCase().includes(searchTerm.toLowerCase()) ||
      seller.companyName.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredData(filtered);
  }, [searchTerm]);

  return (
    <AdminPageLayout role={userRole}>
      {/* 검색 필터 섹션 */}
      <div
        style={{
          padding: "80px",
          border: "1px solid #ddd",
          margin: "60px auto",
          width: "80%",
          backgroundColor: "#f7faff",
          borderRadius: "10px",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
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
          판매자 검색
        </h2>
        <div style={{ display: "flex", alignItems: "center", marginBottom: "20px" }}>
          <label
            htmlFor="searchTerm"
            style={{
              marginRight: "10px",
              fontWeight: "bold",
              color: "#555",
              minWidth: "80px",
            }}
          >
            검색어
          </label>
          <input
            type="text"
            id="searchTerm"
            placeholder="회원명, 아이디 또는 공급사명을 입력하세요"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            style={{
              flexGrow: 1,
              padding: "10px",
              border: "1px solid #ccc",
              borderRadius: "5px",
              width: "250px",
            }}
          />
        </div>
      </div>

      {/* 테이블 섹션 */}
      <div
        style={{
          padding: "20px",
          border: "1px solid #ddd",
          margin: "60px auto",
          width: "80%",
          backgroundColor: "#fff",
          borderRadius: "10px",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        }}
      >
        <h2 style={{
          marginBottom: "20px",
          color: "#333",
          fontSize: "20px",
          fontWeight: "bold"
        }}>판매자 목록</h2>
        <div style={{ maxHeight: "400px", overflowY: "auto" }}>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead style={{ position: "sticky", top: 0, backgroundColor: "#f7faff", zIndex: 1 }}>
              <tr>
                {["번호", "회원명", "아이디", "공급사명", "전화번호"].map((header) => (
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
              {filteredData.map((seller) => (
                <tr key={seller.id} style={{ borderBottom: "1px solid #ddd" }}>
                  <td style={{ padding: "10px", textAlign: "center" }}>{seller.id}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{seller.name}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{seller.userId}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{seller.companyName}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{seller.phone}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminSellerPage;

import React, { useState, useMemo } from 'react';
import AdminPageLayout from '../layouts/AdminPageLayout';

const userRole = "manager";

// 더미 데이터에 승인 상태 추가
const initialDummyData = [
  { id: 1, registrant: "김철수", reason: "신규 판매자 등록", date: "2025-01-20", userId: "seller1", status: "대기중" },
  { id: 2, registrant: "이영희", reason: "상품 카테고리 확장", date: "2025-01-19", userId: "seller2", status: "승인" },
  { id: 3, registrant: "박지성", reason: "브랜드 입점", date: "2025-01-18", userId: "seller3", status: "대기중" },
  { id: 4, registrant: "최동욱", reason: "신규 판매자 등록", date: "2025-01-17", userId: "seller4", status: "거절" },
  { id: 5, registrant: "정수민", reason: "해외 상품 판매", date: "2025-01-16", userId: "seller5", status: "대기중" },
  { id: 6, registrant: "강민호", reason: "신규 판매자 등록", date: "2025-01-15", userId: "seller6", status: "승인" },
  { id: 7, registrant: "윤서연", reason: "상품 라인 확장", date: "2025-01-14", userId: "seller7", status: "대기중" },
  { id: 8, registrant: "임재현", reason: "온라인 매장 오픈", date: "2025-01-13", userId: "seller8", status: "승인" },
  { id: 9, registrant: "한지원", reason: "신규 판매자 등록", date: "2025-01-12", userId: "seller9", status: "대기중" },
  { id: 10, registrant: "오승훈", reason: "브랜드 제품 판매", date: "2025-01-11", userId: "seller10", status: "거절" },
  { id: 11, registrant: "서민재", reason: "신규 판매자 등록", date: "2025-01-10", userId: "seller11", status: "대기중" },
  { id: 12, registrant: "노유진", reason: "해외 브랜드 입점", date: "2025-01-09", userId: "seller12", status: "승인" },
  { id: 13, registrant: "류현진", reason: "스포츠 용품 판매", date: "2025-01-08", userId: "seller13", status: "대기중" },
  { id: 14, registrant: "백승호", reason: "신규 판매자 등록", date: "2025-01-07", userId: "seller14", status: "승인" },
  { id: 15, registrant: "조아라", reason: "패션 아이템 판매", date: "2025-01-06", userId: "seller15", status: "대기중" },
];

function AdminAcceptPage() {
  const [searchTerm, setSearchTerm] = useState("");
  const [dummyData, setDummyData] = useState(initialDummyData);
  const [selectedRowIndex, setSelectedRowIndex] = useState(null);

  const filteredData = useMemo(() => {
    return dummyData.filter((item) =>
      item.registrant.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.userId.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }, [dummyData, searchTerm]);

  const handleRowClick = (index) => {
    setSelectedRowIndex(index);
  };

  // 승인 상태에 따른 배경색 설정
  const getStatusColor = (status) => {
    switch(status) {
      case "승인": return "#e6ffed";
      case "거절": return "#ffebe6";
      default: return "transparent";
    }
  };

  const handleApprove = () => {
    if (selectedRowIndex !== null) {
      const newData = [...dummyData];
      const actualIndex = dummyData.findIndex(item => item.id === filteredData[selectedRowIndex].id);
      newData[actualIndex].status = "승인";
      setDummyData(newData);
    }
  };

  const handleReject = () => {
    if (selectedRowIndex !== null) {
      const newData = [...dummyData];
      const actualIndex = dummyData.findIndex(item => item.id === filteredData[selectedRowIndex].id);
      newData[actualIndex].status = "거절";
      setDummyData(newData);
    }
  };

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
            placeholder="등록자 또는 등록자ID를 입력하세요"
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
          minHeight: "50vh",
        }}
      >
        <h2 style={{
          marginBottom: "20px",
          color: "#333",
          fontSize: "20px",
          fontWeight: "bold"
        }}>
          판매자 목록
        </h2>
        <div style={{ maxHeight: "400px", overflowY: "auto", marginBottom: "20px" }}>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead style={{ position: "sticky", top: 0, backgroundColor: "#f7faff", zIndex: 1 }}>
              <tr>
                {["번호", "등록자", "등록 사유", "등록일", "작성자ID", "승인여부"].map((header) => (
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
              {filteredData.map((item, index) => (
                <tr 
                  key={item.id} 
                  style={{ 
                    borderBottom: "1px solid #ddd",
                    backgroundColor: index === selectedRowIndex ? "#e6f7ff" : "transparent",
                    cursor: "pointer"
                  }}
                  onClick={() => handleRowClick(index)}
                >
                  <td style={{ padding: "10px", textAlign: "center" }}>{item.id}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{item.registrant}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{item.reason}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{item.date}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{item.userId}</td>
                  <td style={{ 
                    padding: "10px", 
                    textAlign: "center", 
                    backgroundColor: getStatusColor(item.status)
                  }}>
                    {item.status}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* 승인 및 거절 버튼 */}
        <div 
          style={{
            display: 'flex',
            justifyContent: 'flex-end',
            gap: '10px',
          }}
        >
          <button 
            style={{
              backgroundColor: '#28a745',
              color: '#fff',
              borderRadius: '5px',
              padding: '10px 20px',
              fontSize: '16px',
              border: 'none',
              cursor: 'pointer',
            }}
            onClick={handleApprove}
          >
            승인
          </button>
          
          <button 
            style={{
              backgroundColor: '#dc3545',
              color: '#fff',
              borderRadius: '5px',
              padding: '10px 20px',
              fontSize: '16px',
              border: 'none',
              cursor: 'pointer',
            }}
            onClick={handleReject}
          >
            거절
          </button>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminAcceptPage;

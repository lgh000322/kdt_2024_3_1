import React, { useState } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";

const userRole = "manager";  // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할

// 더미 데이터
const dummyData = [
  { id: 1, name: "김철수", userId: "user1", level: "일반 회원", joinDate: "2025-01-20" },
  { id: 2, name: "이영희", userId: "seller1", level: "판매자", joinDate: "2025-01-19" },
  { id: 3, name: "박지성", userId: "user2", level: "일반 회원", joinDate: "2025-01-18" },
  { id: 4, name: "최동욱", userId: "user3", level: "일반 회원", joinDate: "2025-01-17" },
  { id: 5, name: "정수민", userId: "seller2", level: "판매자", joinDate: "2025-01-16" },
  { id: 6, name: "강민호", userId: "user4", level: "일반 회원", joinDate: "2025-01-15" },
  { id: 7, name: "윤서연", userId: "seller3", level: "판매자", joinDate: "2025-01-14" },
  { id: 8, name: "임재현", userId: "user5", level: "일반 회원", joinDate: "2025-01-13" },
  { id: 9, name: "한지원", userId: "user6", level: "일반 회원", joinDate: "2025-01-12" },
  { id: 10, name: "오승훈", userId: "seller4", level: "판매자", joinDate: "2025-01-11" },
  { id: 11, name: "서민재", userId: "user7", level: "일반 회원", joinDate: "2025-01-10" },
  { id: 12, name: "노유진", userId: "seller5", level: "판매자", joinDate: "2025-01-09" },
  { id: 13, name: "류현진", userId: "user8", level: "일반 회원", joinDate: "2025-01-08" },
  { id: 14, name: "백승호", userId: "user9", level: "일반 회원", joinDate: "2025-01-07" },
  { id: 15, name: "조아라", userId: "seller6", level: "판매자", joinDate: "2025-01-06" },
  { id: 16, name: "장미란", userId: "user10", level: "일반 회원", joinDate: "2025-01-05" },
  { id: 17, name: "김연아", userId: "seller7", level: "판매자", joinDate: "2025-01-04" },
  { id: 18, name: "손흥민", userId: "user11", level: "일반 회원", joinDate: "2025-01-03" },
  { id: 19, name: "박태환", userId: "user12", level: "일반 회원", joinDate: "2025-01-02" },
  { id: 20, name: "이상화", userId: "seller8", level: "판매자", joinDate: "2025-01-01" },
];

function AdminUserPage() {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchLevel, setSearchLevel] = useState("전체");
  const [filteredData, setFilteredData] = useState(dummyData);

  const handleSearch = () => {
    const filtered = dummyData.filter((user) => {
      const searchMatch = user.userId.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          user.name.toLowerCase().includes(searchTerm.toLowerCase());
      const levelMatch = searchLevel === "전체" || user.level === searchLevel;
      return searchMatch && levelMatch;
    });
    setFilteredData(filtered);
  };

  return (
    <AdminPageLayout role={userRole}>
       <div>
        {/* 검색 필터 섹션 */}
        <div style={{
          padding: "80px",
          border: "1px solid #ddd",
          margin: "60px auto",
          width: "80%",
          backgroundColor: "#f7faff",
          borderRadius: "10px",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)"
        }}>
          <h2 style={{
            marginBottom: "20px",
            color: "#333",
            fontSize: "20px",
            fontWeight: "bold"
          }}>회원 검색</h2>
          <div style={{ marginBottom: "15px" }}>
            <label htmlFor="userId" style={{
              marginRight: "10px",
              fontWeight: "bold",
              color: "#555"
            }}>아이디:</label>
            <input
              type="text"
              id="userId"
              placeholder="아이디 입력"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              style={{
                padding: "10px",
                border: "1px solid #ccc",
                borderRadius: "5px",
                width: "250px"
              }}
            />
          </div>
          <div style={{ marginBottom: "20px" }}>
            <span style={{ fontWeight: "bold", color: "#555" }}>회원 등급:</span>
            {["전체", "일반 회원", "판매자"].map((level) => (
              <label key={level} style={{ marginLeft: "15px", color: "#333" }}>
                <input
                  type="radio"
                  name="memberLevel"
                  value={level}
                  checked={searchLevel === level}
                  onChange={(e) => setSearchLevel(e.target.value)}
                /> {level}
              </label>
            ))}
          </div>
          <button
            onClick={handleSearch}
            style={{
              backgroundColor: "#007BFF",
              color: "#fff",
              border: "none",
              padding: "10px 20px",
              borderRadius: "5px",
              cursor: "pointer",
              fontSize: "16px"
            }}
          >
            검색
          </button>
        </div>

        {/* 테이블 섹션 */}
        <div style={{
          padding: "20px",
          border: "1px solid #ddd",
          margin: "20px auto",
          width: "80%",
          backgroundColor: "#fff",
          borderRadius: "10px",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        }}>
          <h2 style={{
            marginBottom: "20px",
            color: "#333",
            fontSize: "20px",
            fontWeight: "bold"
          }}>회원 목록</h2>
          <div style={{
            maxHeight: "400px",
            overflowY: "auto",
          }}>
            <table style={{
              width: "100%",
              borderCollapse: "collapse"
            }}>
              <thead style={{
                position: "sticky",
                top: 0,
                backgroundColor: "#f7faff",
                zIndex: 1,
              }}>
                <tr>
                  {["번호", "이름", "아이디", "회원등급", "가입일"].map((header, index) => (
                    <th key={header} style={{
                      borderBottom: "2px solid #ddd",
                      padding: "10px",
                      fontWeight: "bold",
                      color: "#555",
                      width: index === 0 ? "10%" : index === 4 ? "20%" : "17.5%",
                      textAlign: index === 0 ? "center" : "left"
                    }}>{header}</th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {filteredData.map((user) => (
                  <tr key={user.id} style={{ borderBottom: "1px solid #ddd" }}>
                    <td style={{ padding: "10px", textAlign: "center" }}>{user.id}</td>
                    <td style={{ padding: "10px", textAlign: "left" }}>{user.name}</td>
                    <td style={{ padding: "10px", textAlign: "left" }}>{user.userId}</td>
                    <td style={{ padding: "10px", textAlign: "left" }}>{user.level}</td>
                    <td style={{ padding: "10px", textAlign: "left" }}>{user.joinDate}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminUserPage;

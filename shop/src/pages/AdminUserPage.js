import React, { useState, useEffect } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";

const userRole = "manager";  // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할


function AdminUserPage() {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchLevel, setSearchLevel] = useState("전체");
  const [filteredData, setFilteredData] = useState([]);
  const [userData, setUserData] = useState([]);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/members"); // Replace with your API endpoint
        if (!response.ok) {
          throw new Error("Failed to fetch user data");
        }
        const data = await response.json();
        setUserData(data);
        setFilteredData(data); // Initialize filtered data with all users
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchUserData();
  }, []);

  const handleSearch = () => {
    const filtered = userData.filter((user) => {
      const searchMatch =
        user.userId.toLowerCase().includes(searchTerm.toLowerCase()) ||
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
                  {["번호", "이름", "이메일","성별", "전화번호", "회원등급", "가입일"].map((header, index) => (
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

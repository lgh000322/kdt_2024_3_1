import React, { useState, useEffect } from 'react';
import AdminPageLayout from '../layouts/AdminPageLayout';
import { useSelector } from "react-redux";
import { sellerAccept } from "../api/memberApi";

const userRole = "manager";

function AdminAcceptPage() {
  const loginSlice = useSelector((state) => state.loginSlice);
  const [formData, setFormData] = useState([]);
  const [filteredData, setFilteredData] = useState([]); // 필터링된 데이터
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedRowIndex, setSelectedRowIndex] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMembers = async () => {
      try {
        const accessToken = loginSlice.accessToken;
        console.log("Access Token:", accessToken);

        const response = await sellerAccept(accessToken);
        console.log("API Response:", response);

        // 이름 기준으로 정렬
        const sortedData = response.data.sort((a, b) =>
          a.name.localeCompare(b.name)
        );

        setFormData(sortedData);
        setFilteredData(sortedData); // 초기값 설정
      } catch (error) {
        console.error("Failed to fetch members:", error);
      } finally {
        setLoading(false);
      }
    };
    if (loginSlice.accessToken) fetchMembers();
  }, [loginSlice.accessToken]);


useEffect(() => {
    if (searchTerm === "") {
      setFilteredData(formData); // 검색어가 없으면 전체 데이터 표시
    } else {
      const filtered = formData.filter((member) =>
        member.memberName.toLowerCase().includes(searchTerm.toLowerCase()) || 
        member.title.toLowerCase().includes(searchTerm.toLowerCase())  
      );
      setFilteredData(filtered);
    }
  }, [searchTerm, formData]);

  if (loading) return <div>Loading...</div>;

  const handleRowClick = (index) => {
    setSelectedRowIndex(index);
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
            placeholder="등록자 또는 제목을 입력하세요"
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
                {["번호", "등록자", "등록 제목", "등록일"].map((header) => (
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
              {filteredData.map((member, index) => (
                <tr 
                  key={member.id} 
                  style={{ 
                    borderBottom: "1px solid #ddd",
                    backgroundColor: index === selectedRowIndex ? "#e6f7ff" : "transparent",
                    cursor: "pointer"
                  }}
                  onClick={() => handleRowClick(index)}
                >
                  <td style={{ padding: "10px", textAlign: "center" }}>{index + 1}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{member.memberName}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{member.title}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{member.createAt}</td>
                </tr>
              ))}
                {filteredData.length === 0 && (
                  <tr>
                    <td colSpan="6" style={{ textAlign:"center", padding:"20px" }}>검색 결과가 없습니다.</td>
                  </tr>
                )}
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
          >
            거절
          </button>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminAcceptPage;

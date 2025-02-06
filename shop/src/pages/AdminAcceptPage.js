import React, { useState, useEffect } from 'react';
import AdminPageLayout from '../layouts/AdminPageLayout';
import { useSelector } from "react-redux";
import { sellerAccept } from "../api/memberApi";
import { sellerAcceptSubmit } from '../api/memberApi';

const userRole = "manager";

function AdminAcceptPage() {
  const loginSlice = useSelector((state) => state.loginSlice);
  const [formData, setFormData] = useState([]);
  const [filteredData, setFilteredData] = useState([]); // 필터링된 데이터
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedRowIndex, setSelectedRowIndex] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const accessToken = loginSlice.accessToken;
    sellerAccept(accessToken).then(res=>{
      const data = res.data;

      const sortedData = data.sort((a,b)=>{
        a.name.localeCompare(b.name)
      })

      setFormData(sortedData)
      setFilteredData(sortedData)
      setLoading(false)
    })
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

  const handleApprove = async () => {
    if (selectedRowIndex === null) {
      alert("승인할 항목을 선택하세요.");
      return;
    }
  
    try {
      const selectedMember = filteredData[selectedRowIndex];
      const accessToken = loginSlice.accessToken;
  
      // 선택된 데이터의 authorityId를 사용하여 API 호출
      const response = await sellerAcceptSubmit(accessToken, selectedMember.authorityId);
  
      if (response.code === 200) {
        alert("성공");
  
        // 승인 후 리스트에서 제거
        const updatedFormData = formData.filter((member) => member.id !== selectedMember.id);
        setFormData(updatedFormData);
        setFilteredData(updatedFormData);
        setSelectedRowIndex(null); // 선택 초기화
      } else {
        alert(`승인 실패: ${response.message}`);
      }
    } catch (error) {
      console.error("승인 실패:", error);
      alert("승인 중 오류가 발생했습니다.");
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
                {["번호", "등록자", "등록 제목", "등록일", "첨부 파일"].map((header) => (
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
                  <td style={{ padding: "10px", textAlign:"center" }}>{index + 1}</td>
                  <td style={{ padding:"10px", textAlign:"center" }}>{member.memberName}</td>
                  <td style={{ padding:"10px", textAlign:"center" }}>{member.title}</td>
                  <td style={{ padding:"10px", textAlign:"center" }}>{member.createAt}</td>
                  <td style={{ padding:"10px", textAlign:"center" }}>
                    <button style={{
                      backgroundColor:'#007bff',
                      color:'#fff',
                      borderRadius:'5px',
                      padding:'10px',
                      fontSize:'16px',
                      border:'none',
                      cursor:'pointer'
                    }}>Download</button></td>
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
            display:'flex',
            justifyContent:'flex-end',
            gap:'10px'
          }}
        >
          <button 
            onClick={handleApprove}
            style={{
              backgroundColor:'#28a745',
              color:'#fff',
              borderRadius:'5px',
              padding:'10px',
              fontSize:'16px',
              border:'none',
              cursor:'pointer'
            }}
          >
            승인
          </button>

          {/* 거절 버튼은 추가 구현 필요 */}
          
          <button 
            style={{
              backgroundColor:'#dc3545',
              color:'#fff',
              borderRadius:'5px',
              padding:'10px',
              fontSize:'16px',
              border:'none',
              cursor:'pointer'
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

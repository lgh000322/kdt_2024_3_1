import React, { useState, useEffect } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";
import { useSelector } from "react-redux";
import { sellerAccept } from "../api/memberApi";
import { sellerAcceptSubmit } from "../api/memberApi";
import { sellerAcceptDelete } from "../api/memberApi";
import { sellerAcceptFile } from "../api/memberApi";


function AdminAcceptPage() {
  const loginSlice = useSelector((state) => state.loginSlice);
  const [formData, setFormData] = useState([]);
  const [filteredData, setFilteredData] = useState([]); // 필터링된 데이터
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedRowIndex, setSelectedRowIndex] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);

const sellerList = async (searchTermValue = "") => {
  setLoading(true);
  try {
    const res = await sellerAccept(
      loginSlice.accessToken,
      page,
      10,
      searchTermValue // 검색 조건 전달
    );
    const sortedData = res.data.sort((a, b) => {
      if (!a.name) return 1;
      if (!b.name) return -1;
      return a.name.localeCompare(b.name);
    });
    setFormData(sortedData);
    setFilteredData(sortedData); // 화면에 표시할 데이터 업데이트
  } catch (err) {
    console.error("회원 정보 조회 실패:", err);
  } finally {
    setLoading(false);
  }
};


  useEffect(() => {
    sellerList();
  }, []);
  if (loading) return <div>Loading...</div>;

  const handleRowClick = (index) => {
    setSelectedRowIndex(index);
  };

  const handleSearch = () => {
    sellerList(searchTerm); // 검색어를 기반으로 API 호출
  };

  const handleFile = async () => {
    if (selectedRowIndex === null) {
      alert("항목을 선택하세요.");
      return;
    }
    try {
      const selectedMember = filteredData[selectedRowIndex];
      const accessToken = loginSlice.accessToken;
      const response = await sellerAcceptFile(
        accessToken,
        selectedMember.authorityId
      );
      if (response.code === 200) {
        alert("성공");
        setSelectedRowIndex(null); // 선택 초기화
  
        const fileData = response.data?.[0]; // data 배열의 첫 번째 항목
        if (fileData && fileData.imageUrl) {
          window.open(fileData.imageUrl); 
        }
      } else {
        alert(`승인 실패: ${response.message}`);
      }
    } catch (error) {
      console.error("승인 실패:", error);
      alert("승인 중 오류가 발생했습니다.");
    }
  };
  
  const handleApprove = async () => {
    if (selectedRowIndex === null) {
      alert("항목을 선택하세요.");
      return;
    }

    try {
      const selectedMember = filteredData[selectedRowIndex];
      const accessToken = loginSlice.accessToken;

      // 선택된 데이터의 authorityId를 사용하여 API 호출
      const response = await sellerAcceptSubmit(
        accessToken,
        selectedMember.authorityId
      );

      if (response.code === 200) {
        alert("성공");

        setSelectedRowIndex(null); // 선택 초기화
      } else {
        alert(`승인 실패: ${response.message}`);
      }
    } catch (error) {
      console.error("승인 실패:", error);
      alert("승인 중 오류가 발생했습니다.");
    }
  };

  const handleDelete = async () => {
    if (selectedRowIndex === null) {
      alert("항목을 선택하세요.");
      return;
    }

    try {
      const selectedMember = filteredData[selectedRowIndex];
      const accessToken = loginSlice.accessToken;

      // 선택된 데이터의 authorityId를 사용하여 API 호출
      const response = await sellerAcceptDelete(
        accessToken,
        selectedMember.authorityId
      );

      if (response.code === 200) {
        alert("성공");

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
    <AdminPageLayout>
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
        <div
          style={{
            display: "flex",
            alignItems: "center",
            marginBottom: "20px",
          }}
        >
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
            placeholder="판매자명을 입력해주세요"
            value={searchTerm} // 검색어 상태와 연결
            onChange={(e) => setSearchTerm(e.target.value)} // 상태 업데이트
            style={{
              flexGrow: 1,
              padding: "10px",
              border: "1px solid #ccc",
              borderRadius: "5px",
              width: "250px",
            }}
          />
          <button
            onClick={handleSearch}
            style={{
              backgroundColor: "#007BFF",
              color: "#fff",
              border: "none",
              padding: "10px 20px",
              borderRadius: "5px",
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
        <h2
          style={{
            marginBottom: "20px",
            color: "#333",
            fontSize: "20px",
            fontWeight: "bold",
          }}
        >
          판매자 목록
        </h2>
        <div
          style={{
            maxHeight: "400px",
            overflowY: "auto",
            marginBottom: "20px",
          }}
        >
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead
              style={{
                position: "sticky",
                top: 0,
                backgroundColor: "#f7faff",
                zIndex: 1,
              }}
            >
              <tr>
                {["번호", "등록자", "등록 제목", "등록일", "첨부 파일"].map(
                  (header, index) => (
                    <th
                      key={index}
                      style={{
                        borderBottom: "2px solid #ddd",
                        padding: "10px",
                        fontWeight: "bold",
                        color: "#555",
                      }}
                    >
                      {header}
                    </th>
                  )
                )}
              </tr>
            </thead>
            <tbody>
              {filteredData.map((member, index) => (
                <tr
                  key={member.id}
                  style={{
                    borderBottom: "1px solid #ddd",
                    backgroundColor:
                      index === selectedRowIndex ? "#e6f7ff" : "transparent",
                    cursor: "pointer",
                  }}
                  onClick={() => handleRowClick(index)}
                >
                  <td style={{ padding: "10px", textAlign: "center" }}>
                    {index + 1}
                  </td>
                  <td style={{ padding: "10px", textAlign: "center" }}>
                    {member.memberName}
                  </td>
                  <td style={{ padding: "10px", textAlign: "center" }}>
                    {member.title}
                  </td>
                  <td style={{ padding: "10px", textAlign: "center" }}>
                    {member.createAt}
                  </td>
                  <td style={{ padding: "10px", textAlign: "center" }}>
                    <button
                      onClick={handleFile}
                      style={{
                        backgroundColor: "#007bff",
                        color: "#fff",
                        borderRadius: "5px",
                        padding: "10px",
                        fontSize: "16px",
                        border: "none",
                        cursor: "pointer",
                      }}
                    >
                      Download
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* 승인 및 거절 버튼 */}
        <div
          style={{
            display: "flex",
            justifyContent: "flex-end",
            gap: "10px",
          }}
        >
          <button
            onClick={handleApprove}
            style={{
              backgroundColor: "#28a745",
              color: "#fff",
              borderRadius: "5px",
              padding: "10px",
              fontSize: "16px",
              border: "none",
              cursor: "pointer",
            }}
          >
            승인
          </button>

          {/* 거절 버튼은 추가 구현 필요 */}

          <button
            onClick={handleDelete}
            style={{
              backgroundColor: "#dc3545",
              color: "#fff",
              borderRadius: "5px",
              padding: "10px",
              fontSize: "16px",
              border: "none",
              cursor: "pointer",
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

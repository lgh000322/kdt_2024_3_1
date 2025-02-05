import React, { useState, useEffect } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";
import { useSelector } from "react-redux";
import { getMembers } from "../api/memberApi";

const userRole = "manager"; // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할

function AdminSellerPage() {
  const loginSlice = useSelector((state) => state.loginSlice);
  const [formData, setFormData] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredData, setFilteredData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMembers = async () => {
      try {
        const accessToken = loginSlice.accessToken;
        console.log("Access Token:", accessToken);

        const response = await getMembers(accessToken);
        console.log("API Response:", response);

        // 역할이 seller인 데이터만 필터링
        const sellersOnly = response.data.filter((member) => member.role === '판매자');

        // 이름 기준으로 정렬
        const sortedData = sellersOnly.sort((a, b) => a.name.localeCompare(b.name));

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

  // 검색어 변경 시 필터링 로직
  useEffect(() => {
    if (searchTerm === "") {
      setFilteredData(formData); // 검색어가 없으면 전체 데이터 표시
    } else {
      const filtered = formData.filter(
        (member) =>
          member.name.toLowerCase().includes(searchTerm.toLowerCase()) || // 이름 필터링
          member.email.toLowerCase().includes(searchTerm.toLowerCase()) // 이메일 필터링
      );
      setFilteredData(filtered);
    }
  }, [searchTerm, formData]);

  if (loading) return <div>Loading...</div>;

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
        <div style={{ maxHeight: "400px", overflowY: "auto" }}>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead style={{ position: "sticky", top: 0, backgroundColor: "#f7faff", zIndex: 1 }}>
              <tr>
                {["번호", "판매자명", "이메일", "성별", "전화번호"].map((header) => (
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
                <tr key={member.id}>
                  <td style={{ padding: "10px", textAlign: "center" }}>{index + 1}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{member.name}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{member.email}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{member.gender}</td>
                  <td style={{ padding: "10px", textAlign: "center" }}>{member.tel}</td>
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
      </div>
    </AdminPageLayout>
  );
}

export default AdminSellerPage;

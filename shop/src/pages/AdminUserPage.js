import React, { useState, useEffect } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";
import { useSelector } from "react-redux";
import { getMembers } from "../api/memberApi";

const userRole = "manager";

function AdminUserPage() {
  const loginSlice = useSelector((state) => state.loginSlice);
  const [formData, setFormData] = useState([]);
  const [filteredData, setFilteredData] = useState([]); // 필터링된 데이터
  const [searchTerm, setSearchTerm] = useState(""); // 검색어 상태
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMembers = async () => {
      try {
        const res = await getMembers(loginSlice.accessToken);
        const sortedData = res.data.sort((a, b) =>
          a.name.localeCompare(b.name)
        );

        setFormData(sortedData);
        setFilteredData(sortedData);
      } catch (error) {
        console.error("회원 정보 조회 실패:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchMembers();
  }, []);

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
      <div>
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
            회원 검색
          </h2>
          <div style={{ marginBottom: "15px" }}>
            <label
              htmlFor="userId"
              style={{
                marginRight: "10px",
                fontWeight: "bold",
                color: "#555",
              }}
            >
              검색:
            </label>
            <input
              type="text"
              id="userId"
              placeholder="이름 또는 이메일 입력"
              value={searchTerm} // 검색어 상태와 연결
              onChange={(e) => setSearchTerm(e.target.value)} // 상태 업데이트
              style={{
                padding: "10px",
                border: "1px solid #ccc",
                borderRadius: "5px",
                width: "250px",
                marginRight: "10px",
              }}
            />
          </div>
          <button
            onClick={() => setSearchTerm("")} // 검색 초기화 버튼
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
            초기화
          </button>
        </div>

        {/* 테이블 섹션 */}
        <div
          style={{
            padding: "20px",
            border: "1px solid #ddd",
            margin: "20px auto",
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
            회원 목록
          </h2>
          <div style={{ maxHeight: "400px", overflowY: "auto" }}>
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
                  {["번호", "이름", "이메일", "성별", "전화번호", "역할"].map(
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
                  <tr key={member.id}>
                    {/* 번호는 index + 1로 설정 */}
                    <td style={{ padding: "10px", textAlign: "center" }}>
                      {index + 1}
                    </td>
                    <td style={{ padding: "10px", textAlign: "center" }}>
                      {member.name}
                    </td>
                    <td style={{ padding: "10px", textAlign: "center" }}>
                      {member.email}
                    </td>
                    <td style={{ padding: "10px", textAlign: "center" }}>
                      {member.gender}
                    </td>
                    <td style={{ padding: "10px", textAlign: "center" }}>
                      {member.tel}
                    </td>
                    <td style={{ padding: "10px", textAlign: "center" }}>
                      {member.role}
                    </td>
                  </tr>
                ))}
                {filteredData.length === 0 && (
                  <tr>
                    <td
                      colSpan="6"
                      style={{ textAlign: "center", padding: "20px" }}
                    >
                      검색 결과가 없습니다.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminUserPage;

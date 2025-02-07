import React, { useState, useEffect } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";
import { useSelector } from "react-redux";
import { getMembers } from "../api/memberApi";
import { searchMemberData } from "../api/memberApi";


function AdminUserPage() {
  const loginSlice = useSelector((state) => state.loginSlice);
  const [formData, setFormData] = useState([]);
  const [searchTerm, setSearchTerm] = useState(""); // 검색어 상태
  const [loading, setLoading] = useState(true);
  const [email, setEmail] = useState(""); // 이메일 검색 상태
  const [role, setRole] = useState(""); // 역할(Role) 검색 상태
  const [error, setError] = useState(null); // 에러 상태

  const fetchMembers = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await getMembers(loginSlice.accessToken, null, null);
      const sortedData = res.data.sort((a, b) => a.name.localeCompare(b.name));
      setFormData(sortedData);
    } catch (err) {
      console.error("회원 정보 조회 실패:", err);
      setError("회원 정보를 불러오는 데 실패했습니다.");
    } finally {
      setLoading(false);
    }
  };

  const searchMember = async () => {
    setLoading(true);
    setError(null);
  
    try {
      const res = await searchMemberData(loginSlice.accessToken, {
        page: 0,
        size: 10,
        email,
      });
  
      const sortedData = res.data.sort((a, b) => a.name.localeCompare(b.name));
      setFormData(sortedData);
    } catch (err) {
      console.error("회원 정보 조회 실패:", err);
      setError("회원 정보를 불러오는 데 실패했습니다.");
    } finally {
      setLoading(false);
    }
  };
  


  useEffect(() => {
    fetchMembers();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div style={{ color: "red" }}>{error}</div>;

  return (
    <AdminPageLayout>
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
              htmlFor="email"
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
              id="email"
              placeholder="이름 또는 이메일 입력"
              value={email} // 검색어 상태와 연결
              onChange={(e) => setEmail(e.target.value)} // 상태 업데이트
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
            onClick={searchMember}
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
                {formData.map((member, index) => (
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
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminUserPage;

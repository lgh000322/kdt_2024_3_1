import React, { useState, useEffect } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useSelector } from "react-redux";
import { getShippingAddresses, updateShippingAddress } from "../api/shippingAddressApi";

function ShippingAddressPage() {
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  const [shippingAddresses, setShippingAddresses] = useState([]); // 기본값 배열 설정
  const [editingAddress, setEditingAddress] = useState([]); // 수정 중인 주소 저장

  // 배송지 목록 불러오기
  useEffect(() => {
    console.log("📢 배송지 목록 불러오기 시작");
  
    const fetchAddresses = async () => {
      try {
        console.log("🚀 배송지 목록 요청 시작");
        const response = await getShippingAddresses(accessToken);
        
        console.log("📦 API 응답 전체:", response);
    
        if (response && response.data && Array.isArray(response.data)) {
          console.log("📌 최종 배송지 목록:", response.data);
          setShippingAddresses([]);
        } else {
          console.error("❌ API 응답이 예상과 다름:", response);
          setShippingAddresses([]);
        }
      } catch (error) {
        console.error("🚨 배송지 목록 불러오기 실패:", error);
        setShippingAddresses([]);
      }
    };    
  
    fetchAddresses();
  }, [accessToken]);  

  // 수정 모드 활성화
  const handleEdit = (address) => {
    setEditingAddress({ ...address });
  };

  // 입력값 변경 핸들러
  const handleInputChange = (field, value) => {
    if (!editingAddress) return;
    setEditingAddress((prev) => ({ ...prev, [field]: value }));
  };

  // 배송지 수정 요청
  const handleUpdateAddress = async () => {
    if (!editingAddress) return;

    try {
      await updateShippingAddress(editingAddress.destinationId, editingAddress, accessToken);

      // UI 업데이트
      setShippingAddresses((prev) =>
        prev.map((addr) => (addr.destinationId === editingAddress.destinationId ? editingAddress : addr))
      );

      setEditingAddress(null); // 수정 모드 종료
      alert("배송지가 성공적으로 수정되었습니다.");
    } catch (error) {
      console.error("배송지 수정 실패:", error);
      alert(`배송지 수정 실패: ${error.message}`);
    }
  };

  return (
    <BasicLayout>
      <div style={{ padding: "40px", maxWidth: "800px", margin: "0 auto" }}>
        <h1 style={{ fontSize: "28px", fontWeight: "600", marginBottom: "30px" }}>배송지 관리</h1>

        {shippingAddresses.length > 0 ? (
          shippingAddresses.map((address, index) => (
            <div key={address.destinationId || `temp-${index}`} style={{ borderRadius: "8px", padding: "20px", borderBottom: "1px solid #ddd" }}>
              {editingAddress?.destinationId === address.destinationId ? (
                // 수정 모드 UI
                editingAddress && (
                  <div>
                    <input
                      type="text"
                      value={editingAddress.destinationName || ""}
                      onChange={(e) => handleInputChange("destinationName", e.target.value)}
                      style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                    />
                    <input
                      type="text"
                      value={editingAddress.receiverName || ""}
                      onChange={(e) => handleInputChange("receiverName", e.target.value)}
                      style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                    />
                    <input
                      type="text"
                      value={editingAddress.tel || ""}
                      onChange={(e) => handleInputChange("tel", e.target.value)}
                      style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                    />
                    <input
                      type="text"
                      value={editingAddress.address || ""}
                      onChange={(e) => handleInputChange("address", e.target.value)}
                      style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                    />
                    <input
                      type="number"
                      value={editingAddress.zipCode || ""}
                      onChange={(e) => handleInputChange("zipCode", e.target.value)}
                      style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
                    />
                    <button onClick={handleUpdateAddress} style={{ padding: "10px 20px", backgroundColor: "#4CAF50", color: "white", borderRadius: "6px", cursor: "pointer", marginRight: "10px" }}>
                      저장
                    </button>
                    <button onClick={() => setEditingAddress(null)} style={{ padding: "10px 20px", backgroundColor: "#f44336", color: "white", borderRadius: "6px", cursor: "pointer" }}>
                      취소
                    </button>
                  </div>
                )
              ) : (
                // 기본 보기 UI
                <div>
                  <h3>{address.destinationName}</h3>
                  <p>받는 사람 : {address.receiverName}</p>
                  <p>전화번호 : {address.tel}</p>
                  <p>주소 : {address.address}</p>
                  <p>우편번호 : {address.zipCode}</p>
                  <button onClick={() => handleEdit(address)} style={{ padding: "8px 16px", backgroundColor: "#008CBA", color: "white", borderRadius: "6px", cursor: "pointer" }}>
                    수정
                  </button>
                </div>
              )}
            </div>
          ))
        ) : (
          <p>배송지가 없습니다.</p>
        )}
      </div>
    </BasicLayout>
  );
}

export default ShippingAddressPage;

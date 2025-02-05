import React, { useState } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useSelector } from "react-redux";
import { addShippingAddress } from "../api/shippingAddressApi";

function ShippingAddressPage() {
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  const [shippingAddresses, setShippingAddresses] = useState([]);
  const [newAddress, setNewAddress] = useState({
    destinationName: "",
    receiverName: "",
    tel: "",
    address: "",
    zipCode: "",
    isSelectedDestination: false,
  });
  const [isAdding, setIsAdding] = useState(false);

  // 새 주소 입력 핸들러
  const handleInputChange = (field, value) => {
    setNewAddress((prev) => ({ ...prev, [field]: value }));
  };

  // 기본 배송지 여부 토글 핸들러
  const handleCheckboxChange = () => {
    setNewAddress((prev) => ({ ...prev, isSelectedDestination: !prev.isSelectedDestination }));
  };

  // 새 주소 저장 핸들러
  const handleSaveNewAddress = async () => {
    const { destinationName, receiverName, tel, address, zipCode } = newAddress;

    if (!destinationName || !receiverName || !tel || !address || !zipCode) {
      alert("모든 필드를 입력해주세요.");
      return;
    }

    try {
      // 서버에 새 주소 추가 요청
      await addShippingAddress(newAddress, accessToken);

      // 성공 시 UI 업데이트
      setShippingAddresses((prev) => [...prev, { ...newAddress, id: prev.length + 1 }]);
      alert("새 배송지가 성공적으로 추가되었습니다.");
      setIsAdding(false); // 입력 폼 닫기
      setNewAddress({
        destinationName: "",
        receiverName: "",
        tel: "",
        address: "",
        zipCode: "",
        isSelectedDestination: false,
      }); // 폼 초기화
    } catch (error) {
      console.error("배송지 추가 실패:", error);
      alert(`배송지 추가 실패: ${error.message}`);
    }
  };

  return (
    <BasicLayout>
      <div style={{ padding: "40px", maxWidth: "800px", margin: "0 auto" }}>
        <h1 style={{ fontSize: "28px", fontWeight: "600", marginBottom: "30px" }}>배송지 관리</h1>

        {!isAdding ? (
          <button
            onClick={() => setIsAdding(true)}
            style={{
              padding: "12px 24px",
              backgroundColor: "#4CAF50",
              color: "white",
              borderRadius: "6px",
              cursor: "pointer",
              marginBottom: "20px",
            }}
          >
            + 새 배송지 추가
          </button>
        ) : (
          <div style={{ marginBottom: "20px", borderRadius: "8px", padding: "20px", border: "1px solid #ddd" }}>
            <h3>새 배송지 추가</h3>
            <input
              type="text"
              placeholder="배송지 이름"
              value={newAddress.destinationName}
              onChange={(e) => handleInputChange("destinationName", e.target.value)}
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            />
            <input
              type="text"
              placeholder="받는 사람"
              value={newAddress.receiverName}
              onChange={(e) => handleInputChange("receiverName", e.target.value)}
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            />
            <input
              type="text"
              placeholder="전화번호"
              value={newAddress.tel}
              onChange={(e) => handleInputChange("tel", e.target.value)}
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            />
            <input
              type="text"
              placeholder="주소"
              value={newAddress.address}
              onChange={(e) => handleInputChange("address", e.target.value)}
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            />
            <input
              type="number"
              placeholder="우편번호"
              value={newAddress.zipCode}
              onChange={(e) => handleInputChange("zipCode", e.target.value)}
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            />
            <div style={{ marginBottom: "10px" }}>
              <label>
                <input
                  type="checkbox"
                  checked={newAddress.isSelectedDestination}
                  onChange={handleCheckboxChange}
                  style={{ marginRight: "5px" }}
                />
                기본 배송지로 설정
              </label>
            </div>
            <button
              onClick={handleSaveNewAddress}
              style={{
                padding: "10px 20px",
                backgroundColor: "#4CAF50",
                color: "white",
                borderRadius: "6px",
                cursor: "pointer",
                marginRight: "10px",
              }}
            >
              저장
            </button>
            <button
              onClick={() => setIsAdding(false)}
              style={{
                padding: "10px 20px",
                backgroundColor: "#f44336",
                color: "white",
                borderRadius: "6px",
                cursor: "pointer",
              }}
            >
              취소
            </button>
          </div>
        )}

        {shippingAddresses.map((address) => (
          <div key={address.id} style={{ borderRadius: "8px", padding: "20px", borderBottom: "1px solid #ddd" }}>
            <h3>{address.destinationName}</h3>
            <p>받는 사람 : {address.receiverName}</p>
            <p>전화번호 : {address.tel}</p>
            <p>주소 : {address.address}</p>
            <p>우편번호 : {address.zipCode}</p>
          </div>
        ))}
      </div>
    </BasicLayout>
  );
}

export default ShippingAddressPage;

import React, { useState, useEffect } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useSelector } from "react-redux";
import { getShippingAddresses, updateShippingAddress, addShippingAddress } from "../api/shippingAddressApi";

function ShippingAddressPage() {
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  const [shippingAddresses, setShippingAddresses] = useState([]);
  const [editingAddress, setEditingAddress] = useState(null);
  const [newAddress, setNewAddress] = useState({
    destinationName: "",
    receiverName: "",
    address: "",
    tel: "",
    zipCode: "",
    isSelectedDestination: false,
  });
  const [showNewAddressForm, setShowNewAddressForm] = useState(false);

  useEffect(() => {
    const fetchAddresses = async () => {
      try {
        const response = await getShippingAddresses(accessToken);
        setShippingAddresses(Array.isArray(response) ? response : []);
      } catch (error) {
        console.error("🚨 배송지 목록 불러오기 실패:", error);
        setShippingAddresses([]);
      }
    };

    fetchAddresses();
  }, [accessToken]);

  const handleInputChange = (field, value, isNew = false) => {
    if (isNew) {
      setNewAddress((prev) => ({ ...prev, [field]: value }));
    } else if (editingAddress) {
      setEditingAddress((prev) => ({ ...prev, [field]: value }));
    }
  };

  const handleAddAddress = async () => {
    try {
      const addedAddress = await addShippingAddress(newAddress, accessToken);
      setShippingAddresses((prev) => [...prev, addedAddress]);
      setNewAddress({ destinationName: "", receiverName: "", address: "", tel: "", zipCode: "", isSelectedDestination: false });
      alert("배송지가 성공적으로 추가되었습니다.");
    } catch (error) {
      console.error("배송지 추가 실패:", error);
      alert(`배송지 추가 실패: ${error.message}`);
    }
  };

  const handleEdit = (address) => {
    console.log("🛠 수정할 데이터:", address); // 추가
    setEditingAddress({ ...address });
  };

  const handleUpdateAddress = async () => {
    if (!editingAddress) return;

    console.log("🛠 수정 요청 데이터:", editingAddress);
    console.log("🛠 destinationId:", editingAddress.destinationId); // 추가

    try {
      await updateShippingAddress(editingAddress.destinationId, editingAddress, accessToken);
      
      alert("배송지가 성공적으로 수정되었습니다.");
    } catch (error) {
      console.error("배송지 수정 실패:", error);
      alert(`배송지 수정 실패: ${error.message}`);
    }
  };

  return (
    <BasicLayout>
      <div style={styles.container}>
        <h1 style={styles.title}>배송지 관리</h1>
        
        <div style={styles.buttonWrapper}>
          <button style={styles.toggleButton} onClick={() => setShowNewAddressForm((prev) => !prev)}>
            {showNewAddressForm ? "새 배송지 추가 숨기기" : "새 배송지 추가"}
          </button>
        </div>

        {showNewAddressForm && (
          <div style={styles.card}>
            <h3>새 배송지 추가</h3>
            <input type="text" value={newAddress.destinationName} onChange={(e) => handleInputChange("destinationName", e.target.value, true)} style={styles.input} placeholder="배송지 이름" />
            <input type="text" value={newAddress.receiverName} onChange={(e) => handleInputChange("receiverName", e.target.value, true)} style={styles.input} placeholder="받는 사람" />
            <input type="text" value={newAddress.address} onChange={(e) => handleInputChange("address", e.target.value, true)} style={styles.input} placeholder="주소" />
            <input type="text" value={newAddress.tel} onChange={(e) => handleInputChange("tel", e.target.value, true)} style={styles.input} placeholder="연락처" />
            <input type="number" value={newAddress.zipCode} onChange={(e) => handleInputChange("zipCode", e.target.value, true)} style={styles.input} placeholder="우편번호" />
            <label>
              <input type="checkbox" checked={newAddress.isSelectedDestination} onChange={(e) => handleInputChange("isSelectedDestination", e.target.checked, true)} /> 기본 배송지로 설정
            </label>
            <button style={styles.saveButton} onClick={handleAddAddress}>추가</button>
          </div>
        )}

        {shippingAddresses.map((address, index) => (
          <div key={index} style={styles.card}>
            {editingAddress && editingAddress.destinationId === address.destinationId ? (
              <div style={styles.form}>
                <input type="text" value={editingAddress.destinationName} onChange={(e) => handleInputChange("destinationName", e.target.value)} style={styles.input} placeholder="배송지 이름" />
                <input type="text" value={editingAddress.receiverName} onChange={(e) => handleInputChange("receiverName", e.target.value)} style={styles.input} placeholder="받는 사람" />
                <input type="text" value={editingAddress.address} onChange={(e) => handleInputChange("address", e.target.value)} style={styles.input} placeholder="주소" />
                <input type="text" value={editingAddress.tel} onChange={(e) => handleInputChange("tel", e.target.value)} style={styles.input} placeholder="연락처" />
                <input type="text" value={editingAddress.zipCode} onChange={(e) => handleInputChange("zipCode", e.target.value)} style={styles.input} placeholder="우편번호" />
                <div style={styles.buttonContainer}>
                  <button style={styles.saveButton} onClick={handleUpdateAddress}>저장</button>
                  <button style={styles.cancelButton} onClick={() => setEditingAddress(null)}>취소</button>
                </div>
              </div>
            ) : (
              <div>
                <h3 style={styles.addressTitle}>{address.destinationName}</h3>
                <p><strong>받는 사람:</strong> {address.receiverName}</p>
                <p><strong>주소:</strong> {address.address}</p>
                <p><strong>연락처:</strong> {address.tel}</p>
                <p><strong>우편번호:</strong> {address.zipCode}</p>
                <button style={styles.editButton} onClick={() => handleEdit(address)}>수정</button>
                <button style={styles.deleteButton} onClick={() => handleEdit(address)}>삭제</button>
              </div>
            )}
          </div>
        ))}
      </div>
    </BasicLayout>
  );
}


const styles = {
  container: {
    padding: "40px",
    maxWidth: "800px",
    margin: "0 auto",
    fontFamily: "'Noto Sans KR', sans-serif",
  },
  title: {
    fontSize: "28px",
    fontWeight: "600",
    marginBottom: "30px",
    textAlign: "center",
  },
  card: {
    border: "1px solid #ddd",
    borderRadius: "10px",
    padding: "15px",
    marginBottom: "15px",
    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
    backgroundColor: "#fff",
  },
  addressTitle: {
    fontSize: "18px",
    fontWeight: "bold",
    marginBottom: "10px",
  },
  form: {
    display: "flex",
    flexDirection: "column",
    gap: "10px",
  },
  input: {
    width: "100%",
    padding: "8px",
    fontSize: "14px",
    borderRadius: "5px",
    border: "1px solid #ccc",
  },
  buttonContainer: {
    display: "flex",
    justifyContent: "space-between",
    marginTop: "10px",
  },
  saveButton: {
    backgroundColor: "#007bff",
    color: "#fff",
    border: "none",
    padding: "10px 15px",
    borderRadius: "5px",
    cursor: "pointer",
    transition: "background 0.3s",
  },
  cancelButton: {
    backgroundColor: "#dc3545",
    color: "#fff",
    border: "none",
    padding: "10px 15px",
    borderRadius: "5px",
    cursor: "pointer",
    transition: "background 0.3s",
  },
  editButton: {
    backgroundColor: "#28a745",
    color: "#fff",
    border: "none",
    padding: "10px 15px",
    borderRadius: "5px",
    cursor: "pointer",
    transition: "background 0.3s",
  },
  deleteButton: {
    backgroundColor: "red",
    color: "#fff",
    border: "none",
    padding: "10px 15px",
    borderRadius: "5px",
    cursor: "pointer",
    transition: "background 0.3s",
  },
  noAddress: {
    textAlign: "center",
    fontSize: "16px",
    color: "#666",
  },
  buttonWrapper: {
    marginBottom: "15px",
  },
  toggleButton: {
    backgroundColor: "#007bff",
    color: "#fff",
    border: "none",
    padding: "10px 15px",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "16px",
    fontWeight: "bold",
  },
};

export default ShippingAddressPage;

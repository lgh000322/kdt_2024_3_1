import React, { useState } from "react";
import BasicLayout from "../layouts/BasicLayout";

function ShippingAddressPage() {
  const userRole = "consumer";

  const [shippingAddresses, setShippingAddresses] = useState([
    {
      id: 1,
      title: "기본 배송지",
      name: "",
      address: "",
      phone: "",
      isEditable: false,
      isDefault: true,
    },
    {
      id: 2,
      title: "배송지 2",
      name: "",
      address: "",
      phone: "",
      isEditable: false,
      isDefault: false,
    },
  ]);

  const [selectedAddressId, setSelectedAddressId] = useState(null);

  const handleAddAddress = () => {
    const newAddress = {
      id: shippingAddresses.length + 1,
      title: "",
      name: "",
      address: "",
      phone: "",
      isEditable: false,
      isDefault: false,
    };
    setShippingAddresses([...shippingAddresses, newAddress]);
  };

  const handleSelectAddress = (id) => {
    setSelectedAddressId(id);
  };

  const handleEditToggle = (id) => {
    setShippingAddresses((prev) =>
      prev.map((address) =>
        address.id === id
          ? { ...address, isEditable: !address.isEditable }
          : address
      )
    );
  };

  const handleInputChange = (id, field, value) => {
    setShippingAddresses((prev) =>
      prev.map((address) =>
        address.id === id ? { ...address, [field]: value } : address
      )
    );
  };

  const handleSetDefaultAddress = (id) => {
    setShippingAddresses((prev) =>
      prev.map((address) =>
        address.id === id
          ? { ...address, isDefault: true }
          : { ...address, isDefault: false }
      )
    );
  };

  const styles = {
    container: {
      padding: "40px",
      maxWidth: "800px",
      margin: "0 auto",
    },
    header: {
      fontSize: "28px",
      fontWeight: "600",
      color: "#333",
      marginBottom: "30px",
      borderBottom: "2px solid #eee",
      paddingBottom: "15px",
    },
    addButton: {
      padding: "12px 24px",
      backgroundColor: "#4CAF50",
      color: "white",
      border: "none",
      borderRadius: "6px",
      cursor: "pointer",
      fontSize: "15px",
      fontWeight: "500",
      transition: "background-color 0.3s",
      marginBottom: "30px",
      boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
    },
    addressCard: {
      border: "1px solid #e0e0e0",
      borderRadius: "8px",
      padding: "20px",
      marginBottom: "20px",
      backgroundColor: "white",
      boxShadow: "0 2px 6px rgba(0,0,0,0.05)",
      transition: "all 0.3s ease",
    },
    selectedCard: {
      border: "2px solid #4CAF50",
      transform: "translateY(-2px)",
    },
    defaultBadge: {
      backgroundColor: "#4CAF50",
      color: "white",
      padding: "4px 8px",
      borderRadius: "4px",
      fontSize: "12px",
      marginBottom: "10px",
      display: "inline-block",
    },
    inputGroup: {
      marginBottom: "15px",
    },
    label: {
      display: "block",
      marginBottom: "8px",
      color: "#666",
      fontSize: "14px",
      fontWeight: "500",
    },
    input: {
      width: "100%",
      padding: "10px",
      border: "1px solid #ddd",
      borderRadius: "4px",
      fontSize: "14px",
      transition: "border-color 0.3s",
    },
    buttonGroup: {
      marginTop: "15px",
      display: "flex",
      gap: "10px",
    },
    editButton: {
      padding: "8px 16px",
      backgroundColor: "#ff9800",
      color: "white",
      border: "none",
      borderRadius: "4px",
      cursor: "pointer",
      fontSize: "14px",
      transition: "background-color 0.3s",
    },
    defaultButton: {
      padding: "8px 16px",
      backgroundColor: "#2196F3",
      color: "white",
      border: "none",
      borderRadius: "4px",
      cursor: "pointer",
      fontSize: "14px",
      transition: "background-color 0.3s",
    },
    confirmButton: {
      padding: "8px 16px",
      backgroundColor: "#4CAF50",
      color: "white",
      border: "none",
      borderRadius: "4px",
      cursor: "pointer",
      fontSize: "14px",
      transition: "background-color 0.3s",
    },
  };

  return (
    <BasicLayout>
      <div style={styles.container}>
        <h1 style={styles.header}>배송지 관리</h1>
        <button
          onClick={handleAddAddress}
          style={styles.addButton}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#45a049")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#4CAF50")}
        >
          + 새 배송지 추가
        </button>

        {shippingAddresses.map((address) => (
          <div
            key={address.id}
            onClick={() => handleSelectAddress(address.id)}
            style={{
              ...styles.addressCard,
              ...(selectedAddressId === address.id ? styles.selectedCard : {}),
            }}
          >
            {address.isDefault && (
              <div style={styles.defaultBadge}>기본 배송지</div>
            )}

            <div style={styles.inputGroup}>
              <label style={styles.label}>배송지 제목</label>
              <input
                type="text"
                value={address.title}
                onChange={(e) =>
                  handleInputChange(address.id, "title", e.target.value)
                }
                placeholder="배송지 제목(예: 집, 회사)"
                style={styles.input}
                readOnly={!address.isEditable}
              />
            </div>

            <div style={styles.inputGroup}>
              <label style={styles.label}>받는 사람</label>
              <input
                type="text"
                value={address.name}
                onChange={(e) =>
                  handleInputChange(address.id, "name", e.target.value)
                }
                placeholder="받는 사람 이름"
                style={styles.input}
                readOnly={!address.isEditable}
              />
            </div>

            <div style={styles.inputGroup}>
              <label style={styles.label}>주소</label>
              <input
                type="text"
                value={address.address}
                onChange={(e) =>
                  handleInputChange(address.id, "address", e.target.value)
                }
                placeholder="상세 주소"
                style={styles.input}
                readOnly={!address.isEditable}
              />
            </div>

            <div style={styles.inputGroup}>
              <label style={styles.label}>연락처</label>
              <input
                type="text"
                value={address.phone}
                onChange={(e) =>
                  handleInputChange(address.id, "phone", e.target.value)
                }
                placeholder="휴대폰 번호"
                style={styles.input}
                readOnly={!address.isEditable}
              />
            </div>

            <div style={styles.buttonGroup}>
              {!address.isEditable ? (
                <>
                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      handleEditToggle(address.id);
                    }}
                    style={styles.editButton}
                  >
                    수정
                  </button>
                  {!address.isDefault && (
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        handleSetDefaultAddress(address.id);
                      }}
                      style={styles.defaultButton}
                    >
                      기본 배송지로 설정
                    </button>
                  )}
                </>
              ) : (
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleEditToggle(address.id);
                  }}
                  style={styles.confirmButton}
                >
                  저장
                </button>
              )}
            </div>
          </div>
        ))}
      </div>
    </BasicLayout>
  );
}

export default ShippingAddressPage;

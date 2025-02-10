import React, { useState, useEffect } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useSelector } from "react-redux";
import {
  getShippingAddresses,
  updateShippingAddress,
  addShippingAddress,
  deleteShippingAddress,
} from "../api/shippingAddressApi";
import Daumpostcode from "react-daum-postcode";

const initState = {
  destinationName: "",
  receiverName: "",
  address: "",
  tel: "",
  zipCode: "",
  isSelectedDestination: false,
};
function ShippingAddressPage() {
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  const [shippingAddresses, setShippingAddresses] = useState([]);
  const [editingAddress, setEditingAddress] = useState(null);
  const [newAddress, setNewAddress] = useState(initState);
  const [showNewAddressForm, setShowNewAddressForm] = useState(false);
  const [modalState, setModalState] = useState(false); // 모달 기본 상태 false

  const handleAddNewField = (newField) => {
    setNewAddress((prev) => ({ ...prev, ...newField }));
  };

  const handleModifyField = (newField) => {
    setEditingAddress((prev) => ({ ...prev, ...newField }));
  };

  const postCodeStyle = {
    width: "400px",
    height: "400px",
    display: modalState ? "block" : "none", // modalState가 true일 때만 보이게
    position: "absolute",
    top: "60%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    zIndex: "9999",
    backgroundColor: "#fff",
    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
    borderRadius: "10px",
  };

  const onCompletePost = (data) => {
    setModalState(false); // 모달 닫기
    if (!editingAddress) {
      handleAddNewField({ address: data.address });
      handleAddNewField({ zipCode: data.zonecode });
    } else {
      handleModifyField({ address: data.address });
      handleModifyField({ zipCode: data.zonecode });
    }
  };

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

<<<<<<< HEAD
=======
  const handleSetDefault = async (address) => {
    try {
      await updateShippingAddress(
        address.destinationId,
        { ...address, isSelectedDestination: true },
        accessToken
      );

      setShippingAddresses((prev) =>
        prev.map((item) => ({
          ...item,
          isSelectedDestination: item.destinationId === address.destinationId,
        }))
      );

      alert("기본 배송지가 설정되었습니다.");
    } catch (error) {
      console.error("기본 배송지 설정 실패:", error);
      alert(`기본 배송지 설정 실패: ${error.message}`);
    }
  };

>>>>>>> f96a789e89b450e58a3d6358c3f87f15fa1bc178
  const handleAddAddress = async () => {
    try {
      const addedAddress = await addShippingAddress(newAddress, accessToken);
      setShippingAddresses((prev) => [...prev, addedAddress]);
      setNewAddress({
        destinationName: "",
        receiverName: "",
        address: "",
        tel: "",
        zipCode: "",
        isSelectedDestination: false,
      });
      alert("배송지가 성공적으로 추가되었습니다.");
    } catch (error) {
      console.error("배송지 추가 실패:", error);
      alert(`배송지 추가 실패: ${error.message}`);
    }
    window.location.reload();
  };

  const handleUpdateAddress = async () => {
    if (!editingAddress) return;

    try {
      await updateShippingAddress(
        editingAddress.destinationId,
        editingAddress,
        accessToken
      );

      alert("배송지가 성공적으로 수정되었습니다.");
    } catch (error) {
      console.error("배송지 수정 실패:", error);
      alert(`배송지 수정 실패: ${error.message}`);
    }
    window.location.reload();
  };

  const handleDeleteAddress = async (address) => {
    if (!address) return;

    if (!window.confirm("정말 이 배송지를 삭제하시겠습니까?")) return;

    try {
      await deleteShippingAddress(address.destinationId, accessToken);

      setShippingAddresses((prev) =>
        prev.filter((item) => item.destinationId !== address.destinationId)
      );

      alert("배송지가 성공적으로 삭제되었습니다.");
    } catch (error) {
      console.error("배송지 삭제 실패:", error);
      alert(`배송지 삭제 실패: ${error.message}`);
    }
  };

  const handleEdit = (address) => {
    setEditingAddress({ ...address });
  };

  return (
    <BasicLayout>
      <div style={styles.container}>
        <h1 style={styles.title}>배송지 관리</h1>

        <div style={styles.buttonWrapper}>
          <button
            style={styles.toggleButton}
            onClick={() => setShowNewAddressForm((prev) => !prev)}
          >
            {showNewAddressForm ? "새 배송지 추가 숨기기" : "새 배송지 추가"}
          </button>
        </div>

        {showNewAddressForm && (
          <div style={styles.card}>
            <h3>새 배송지 추가</h3>
            <input
              type="text"
              value={newAddress.destinationName}
              onChange={(e) =>
                handleInputChange("destinationName", e.target.value, true)
              }
              style={styles.addinput}
              placeholder="배송지 이름"
            />
            <input
              type="text"
              value={newAddress.receiverName}
              onChange={(e) =>
                handleInputChange("receiverName", e.target.value, true)
              }
              style={styles.addinput}
              placeholder="받는 사람"
            />
            <input
              type="text"
              value={newAddress.tel}
              onChange={(e) => handleInputChange("tel", e.target.value, true)}
              style={styles.addinput}
              placeholder="연락처"
            />
            <button
              style={styles.addAddressButton}
              onClick={() => setModalState(true)} // 주소 찾기 버튼 클릭 시 모달 띄우기
            >
              주소 찾기
            </button>
            <Daumpostcode
              style={postCodeStyle}
              onComplete={onCompletePost}
            ></Daumpostcode>
            <input
              type="text"
              readOnly
              value={newAddress.address}
              onChange={(e) =>
                handleInputChange("address", e.target.value, true)
              }
              style={styles.addinput}
              placeholder="주소"
            />
            <input
              type="number"
              readOnly
              value={newAddress.zipCode}
              onChange={(e) =>
                handleInputChange("zipCode", e.target.value, true)
              }
              style={styles.addinput}
              placeholder="우편번호"
            />
            <label>
              <input
                type="checkbox"
                checked={newAddress.isSelectedDestination}
                onChange={(e) =>
                  handleInputChange(
                    "isSelectedDestination",
                    e.target.checked,
                    true
                  )
                }
              />{" "}
              기본 배송지로 설정
            </label>
            <button style={styles.addAddressButton} onClick={handleAddAddress}>
              추가
            </button>
          </div>
        )}

        {shippingAddresses.map((address, index) => (
          <div key={index} style={styles.card}>
            {address.isSelectedDefault && <span style={styles.defaultBadge}>기본 배송지</span>}
            {editingAddress && editingAddress.destinationId === address.destinationId ? (
              <div style={styles.form}>
                <div style={styles.formRow}>
                  <p style={styles.label}>배송지 이름:</p>
                  <input
                    type="text"
                    value={editingAddress.destinationName}
                    onChange={(e) =>
                      handleInputChange("destinationName", e.target.value)
                    }
                    style={styles.editinput}
                    placeholder="배송지 이름"
                  />
                </div>
                <div style={styles.formRow}>
                  <p style={styles.label}>받는 사람:</p>
                  <input
                    type="text"
                    value={editingAddress.receiverName}
                    onChange={(e) =>
                      handleInputChange("receiverName", e.target.value)
                    }
                    style={styles.editinput}
                    placeholder="받는 사람"
                  />
                </div>
                <div style={styles.formRow}>
                  <p style={styles.label}>연락처:</p>
                  <input
                    type="text"
                    value={editingAddress.tel}
                    onChange={(e) => handleInputChange("tel", e.target.value)}
                    style={styles.editinput}
                    placeholder="연락처"
                  />
                </div>
                <button
                  style={styles.addAddressButton}
                  onClick={() => setModalState(true)} // 주소 찾기 버튼 클릭 시 모달 띄우기
                >
                  주소 찾기
                </button>
                <Daumpostcode
                  style={postCodeStyle}
                  onComplete={onCompletePost}
                ></Daumpostcode>
                <div style={styles.formRow}>
                  <p style={styles.label}>주소:</p>
                  <input
                    type="text"
                    readOnly
                    value={editingAddress.address}
                    onChange={(e) =>
                      handleInputChange("address", e.target.value)
                    }
                    style={styles.editinput}
                    placeholder="주소"
                  />
                </div>
                <div style={styles.formRow}>
                  <p style={styles.label}>우편번호:</p>
                  <input
                    type="text"
                    readOnly
                    value={editingAddress.zipCode}
                    onChange={(e) =>
                      handleInputChange("zipCode", e.target.value)
                    }
                    style={styles.editinput}
                    placeholder="우편번호"
                  />
                </div>
                <label>
                  <input
                    type="checkbox"
                    checked={editingAddress.isSelectedDestination}
                    onChange={(e) =>
                      handleInputChange(
                        "isSelectedDestination",
                        e.target.checked
                      )
                    }
                  />{" "}
                  기본 배송지로 설정
                </label>
                <div style={styles.formRow}>
                    <p style={styles.label}>우편번호:</p>
                    <input type="number" value={editingAddress.zipCode} onChange={(e) => handleInputChange("zipCode", e.target.value)} style={styles.editinput} placeholder="우편번호" />
                </div>
                <div style={styles.buttonContainer}>
                  <label>
                    <input
                      type="checkbox"
                      checked={editingAddress.isSelectedDestination}
                      onChange={(e) =>
                        setEditingAddress((prev) => ({
                          ...prev,
                          isSelectedDestination: e.target.checked,
                        }))
                      }
                    />
                    기본 배송지로 설정
                  </label>
                  <button style={styles.saveButton} onClick={handleUpdateAddress}>저장</button>
                  <button style={styles.cancelButton} onClick={() => setEditingAddress(null)}>취소</button>
                </div>
              </div>
            ) : (
              <div>
                <h3>{address.destinationName}</h3>
                <p>받는 사람: {address.receiverName}</p>
                <p>주소: {address.address}</p>
                <p>연락처: {address.tel}</p>
                <p>우편번호: {address.zipCode}</p>
                {address.isSelectedDestination && <p>기본 배송지로 설정됨</p>}
                <button
                  onClick={() => handleEdit(address)}
                  style={styles.editButton}
                >
                  수정
                </button>
                <button
                  onClick={() => handleDeleteAddress(address)}
                  style={styles.deleteButton}
                >
                  삭제
                </button>
                {!address.isSelectedDestination && (
                  <button
                    onClick={() => handleSetDefault(address)}
                    style={styles.setDefaultButton}
                  >
                    기본 배송지로 설정
                  </button>
                )}
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
    width: "100%",
    margin: "0 auto",
    padding: "20px",
  },
  title: {
    marginTop: "40px",
    fontSize: "24px",
    marginBottom: "20px",
  },
  toggleButton: {
    padding: "10px 20px",
    backgroundColor: "#4CAF50",
    color: "white",
    border: "none",
    cursor: "pointer",
  },
  card: {
    background: "#f9f9f9",
    borderRadius: "10px",
    padding: "20px",
    marginBottom: "20px",
    boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
    backgroundColor: "#fff",
  },
  addressTitle: {
    fontSize: "18px",
    fontWeight: "bold",
    marginBottom: "10px",
  },
  defaultBadge: {
    display: "inline-block",
    backgroundColor: "#ff9800",
    color: "#fff",
    padding: "5px 10px",
    fontSize: "12px",
    fontWeight: "bold",
    borderRadius: "5px",
    marginBottom: "5px",
  },
  defaultButton: {
    backgroundColor: "#007bff",
    color: "#fff",
    border: "none",
    padding: "10px 15px",
    borderRadius: "5px",
    cursor: "pointer",
    transition: "background 0.3s",
  },
  formRow: {
    display: "flex",
    alignItems: "center",
    gap: "10px",
    marginTop: "5px"
  },
  label: {
    width: "120px",
    fontWeight: "bold",
    textAlign: "right",
  },
  addinput: {
    width: "100%",
    padding: "10px",
    margin: "5px 0",
    borderRadius: "5px",
    border: "1px solid #ccc",
  },
  addAddressButton: {
    backgroundColor: "#4CAF50",
    padding: "10px 20px",
    color: "white",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
  },
  label: {
    marginBottom: "5px",
    fontWeight: "bold",
  },
  editinput: {
    width: "100%",
    padding: "10px",
    margin: "5px 0",
    borderRadius: "5px",
    border: "1px solid #ccc",
  },
  saveButton: {
    backgroundColor: "#4CAF50",
    color: "white",
    padding: "10px 20px",
    border: "none",
    cursor: "pointer",
    borderRadius: "5px",
  },
  cancelButton: {
    backgroundColor: "#ccc",
    color: "white",
    padding: "10px 20px",
    border: "none",
    cursor: "pointer",
    borderRadius: "5px",
  },
  editButton: {
    backgroundColor: "#4CAF50",
    padding: "10px 20px",
    color: "white",
    border: "none",
    cursor: "pointer",
    borderRadius: "5px",
  },
  deleteButton: {
    backgroundColor: "#f44336",
    padding: "10px 20px",
    color: "white",
    border: "none",
    cursor: "pointer",
    borderRadius: "5px",
  },
  setDefaultButton: {
    backgroundColor: "#ff9800",
    padding: "10px 20px",
    color: "white",
    border: "none",
    cursor: "pointer",
    borderRadius: "5px",
  },
};

export default ShippingAddressPage;

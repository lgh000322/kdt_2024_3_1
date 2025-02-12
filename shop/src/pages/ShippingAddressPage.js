import React, { useState, useEffect } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useSelector } from "react-redux";
import {
  getShippingAddresses,
  updateShippingAddress,
  addShippingAddress,
  deleteShippingAddress,
} from "../api/shippingAddressApi";
import DaumPostcode from "react-daum-postcode";

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

  const [shippingAddresses, setShippingAddresses] = useState([]); // api 호출 후 배송지 목록 상태 설정
  const [editingAddress, setEditingAddress] = useState(null); // 수정될 배송지를 저장하는 상태
  const [newAddress, setNewAddress] = useState(initState); // 새로 생성할 배송지를 저장하는 상태
  const [showNewAddressForm, setShowNewAddressForm] = useState(false); // 배송지 생성 폼태그 생성 플래그
  const [modalState, setModalState] = useState(false); // 주소찾는 모달 띄워줄 플래그

  const handleAddNewField = (newField) => {
    console.log(newField);
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

  const handleAddAddress = async () => {
    try {
      const addedAddress = await addShippingAddress(newAddress, accessToken);
      setShippingAddresses((prev) => [...prev, addedAddress]);
      setNewAddress(initState);
      alert("배송지가 성공적으로 추가되었습니다.");
    } catch (error) {
      console.error("배송지 추가 실패:", error);
    }
    window.location.reload();
  };

  const handleUpdateAddress = async () => {
    if (!editingAddress) return;

    console.log("🛠 수정 요청 데이터:", editingAddress);
    console.log("🛠 destinationId:", editingAddress.destinationId);

    try {
      await updateShippingAddress(
        editingAddress.destinationId,
        editingAddress,
        accessToken
      );

      alert("배송지가 성공적으로 수정되었습니다.");
    } catch (error) {
      console.error("배송지 수정 실패:", error);
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
    }
  };

  const handleEdit = (address) => {
    console.log("🛠 수정할 데이터:", address);

    setEditingAddress({ ...address });
  };


  return (
    <BasicLayout>
      <div className="p-10 max-w-2xl mx-auto">
        <h1 className="text-3xl font-semibold mb-8 text-center">배송지 관리</h1>

        <div className="mb-4 flex justify-start">
          <button
            className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
            onClick={() => setShowNewAddressForm((prev) => !prev)}
          >
            {showNewAddressForm ? "새 배송지 추가 숨기기" : "새 배송지 추가"}
          </button>
        </div>

        {showNewAddressForm && (
          <div className="border border-gray-300 rounded-lg p-5 mb-5 shadow-md">
            <h3 className="text-lg font-semibold mb-4">새 배송지 추가</h3>
            <input
              type="text"
              value={newAddress.destinationName}
              onChange={(e) =>
                handleInputChange("destinationName", e.target.value, true)
              }
              className="w-full p-2 mb-2 text-sm border border-gray-300 rounded-md"
              placeholder="배송지 이름"
            />
            <input
              type="text"
              value={newAddress.receiverName}
              onChange={(e) =>
                handleInputChange("receiverName", e.target.value, true)
              }
              className="w-full p-2 mb-2 text-sm border border-gray-300 rounded-md"
              placeholder="받는 사람"
            />
            <input
              type="text"
              value={newAddress.tel}
              onChange={(e) => handleInputChange("tel", e.target.value, true)}
              className="w-full p-2 mb-2 text-sm border border-gray-300 rounded-md"
              placeholder="연락처"
            />
            <button
              className="w-full p-2 mb-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
              onClick={() => setModalState((prevModal) => !prevModal)}
            >
              주소 찾기
            </button>
            {modalState && (
              <DaumPostcode style={postCodeStyle} onComplete={onCompletePost} />
            )}
            <input
              type="text"
              value={newAddress.address}
              onChange={(e) =>
                handleInputChange("address", e.target.value, true)
              }
              className="w-full p-2 mb-2 text-sm border border-gray-300 rounded-md"
              readOnly
              placeholder="주소"
            />
            <input
              type="number"
              value={newAddress.zipCode}
              onChange={(e) =>
                handleInputChange("zipCode", e.target.value, true)
              }
              className="w-full p-2 mb-2 text-sm border border-gray-300 rounded-md"
              placeholder="우편번호"
              readOnly
            />
            <label className="flex items-center space-x-2">
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
              />
              <span>기본 배송지로 설정</span>
            </label>
            <button
              className="mt-4 p-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
              onClick={handleAddAddress}
            >
              추가
            </button>
          </div>
        )}

        {shippingAddresses.map((address, index) => (
          <div
            key={index}
            className="border border-gray-300 rounded-lg p-5 mb-5 shadow-md"
          >
            {address.isSelectedDefault && (
              <span className="inline-block bg-orange-500 text-white px-3 py-1 text-xs font-bold rounded-md mb-2">
                기본 배송지
              </span>
            )}
            {editingAddress &&
            editingAddress.destinationId === address.destinationId ? (
              <div>
                <div className="mb-2 flex items-center">
                  <p className="w-28 font-semibold">배송지 이름:</p>
                  <input
                    type="text"
                    value={editingAddress.destinationName}
                    onChange={(e) =>
                      handleInputChange("destinationName", e.target.value)
                    }
                    className="w-full p-2 text-sm border border-gray-300 rounded-md"
                  />
                </div>
                <div className="mb-2 flex items-center">
                  <p className="w-28 font-semibold">받는 사람:</p>
                  <input
                    type="text"
                    value={editingAddress.receiverName}
                    onChange={(e) =>
                      handleInputChange("receiverName", e.target.value)
                    }
                    className="w-full p-2 text-sm border border-gray-300 rounded-md"
                  />
                </div>
                <div className="mb-2 flex items-center">
                  <p className="w-28 font-semibold">연락처:</p>
                  <input
                    type="text"
                    value={editingAddress.tel}
                    onChange={(e) => handleInputChange("tel", e.target.value)}
                    className="w-full p-2 text-sm border border-gray-300 rounded-md"
                  />
                </div>
                <button
                  className="w-full p-2 mb-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
                  onClick={() => setModalState((prevModal) => !prevModal)}
                >
                  주소 찾기
                </button>
                {modalState && (
                  <DaumPostcode
                    style={postCodeStyle}
                    onComplete={onCompletePost}
                  />
                )}
                <div className="mb-2 flex items-center">
                  <p className="w-28 font-semibold">주소:</p>
                  <input
                    type="text"
                    value={editingAddress.address}
                    onChange={(e) =>
                      handleInputChange("address", e.target.value)
                    }
                    className="w-full p-2 text-sm border border-gray-300 rounded-md"
                    readOnly
                  />
                </div>
                <div className="mb-2 flex items-center">
                  <p className="w-28 font-semibold">우편번호:</p>
                  <input
                    type="number"
                    value={editingAddress.zipCode}
                    onChange={(e) =>
                      handleInputChange("zipCode", e.target.value)
                    }
                    className="w-full p-2 text-sm border border-gray-300 rounded-md"
                    readOnly
                  />
                </div>
                <label className="flex items-center space-x-2">
                  <input
                    type="checkbox"
                    checked={editingAddress.isSelectedDestination}
                    onChange={(e) =>
                      handleInputChange(
                        "isSelectedDestination",
                        e.target.checked
                      )
                    }
                  />
                  <span>기본 배송지로 설정</span>
                </label>
                <div className="mt-4 flex justify-between">
                  <button
                    className="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
                    onClick={handleUpdateAddress}
                  >
                    저장
                  </button>
                  <button
                    className="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
                    onClick={() => setEditingAddress(null)}
                  >
                    취소
                  </button>

                </div>
              </div>
            ) : (
              <div>
                <p className="mb-1">
                  <span className="font-semibold">배송지 이름:</span>{" "}
                  {address.destinationName}
                </p>
                <p className="mb-1">
                  <span className="font-semibold">받는 사람:</span>{" "}
                  {address.receiverName}
                </p>
                <p className="mb-1">
                  <span className="font-semibold">연락처:</span> {address.tel}
                </p>
                <p className="mb-1">
                  <span className="font-semibold">주소:</span> {address.address}
                </p>
                <p className="mb-1">
                  <span className="font-semibold">우편번호:</span>{" "}
                  {address.zipCode}
                </p>
                <div className="mt-4 flex justify-between">
                  <button
                    className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-yellow-600 transition-colors"
                    onClick={() => handleEdit(address)}
                  >
                    수정
                  </button>
                  <button
                    className="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
                    onClick={() => handleDeleteAddress(address)}
                  >
                    삭제
                  </button>
                </div>

              </div>
            )}
          </div>
        ))}
      </div>
    </BasicLayout>
  );
}
export default ShippingAddressPage;

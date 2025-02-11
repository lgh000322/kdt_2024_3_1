import React, { useEffect, useState } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { getShippingAddresses } from "../api/shippingAddressApi";
import { useSelector } from "react-redux";
import useCustomMove from "../hook/useCustomMove";
import { useSearchParams } from "react-router-dom";
import { postOrderHistory } from "../api/OrderListApi";

function ProductPaymentPage() {
  const [destination, setDestination] = useState([]);
  const [selectedDestination, setSelectedDestination] = useState(null); // 선택된 destination 객체 저장
  const { moveToDestination, moveToPay } = useCustomMove();
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;
  const [queryParams] = useSearchParams();
  const productName = queryParams.get("productName"); // 선택된 상품의 이름 (쿼리 파라미터에서 가져옴)
  const productCount = queryParams.get("productCount"); // 선택한 상품의 수
  const totalPrice = queryParams.get("totalPrice"); // 선택된 상품의 총 가격 (쿼리 파라미터에서 가져옴)
  const imgUrl = queryParams.get("imgUrl"); // 선택된 상품의 메인이미지 (쿼리 파라미터에서 가져옴)
  const selectedSize = queryParams.get("selectedSize"); // 선택된 상품의 신발 사이즈 (쿼리 파라미터에서 가져옴)
  const productId = queryParams.get("productId"); // 상품의 아이디

  const formattedPrice = new Intl.NumberFormat("ko-KR").format(
    parseInt(totalPrice)
  );

  const handleAddressChange = (e) => {
    const selectedId = e.target.value; // 선택한 destinationId
    const selected = destination.find((item) => {
      return item.destinationId == selectedId;
    }); // destination 목록에서 선택된 객체 찾기
    setSelectedDestination(selected); // 선택된 객체 상태로 저장
  };

  const handleDeliveryMessageChange = (e) => {
    const deliveryMessage = {
      deliveryMessage: e.target.value,
    };

    setSelectedDestination((prev) => ({ ...prev, ...deliveryMessage }));
  };

  const clickPaymentBtn = (e) => {
    e.preventDefault();

    // 주문 상태를 저장하는 api를 호출해야한다 (결제전)
    const orderRequest = {
      totalPrice: totalPrice,
      address: selectedDestination.address,
      destinationName: selectedDestination.destinationName,
      receiverName: selectedDestination.receiverName,
      tel: selectedDestination.tel,
      zipCode: selectedDestination.zipCode,
      deliveryMessage: selectedDestination.deliveryMessage,
      orderProductCount: 1,
      orderProductRequests: [
        {
          productId: productId,
          shoesSize: selectedSize,
          productCount: productCount,
          productTotalPrice: totalPrice,
        },
      ],
    };

    postOrderHistory(accessToken, orderRequest).then((res) => {
      if (res.code === 200) {
        const orderId = res.data;
        moveToPay(productName, totalPrice, orderId);
      }
    });
  };

  useEffect(() => {
    getShippingAddresses(accessToken).then((data) => {
      const list = data.map((item) => {
        return {
          address: item.address,
          destinationId: item.destinationId,
          destinationName: item.destinationName,
          tel: item.tel,
          zipCode: item.zipCode,
          receiverName: item.receiverName,
          deliveryMessage: item.deliveryMessage,
        };
      });

      setDestination(list);
    });
  }, [accessToken]);

  return (
    <BasicLayout>
      <div className="max-w-lg mx-auto my-10 p-8 bg-white rounded-lg shadow-lg">
        <h2 className="text-2xl font-semibold mb-8 text-gray-800">주문/결제</h2>

        <form>
          <div className="mb-6">
            <label className="block text-sm font-medium text-gray-600">
              받는 사람 이름
            </label>
            <input
              type="text"
              name="recipientName"
              placeholder="배송지 이름을 입력해주세요."
              className="w-full p-3 mt-1 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-blue-500"
              defaultValue={
                selectedDestination ? selectedDestination.receiverName : ""
              }
            />
          </div>

          <div className="mb-6">
            <label className="block text-sm font-medium text-gray-600">
              전화번호
            </label>
            <input
              type="tel"
              name="phoneNumber"
              placeholder="전화번호를 입력해주세요."
              className="w-full p-3 mt-1 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-blue-500"
              defaultValue={selectedDestination ? selectedDestination.tel : ""}
            />
          </div>

          <div className="mb-6 flex items-center justify-between">
            <label className="block text-sm font-medium text-gray-600">
              주소
            </label>
            <button
              type="button"
              className="text-sm text-blue-500 hover:underline"
              onClick={moveToDestination}
            >
              새로운 배송지 추가하기
            </button>
          </div>
          <select
            name="address"
            className="w-full p-3 mt-1 border border-gray-300 rounded-lg text-sm appearance-none focus:ring-2 focus:ring-blue-500"
            onChange={handleAddressChange}
          >
            <option value="">배송지를 선택하세요</option>
            {destination.map((destination) => (
              <option
                key={destination.destinationId}
                value={destination.destinationId}
              >
                {destination.destinationName} : {destination.address}
              </option>
            ))}
          </select>

          <div className="mb-6">
            <label className="block text-sm font-medium text-gray-600">
              배송 메시지
            </label>
            <input
              type="text"
              name="deliveryMessage"
              placeholder="배송 메시지를 입력해주세요."
              className="w-full p-3 mt-1 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-blue-500"
              defaultValue={
                selectedDestination ? selectedDestination.deliveryMessage : ""
              }
              onChange={handleDeliveryMessageChange}
            />
          </div>

          <div className="mb-6 bg-gray-50 p-5 rounded-lg border border-gray-200">
            <h3 className="text-lg font-semibold mb-4 text-gray-800">
              주문 상품 정보
            </h3>
            <div className="flex items-center">
              <div className="w-20 h-20 bg-gray-100 rounded-lg flex items-center justify-center text-xs text-gray-500">
                <img src={imgUrl} alt="상품 이미지"></img>
              </div>
              <div className="ml-5 flex-1">
                <p className="text-base font-medium mb-2">{productName}</p>
                <p className="text-xl font-semibold text-blue-700">
                  ₩ {formattedPrice}
                </p>
              </div>
            </div>
          </div>

          <button
            className="w-full py-4 bg-gray-700 text-white rounded-lg text-lg font-semibold hover:bg-gray-800 transition-all"
            onClick={clickPaymentBtn}
          >
            결제하기
          </button>
        </form>
      </div>
    </BasicLayout>
  );
}

export default ProductPaymentPage;

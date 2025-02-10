import React, { useEffect, useState } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { getShippingAddresses } from "../api/shippingAddressApi";
import { useSelector } from "react-redux";

function ProductPaymentPage() {
  const [destination, setDestination] = useState([]);
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  useEffect(() => {
    getShippingAddresses(accessToken).then((data) => {
      setDestination(data);
    });
  }, []);

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
            />
          </div>

          <div className="mb-6 flex items-center justify-between">
            <label className="block text-sm font-medium text-gray-600">
              주소
            </label>
            <button
              type="button"
              className="text-sm text-blue-500 hover:underline"
              onClick={() => alert("새로운 배송지 추가")}
            >
              새로운 배송지 추가하기
            </button>
          </div>
          <select
            name="address"
            className="w-full p-3 mt-1 border border-gray-300 rounded-lg text-sm appearance-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">주소를 선택해주세요</option>
            <option value="서울특별시">서울특별시</option>
            <option value="부산광역시">부산광역시</option>
            <option value="대구광역시">대구광역시</option>
            <option value="인천광역시">인천광역시</option>
            <option value="광주광역시">광주광역시</option>
            <option value="대전광역시">대전광역시</option>
            <option value="울산광역시">울산광역시</option>
            <option value="경기도">경기도</option>
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
            />
          </div>

          <div className="mb-6 bg-gray-50 p-5 rounded-lg border border-gray-200">
            <h3 className="text-lg font-semibold mb-4 text-gray-800">
              주문 상품 정보
            </h3>
            <div className="flex items-center">
              <div className="w-20 h-20 bg-gray-100 rounded-lg flex items-center justify-center text-xs text-gray-500">
                이미지
              </div>
              <div className="ml-5 flex-1">
                <p className="text-base font-medium mb-2">상품 이름 외 x개</p>
                <p className="text-xl font-semibold text-blue-700">₩ 50,000</p>
              </div>
            </div>
          </div>

          <button
            type="submit"
            className="w-full py-4 bg-gray-700 text-white rounded-lg text-lg font-semibold hover:bg-gray-800 transition-all"
          >
            결제하기
          </button>
        </form>
      </div>
    </BasicLayout>
  );
}

export default ProductPaymentPage;

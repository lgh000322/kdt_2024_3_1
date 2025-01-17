import React from 'react';

const OrderItem = ({ orderNumber, imageUrl, productName, price, status }) => {
  return (
    <div className="bg-gray-100 rounded-lg p-4 mb-4">
      <div className="flex items-start space-x-4">
        {/* 상품 이미지 */}
        <div className="w-24 h-24 bg-red-300 rounded-md flex-shrink-0 overflow-hidden">
          <img
            src={imageUrl || "/api/placeholder/96/96"}
            alt={productName}
            className="w-full h-full object-cover"
          />
        </div>

        {/* 주문 정보 */}
        <div className="flex-1 min-w-0">
          <div className="flex justify-between items-start">
            <div>
              <p className="text-sm text-gray-500 mb-1">주문번호: {orderNumber}</p>
              <h3 className="text-lg font-medium text-gray-900 mb-2">{productName}</h3>
              <p className="text-lg font-bold text-gray-900">{price.toLocaleString()}원</p>
            </div>
            <span className="px-3 py-1 text-sm font-medium text-blue-600 bg-blue-100 rounded-full">
              {status}
            </span>
          </div>
        </div>
      </div>

      {/* 버튼 그룹 */}
      <div className="mt-4 flex space-x-2 justify-end">
        <button className="px-4 py-2 text-sm text-gray-600 hover:text-gray-900 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
          주문 상세 내역
        </button>
        <button className="px-4 py-2 text-sm text-gray-600 hover:text-gray-900 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
          배송 조회
        </button>
        <button className="px-4 py-2 text-sm text-gray-600 hover:text-gray-900 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
          리뷰 작성
        </button>
      </div>
    </div>
  );
};

const OrderItemList = () => {
  const sampleOrders = [
    {
      orderNumber: "2024011501",
      imageUrl: "/api/placeholder/96/96",
      productName: "클래식 더비 슈즈",
      price: 159000,
      status: "배송중"
    },
    {
      orderNumber: "2024011502",
      imageUrl: "/api/placeholder/96/96",
      productName: "캐주얼 스니커즈",
      price: 89000,
      status: "배송완료"
    }
  ];

  return (
    <div className="max-w-2xl mx-auto p-4">
      {sampleOrders.map((order) => (
        <OrderItem
          key={order.orderNumber}
          {...order}
        />
      ))}
    </div>
  );
};

export default OrderItemList;
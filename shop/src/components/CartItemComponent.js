import React, { useState } from "react";

const CartItemComponent = ({
  imageUrl,
  makerName,
  productName,
  initialQuantity,
  basePrice,
  onRemove,
  onSelect,
  isSelected,
}) => {
  const [quantity, setQuantity] = useState(initialQuantity);

  const incrementQuantity = () => {
    setQuantity((prev) => prev + 1);
  };

  const decrementQuantity = () => {
    if (quantity > 1) {
      setQuantity((prev) => prev - 1);
    }
  };

  const totalPrice = basePrice * quantity;

  return (
    <div className="bg-gray-100 rounded-lg p-4 mb-4 shadow-sm flex items-start space-x-4">
      {/* 왼쪽 상단 체크박스 */}
      <input
        type="checkbox"
        checked={isSelected}
        onChange={onSelect}
        className="w-5 h-5 mt-2"
      />

      {/* 상품 이미지 */}
      <div className="w-24 h-24 bg-red-300 rounded-md flex-shrink-0 overflow-hidden">
        <img
          src={imageUrl}
          alt={productName}
          className="w-full h-full object-cover"
        />
      </div>

      {/* 상품 정보 */}
      <div className="flex-1 min-w-0">
        <h3 className="text-lg font-medium text-gray-900">{productName}</h3>
        <p className="text-sm text-gray-500">{makerName}</p>

        {/* 수량 조절 및 가격 */}
        <div className="flex items-center space-x-4 mt-2">
          <button
            onClick={decrementQuantity}
            disabled={quantity <= 1}
            className="w-8 h-8 flex items-center justify-center text-gray-600 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors"
          >
            -
          </button>
          <span className="w-8 text-center text-gray-900">{quantity}</span>
          <button
            onClick={incrementQuantity}
            className="w-8 h-8 flex items-center justify-center text-gray-600 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors"
          >
            +
          </button>
        </div>

        <p className="text-lg font-bold text-gray-900 mt-2">
          {totalPrice.toLocaleString()}원
        </p>
      </div>

      {/* 삭제 버튼 */}
      <button
        onClick={onRemove}
        className="px-4 py-2 text-sm text-red-600 hover:text-red-700 border border-red-300 hover:border-red-400 rounded-md hover:bg-red-50 transition-colors"
      >
        삭제
      </button>
    </div>
  );
};

export default CartItemComponent;

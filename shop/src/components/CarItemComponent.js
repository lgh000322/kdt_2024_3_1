import React, { useState } from 'react';

const CartItemComponent = ({
  imageUrl,                //이미지 주소
  makerName,               // 판매회사이름
  productName,              // 상품이름
  initialQuantity,          //수량
  basePrice                 // 기본가격 -> 원가 * 장바구니에 담긴 수량 
}) => {
  const [quantity, setQuantity] = useState(initialQuantity);

  const incrementQuantity = () => {
    setQuantity(prev => prev + 1);
  };

  const decrementQuantity = () => {
    if (quantity > 1) {
      setQuantity(prev => prev - 1);
    }
  };

  const totalPrice = parseInt(basePrice) * quantity;

  return (
    <div className="bg-gray-100 rounded-lg p-4 mb-4">
      <div className="flex items-start space-x-4">
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
          <div className="flex justify-between items-start">
            <div className="space-y-1">
              <p className="text-sm text-gray-500">{makerName}</p>
              <h3 className="text-lg font-medium text-gray-900">{productName}</h3>
              <div className="flex items-center space-x-4 mt-2">
                <div className="flex items-center space-x-2">
                  <button 
                    onClick={decrementQuantity}
                    className="w-8 h-8 flex items-center justify-center text-gray-600 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors"
                    disabled={quantity <= 1}
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
              </div>
              <p className="text-lg font-bold text-gray-900">{totalPrice.toLocaleString}원</p>
            </div>
            
            {/* 구매 버튼 */}
            <button className="px-6 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-gray-300 transition-colors">
              구매
            </button>
          </div>
        </div>
      </div>

      {/* 버튼 그룹 */}
      <div className="mt-4 flex space-x-2 justify-end">
        <button className="px-4 py-2 text-sm text-red-600 hover:text-red-700 border border-red-300 hover:border-red-400 rounded-md hover:bg-red-50 transition-colors">
          삭제
        </button>
      </div>
    </div>
  );
};

export default CartItemComponent;
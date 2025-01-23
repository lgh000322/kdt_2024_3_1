import React, { useState } from 'react';
import UploadLayout from "../layouts/UploadLayout";
import { Upload } from 'lucide-react';

const LookUpProduct = () => {
  const [quantity, setQuantity] = useState(0);
  const price = 100;

  const handleIncrease = () => setQuantity(quantity + 1);
  const handleDecrease = () => {
    if (quantity > 0) setQuantity(quantity - 1);
  };

  const totalPrice = price * quantity;

  return (
    <UploadLayout>
        <main className="flex">
          {/* 상품 이미지 */}
          <div className="flex-1">
            <div className="bg-gray-300 w-full h-96 flex items-center justify-center">
              <p>상품 대표 이미지</p>
            </div>
            <div className="flex justify-center mt-4 space-x-2">
              {[1, 2, 3, 4, 5].map((num) => (
                <button
                  key={num}
                  className="w-8 h-8 border rounded-md flex items-center justify-center"
                >
                  {num}
                </button>
              ))}
            </div>
          </div>

          {/* 상품 정 보 */}
          <div className="flex-1 ml-8 border rounded-md p-4">
            <h1 className="text-2xl font-bold mb-4">상품 제목</h1>
            <hr></hr>
            <br></br>
            <p className="mb-4 text-xl">가격: {price.toLocaleString()} 원</p>
            <div className="flex items-center mb-4">
              <span className="text-xl">수량:</span>
              <button
                onClick={handleDecrease}
                className="ml-2 px-2 py-1 bg-gray-200 border rounded-md"
              >
                -
              </button>
              <span className="mx-4">{quantity}개</span>
              <button
                onClick={handleIncrease}
                className="px-2 py-1 bg-gray-200 border rounded-md"
              >
                +
              </button>
            </div>
            <p className="mb-4 text-xl">총 금액: {totalPrice.toLocaleString()} 원</p>
            <div className="flex space-x-4 mt-40">
              <button className="flex-1 py-2 bg-red-300 rounded-md">장바구니</button>
              <button className="flex-1 py-2 bg-red-300 rounded-md">결제</button>
            </div>
          </div>
        </main>

        {/* 상품 설명 */}
        <section className="mt-8 p-4 border rounded-md">
          <h2 className="text-xl font-bold mb-4">상품 설명</h2>
          <p>여기에 상품 설명이 들어갑니다.</p>
        </section>
    </UploadLayout>
  );
};

export default LookUpProduct;
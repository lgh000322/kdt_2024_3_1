import { Link } from "react-router-dom";
import React from "react";

function ProductCardComponent({ image, title, price, id }) {
  return (
    <Link to={`/product/${id}`} className="group">
      <div className="border rounded-lg overflow-hidden shadow-sm hover:shadow-md transition-shadow">
        <div className="relative">
          <img
            src={image}
            alt={title}
            className="w-full h-48 object-cover" // 이미지 높이를 48로 고정하여 크기 줄이기
          />
        </div>
        <div className="p-4">
          <h3 className="text-lg font-medium text-gray-800 group-hover:text-black">
            {title}
          </h3>
          <p className="mt-1 text-gray-600">{price.toLocaleString()}원</p>
        </div>
      </div>
    </Link>
  );
}

export default ProductCardComponent;

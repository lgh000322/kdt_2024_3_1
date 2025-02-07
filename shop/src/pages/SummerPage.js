import React from "react";
import BasicLayout from "../layouts/BasicLayout";
import ProductCardComponent from "../components/ProductCardComponent";
import SubProductLayout from "../layouts/SubProductLayout";

function SummerPage() {
  const products = [
    { id: 1, title: "여름상품1", price: 89000, image: "img/logo.png" },
    { id: 2, title: "여름상품2", price: 99000, image: "img/logo.png" },
    { id: 3, title: "여름상품3", price: 79000, image: "img/logo.png" },
    { id: 4, title: "여름상품4", price: 109000, image: "img/logo.png" },
  ];

  return (
    <SubProductLayout>
      {/* 상품 그리드 섹션 */}
      <div className="max-w-7xl mx-auto px-4 pt-12">
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {products.map((product) => (
            <ProductCardComponent
              key={product.id}
              id={product.id}
              title={product.title}
              price={product.price}
              image={product.image}
            />
          ))}
        </div>
      </div>
    </SubProductLayout>
  );
}

export default SummerPage;

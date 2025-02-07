import React, { useEffect, useState } from "react";
import ProductLayout from "../layouts/ProductLayout";
import ProductCardComponent from "../components/ProductCardComponent";
import { getProductList } from "../api/productApi";

const initState = [
  { id: 1, title: "상품1", price: 89000, image: "img/logo.png" },
  { id: 2, title: "상품2", price: 99000, image: "img/logo.png" },
  { id: 3, title: "상품3", price: 79000, image: "img/logo.png" },
  { id: 4, title: "상품4", price: 109000, image: "img/logo.png" },
];
function MainPage() {
  const [products, setProduct] = useState(initState);
  const [page, setPage] = useState(0);

  useEffect(() => {
    getProductList(page, 10, null, null, null, null, null).then((res) => {
      const data = res.data;
      console.log(data);
    });
  }, [page]);

  return (
    <div>
      <ProductLayout>
        {/* 상품 그리드 섹션 */}
        <div className="max-w-7xl mx-auto px-4">
          <h2 className="text-2xl font-bold mb-6">신상품</h2>
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
      </ProductLayout>
    </div>
  );
}

export default MainPage;

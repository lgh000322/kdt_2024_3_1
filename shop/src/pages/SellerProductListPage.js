import React, { useEffect, useState } from "react";
import WishLayout from "../layouts/WishLayout";
import useCustomMove from "../hook/useCustomMove";
import { getSellerProductList } from "../api/productApi";
import { useSelector } from "react-redux";

function SellerProductListPage() {
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(0);
  const { moveToProductOne , moveToModificationPage } = useCustomMove();
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;

  useEffect(() => {
    getSellerProductList(accessToken, page, 10).then((res) => {
      const data = res.data;
      setProducts(data);
    });
  }, [page]);

  return (
    <WishLayout>
      <div className="p-10">
        <h1 className="text-left text-2xl font-bold">내가 등록한 상품</h1>
        <hr className="my-4" />
        {products.length > 0 ? (
          <div className="flex gap-7 flex-wrap justify-start">
            {products.map((product) => (
              <div
                key={product.productId}
                className="w-52 h-80 shadow-md rounded-lg p-3 bg-white flex flex-col justify-between"
              >
                <img
                  src={product.imgUrl}
                  alt={product.productName}
                  onClick={() => {
                    moveToProductOne(product.productId);
                  }}
                  className="w-full h-60 object-cover rounded-md cursor-pointer"
                />
                <hr className="my-2" />
                <h3 className="text-lg font-semibold my-1">
                  {product.productName}
                </h3>
                <p className="text-gray-500">
                  {product.price.toLocaleString()}원
                </p>
                <button
                  onClick={() => moveToModificationPage(product.productId)}
                  className="bg-red-500 text-white px-3 py-2 rounded-md hover:bg-red-600 transition-colors"
                >
                  상품 업데이트
                </button>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-center mt-12">찜한 상품이 없습니다.</p>
        )}
      </div>
    </WishLayout>
  );
}

export default SellerProductListPage;

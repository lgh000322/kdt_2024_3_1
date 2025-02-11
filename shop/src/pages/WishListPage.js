import React, { useEffect, useState } from "react";
import WishLayout from "../layouts/WishLayout";
import { deleteWishItem, getWishList } from "../api/wishApi";
import { useSelector } from "react-redux";
import useCustomMove from "../hook/useCustomMove";

const WishlistPage = () => {
  const [wishlist, setWishlist] = useState([]);
  const [page, setPage] = useState(0);
  const loginState = useSelector((state) => state.loginSlice);
  const accessToken = loginState.accessToken;
  const { moveToProductOne } = useCustomMove();

  useEffect(() => {
    getWishList(accessToken, page, 10).then((res) => {
      const data = res.data;
      setWishlist(data);
    });
  }, [page]);

  const removeFromWishlist = (likesId, productId) => {
    // 화면 업데이트를 하기 위한 객체
    const updatedWishlist = wishlist.filter((item) => item.likesId !== likesId);

    // delete api 실행
    deleteWishItem(accessToken, likesId, productId).then((res) => {
      if (res.code === 200) {
        alert("찜 아이템을 지우는데 성공했습니다.");
        setWishlist(updatedWishlist);
      }
    });
  };

  return (
    <WishLayout>
      <div className="p-10">
        <h1 className="text-left text-2xl font-bold">찜한 상품</h1>
        <hr className="my-4" />
        {wishlist.length > 0 ? (
          <div className="flex gap-7 flex-wrap justify-start">
            {wishlist.map((item) => (
              <div
                key={item.likesId}
                className="w-52 h-80 shadow-md rounded-lg p-3 bg-white flex flex-col justify-between"
              >
                <img
                  src={item.imageUrl}
                  alt={item.title}
                  onClick={() => {
                    moveToProductOne(item.productId);
                  }}
                  className="w-full h-60 object-cover rounded-md cursor-pointer"
                />
                <hr className="my-2" />
                <h3 className="text-lg font-semibold my-1">{item.title}</h3>
                <p className="text-gray-500">{item.price.toLocaleString()}원</p>
                <button
                  onClick={() =>
                    removeFromWishlist(item.likesId, item.productId)
                  }
                  className="bg-red-500 text-white px-3 py-2 rounded-md hover:bg-red-600 transition-colors"
                >
                  삭제
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
};

export default WishlistPage;

import React, { useState, useEffect } from "react";
import WishLayout from "../layouts/WishLayout";

const WishlistPage = () => {
  const [wishlist, setWishlist] = useState([
    {
      id: "ORD003",
      image: "https://via.placeholder.com/150", // 임시 이미지 URL
      title: "상품 C",
      price: 30000,
    },
    {
      id: "ORD004",
      image: "https://via.placeholder.com/150",
      title: "상품 D",
      price: 40000,
    },
    {
      id: "ORD005",
      image: "https://via.placeholder.com/150",
      title: "상품 E",
      price: 50000,
    },
    {
      id: "ORD006",
      image: "https://via.placeholder.com/150",
      title: "상품 H",
      price: 50000,
    },
    {
      id: "ORD007",
      image: "https://via.placeholder.com/150",
      title: "상품 Z",
      price: 50000,
    },
    {
      id: "ORD005",
      image: "https://via.placeholder.com/150",
      title: "상품 E",
      price: 50000,
    },
    {
      id: "ORD005",
      image: "https://via.placeholder.com/150",
      title: "상품 E",
      price: 50000,
    },
    {
      id: "ORD005",
      image: "https://via.placeholder.com/150",
      title: "상품 E",
      price: 50000,
    },
  ]);

  // 찜한 상품 삭제 기능
  const removeFromWishlist = (id) => {
    const updatedWishlist = wishlist.filter((item) => item.id !== id);
    setWishlist(updatedWishlist);
    localStorage.setItem("wishlist", JSON.stringify(updatedWishlist));
  };

  return (
    <WishLayout>
      <div style={{ padding: "40px" }}>
        <h1 style={{ textAlign: "left", fontSize: "27px", fontWeight: "bold" }}>찜한 상품</h1>
        <hr />
        {wishlist.length > 0 ? (
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
              gap: "20px",
            }}
          >
            {wishlist.map((item) => (
              <div
                key={item.id}
                style={{
                  boxShadow: "0px 4px 6px rgba(0,0,0,0.1)",
                  borderRadius: "8px",
                  padding: "10px",
                  textAlign: "center",
                  backgroundColor: "#fff",
                }}
              >
                <img
                  src={item.image}
                  alt={item.title}
                  style={{
                    width: "100%",
                    height: "150px",
                    objectFit: "cover",
                    borderRadius: "8px",
                  }}
                />
                <h3 style={{ fontSize: "16px", margin: "10px 0" }}>{item.title}</h3>
                <p style={{ color: "#888", marginBottom: "10px" }}>{item.price.toLocaleString()}원</p>
                <button
                  // onClick={() => removeFromWishlist(item.id)}
                  style={{
                    backgroundColor: "#ff4d4f",
                    color: "#fff",
                    border: "none",
                    padding: "8px 12px",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  삭제
                </button>
              </div>
            ))}
          </div>
        ) : (
          <p style={{ textAlign: "center", marginTop: "50px" }}>찜한 상품이 없습니다.</p>
        )}
      </div>
    </WishLayout>
  );
};

export default WishlistPage;

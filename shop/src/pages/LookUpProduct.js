import React, { useEffect, useState } from "react";
import UploadLayout from "../layouts/UploadLayout";
import { Upload } from "lucide-react";
import { useParams } from "react-router-dom";
import { getProductOne } from "../api/productApi";
import { addWishItem } from "../api/wishApi";
import { useSelector } from "react-redux";
import useCustomMove from "../hook/useCustomMove";
import { addCartItem } from "../api/cartApi";

const LookUpProduct = () => {
  const { productId } = useParams();
  const [quantity, setQuantity] = useState(1); // 수량
  const [price, setPrice] = useState(100); // 가격
  const [image, setImage] = useState([]); // 메인이미지 + 사이드 이미지
  const [selectedImage, setSelectedImage] = useState(""); // 선택된 이미지 출력
  const [description, setDescription] = useState(""); // 상품에 대한 설명
  const [productName, setProductName] = useState(""); // 상품의 이름
  const [quantities, setQuantities] = useState([]); // 재고 정보
  const [selectedSize, setSelectedSize] = useState(null); // 선택된 사이즈
  const loginState = useSelector((state) => state.loginSlice);
  const { moveToCheckOut,moveToCart } = useCustomMove();

  useEffect(() => {
    getProductOne(productId).then((res) => {
      const data = res.data;
      setPrice(data.price); // 가격 세팅

      const imageForm = [
        data.mainImgUrl,
        ...data.sideImgInforms.map((sideImg) => sideImg.imgUrl),
      ]; // 이미지 세팅

      setImage(imageForm); // 전체 이미지
      setSelectedImage(imageForm[0]); // 메인 이미지
      setDescription(data.description); // 상품에 대한 설명
      setProductName(data.productName); // 상품의 이름
      setQuantities(data.productDetailsList); // 상품의 상세 재고
    });
  }, [productId]);

  // 수량 증가
  const handleIncrease = () => setQuantity(quantity + 1);

  // 수량 감소
  const handleDecrease = () => {
    if (quantity > 0) setQuantity(quantity - 1);
  };

  // 전체 가격
  const totalPrice = price * quantity;

  const addWishList = () => {
    let accessToken = loginState.accessToken;
    if (!accessToken) {
      alert("로그인 후 찜 상품을 등록할 수 있습니다.");
      return;
    }

    addWishItem(accessToken, productId).then((res) => {
      if (res.code === 200) {
        alert("찜 등록 성공");
      }
    });
  };

  const addCartList = () => {

    let accessToken = loginState.accessToken;
    if (!accessToken) {
      alert("로그인 후 장바구니에 담을 수 있습니다.");
      return;
    }

    //api 호출
    addCartItem(accessToken, productId, quantity, selectedSize, totalPrice).then((res) => {
      if (res.code === 200) {
        alert("장바구니 등록 성공");
      }
    });
  };

  return (
    <UploadLayout>
      <main className="flex">
        {/* 상품 이미지 */}
        <div className="flex-1">
          {/* 대표 이미지 */}
          <div className="bg-gray-300 w-full h-96 flex items-center justify-center">
            {selectedImage ? (
              <img
                src={selectedImage}
                alt="대표 이미지"
                className="h-full object-contain"
              />
            ) : (
              <p>상품 대표 이미지</p>
            )}
          </div>

          {/* 이미지 썸네일 리스트 */}
          <div className="flex justify-center mt-4 space-x-2">
            {image.map((imgUrl, index) => (
              <button
                key={index}
                onClick={() => setSelectedImage(imgUrl)} // 클릭 시 대표 이미지 변경
                className="w-16 h-16 border rounded-md flex items-center justify-center"
              >
                <img
                  src={imgUrl}
                  alt={`상품 이미지 ${index + 1}`}
                  className="w-full h-full object-cover"
                />
              </button>
            ))}
          </div>
        </div>

        {/* 상품 정 보 */}
        <div className="flex-1 ml-8 border rounded-md p-4">
          <h1 className="text-2xl font-bold mb-4">{productName}</h1>
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
          <p className="mb-4 text-xl">
            총 금액: {totalPrice.toLocaleString()} 원
          </p>
          <hr></hr>
          <p className="mb-4 text-xl">재고</p>
          {quantities.map((quantity, index) => (
            <p key={quantity.productDetailsId} className="mb-4 text-sm">
              {quantity.size}사이즈 : {quantity.quantityBySize}
            </p>
          ))}
          <p className="mb-4 text-xl">사이즈 선택</p>
          <div className="flex space-x-2">
            {quantities.map((quantity, index) => (
              <button
                key={quantity.productDetailsId}
                onClick={() => setSelectedSize(quantity.size)} // 사이즈 선택
                className={`px-4 py-2 border rounded-md ${
                  selectedSize === quantity.size ? "bg-blue-500 text-white" : ""
                }`}
              >
                {quantity.size}
              </button>
            ))}
          </div>
          <div className="flex space-x-4 mt-5">
            <button
              className="flex-1 py-2 bg-red-300 rounded-md"
              onClick={addWishList}
            >
              찜하기
            </button>
            <button className="flex-1 py-2 bg-red-300 rounded-md" onClick={addCartList}>
              장바구니
            </button>
            <button className="flex-1 py-2 bg-red-300 rounded-md">결제</button>
          </div>
        </div>
      </main>

      {/* 상품 설명 */}
      <section className="mt-8 p-4 border rounded-md">
        <h2 className="text-xl font-bold mb-4">상품 설명</h2>
        <p>{description}</p>
      </section>
    </UploadLayout>
  );
};

export default LookUpProduct;

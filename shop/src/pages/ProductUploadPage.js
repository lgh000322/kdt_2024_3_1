import React, { useState } from "react";
import UploadLayout from "../layouts/UploadLayout";

function ProductUploadPage() {
  const [productName, setProductName] = useState("");
  const [category, setCategory] = useState("");
  const [description, setDescription] = useState("");
  const [productPrice, setProductPrice] = useState("");
  const [representImage, setRepresentImage] = useState(null);
  const [images, setImages] = useState([]);
  const [hoveredImage, setHoveredImage] = useState(null); // 팝업 이미지 상태
  const [popupPosition, setPopupPosition] = useState({ x: 0, y: 0 });
  const fileInputRef = React.useRef(null);
  const multiFileInputRef = React.useRef(null);

  {/* 상품 대표 이미지(단일) */}
  const handleRepresentImageUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      setRepresentImage({
        file,
        preview: URL.createObjectURL(file),
      });
    }
  };

  {/* 상품 추가 이미지 업로드(다수) */}
  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files).map((file) => ({
      file,
      preview: URL.createObjectURL(file),
    }));
    setImages((prevImages) => [...prevImages, ...files]);
  };

  {/* 대표 이미지 삭제 */}
  const handleRemoveRepresentImage = () => {
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
    setRepresentImage(null);
  };
  
  {/* 상품 이미지 삭제 */}
  const handleRemoveImage = (index) => {
    setImages((prevImages) => {
      const updatedImages = prevImages.filter((_, i) => i !== index); // 이미지 삭제
      // 업로드된 파일 URL 해제
      URL.revokeObjectURL(prevImages[index].preview);
      return updatedImages;
    });
  };

  {/* 상품 대표 이미지 팝업 위치 조정 */}
  const handleHoverRepresentImage = (image, event) => {
    const imageRect = event.currentTarget.getBoundingClientRect();
    setHoveredImage(image.preview);
    setPopupPosition({
      x: imageRect.left + 250,
      y: imageRect.top + 180,
    });
  };

  {/* 상품 추가 이미지 팝업 위치 조정 */}
  const handleHoverImage = (image, event) => {
    const imageRect = event.currentTarget.getBoundingClientRect();
    setHoveredImage(image.preview);
    setPopupPosition({
      x: imageRect.left + 250, // 마우스 포인터 기준 위치 조정
      y: imageRect.top,
    });
  };

  {/* 상품 이미지 팝업 상호작용 */}
  const handleMouseLeave = () => {
    setHoveredImage(null);
  };

  {/* 상품 등록 버튼 */}
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({
      productName,
      category,
      description,
      representImage,
      images,
    });
    alert("상품이 등록되었습니다!");
  };

  return (
    <UploadLayout role="seller">
      <div className="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-lg border relative">
        <h1 className="text-2xl font-bold mb-6">상품 등록</h1>
        <form onSubmit={handleSubmit} className="space-y-6">
          {/* 대표 이미지 업로드 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              대표 이미지 업로드
            </label>
            <input
              ref={fileInputRef}
              type="file"
              accept="image/*"
              onChange={handleRepresentImageUpload}
              className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:border file:border-gray-300 file:rounded-lg file:bg-gray-50 file:text-gray-700"
            />
            {representImage && (
              <div className="mt-4 relative">
                <img
                  src={representImage.preview}
                  alt="대표 이미지"
                  className="w-60 h-auto object-cover rounded-lg"
                  onMouseMove={(e) => handleHoverRepresentImage(representImage, e)}
                  onMouseLeave={handleMouseLeave}
                />
                <button
                  type="button"
                  onClick={handleRemoveRepresentImage}
                  className="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 text-sm rounded-full"
                >
                  삭제
                </button>
              </div>
            )}
          </div>

          {/* 상품 이미지 업로드 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              상품 이미지 업로드
            </label>
            <input
              ref={multiFileInputRef}
              type="file"
              multiple
              accept="image/*"
              onChange={handleImageUpload}
              className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:border file:border-gray-300 file:rounded-lg file:bg-gray-50 file:text-gray-700"
            />
            <div className="mt-4 grid grid-cols-3 gap-4">
              {images.map((image, index) => (
                <div
                  key={index}
                  className="relative"
                  onMouseMove={(e) => handleHoverImage(image, e)}
                  onMouseLeave={handleMouseLeave}
                >
                  <img
                    src={image.preview}
                    alt={`uploaded-${index}`}
                    className="w-60 h-auto object-cover rounded-lg"
                  />
                  <button
                    type="button"
                    onClick={() => handleRemoveImage(index)}
                    className="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 text-sm rounded-full"
                  >
                    삭제
                  </button>
                </div>
              ))}
            </div>
          </div>

          {/* 상품명 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              상품명
            </label>
            <input
              type="text"
              value={productName}
              onChange={(e) => setProductName(e.target.value)}
              placeholder="상품명을 입력하세요"
              className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          {/* 카테고리 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              카테고리
            </label>
            <select
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            >
              <option value="">카테고리를 선택하세요</option>
              <option value="summer">여름용</option>
              <option value="winter">겨울용</option>
              <option value="men">남성용</option>
              <option value="women">여성용</option>
              <option value="kids">유아용</option>
            </select>
          </div>

          {/* 상품 설명란 */}
          <div className="mb-6">
            <label className="block text-sm font-medium mb-2" htmlFor="description"> 
              상품 설명
            </label>
            <textarea
              id="description"
              rows="5"
              placeholder="상품에 대한 설명을 입력하세요"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full border px-3 py-2 rounded-lg shadow-sm focus:outline-none focus:ring focus:ring-blue-300"
            />
          </div>

          {/* 상품 가격 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              상품 가격
            </label>
            <div className="relative">
              <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">
                ￦
              </span>
              <input
                type="text"
                value={productPrice}
                onChange={(e) => setProductPrice(e.target.value)}
                placeholder="가격을 입력하세요"
                className="block w-full pl-10 px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              />
            </div>
          </div>

          {/* 등록 버튼 */}
          <div className="flex justify-end space-x-4">
            <button
              type="button"
              className="px-6 py-2 bg-gray-300 text-gray-700 rounded-lg shadow-sm hover:bg-gray-400"
            >
              취소
            </button>
            <button
              type="submit"
              className="px-6 py-2 bg-blue-500 text-white rounded-lg shadow-sm hover:bg-blue-600"
            >
              등록
            </button>
          </div>
        </form>
      </div>
      {/* 이미지 팝업 */}
      {hoveredImage && (
        <div
          style={{
            position: "absolute",
            top: `${popupPosition.y}px`,
            left: `${popupPosition.x}px`,
            border: "1px solid #ddd",
            backgroundColor: "white",
            padding: "5px",
            zIndex: 1000,
          }}
          className="shadow-lg rounded"
        >
          <img
            src={hoveredImage}
            alt="미리보기"
            style={{
              width: "300px", // 팝업 이미지 크기
              height: "auto",
              objectFit: "cover",
            }}
          />
        </div>
      )}
    </UploadLayout>
  );
}

export default ProductUploadPage;

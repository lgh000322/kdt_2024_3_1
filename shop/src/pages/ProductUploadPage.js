import React, { useState } from "react";
import UploadLayout from "../layouts/UploadLayout";

function ProductUploadPage() {
  const [productName, setProductName] = useState("");
  const [category, setCategory] = useState("");
  const [description, setDescription] = useState("");
  const [productPrice, setProductPrice] = useState("");
  const [images, setImages] = useState([]); // 이미지 파일과 URL을 함께 관리

  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files).map((file) => ({
        file,
        preview: URL.createObjectURL(file),
    }));
    setImages((prevImages) => [...prevImages, ...files]);
  };

  const handlePriceChange = (e) => {
    const value = e.target.value;
    if (/^\d*$/.test(value)) {
      // 숫자만 허용
      setProductPrice(value);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({
      productName,
      category,
      description,
      images,
    });
    alert("상품이 등록되었습니다!");
  };
  
  const handleRemoveImage = (index) => {
    setImages((prevImages) => {
        URL.revokeObjectURL(prevImages[index].preview);
        return prevImages.filter((_, i) => i !== index);
    });
  };

  return (
    <UploadLayout role="seller">
        <div className="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-lg border">
            <h1 className="text-2xl font-bold mb-6">상품 등록</h1>
            <form onSubmit={handleSubmit} className="space-y-6">
                {/* 이미지 업로드 */}
                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">이미지 업로드</label>
                    <input
                        type="file"
                        multiple
                        accept="image/*"
                        onChange={handleImageUpload}
                        className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:border file:border-gray-300 file:rounded-lg file:bg-gray-50 file:text-gray-700"
                    />
                    {/* 업로드한 이미지 미리보기 */}
                    <div className="mt-4 grid grid-cols-3 gap-4">
                        {images.map((image, index) => (
                            <div key={index} className="relative">
                                <img src={image.preview} alt={`uploaded-${index}`} className="w-full h-32 object-cover rounded-lg"/>
                                <button type="button" onClick={() => handleRemoveImage(index)} className="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 text-sm rounded-full">삭제</button>
                            </div>
                        ))}
                    </div>
                </div>

                {/* 상품명 */}
                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">상품명</label>
                    <input
                        type="text"
                        value={productName}
                        onChange={(e) => setProductName(e.target.value)}
                        placeholder="상품명을 입력하세요"
                        className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    />
                </div>

                {/* 상품 카테고리 지정 */}
                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">카테고리</label>
                    <select
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                        className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500">
                        <option value="">카테고리를 선택하세요</option>
                        <option value="summer">여름용</option>
                        <option value="winter">겨울용</option>
                        <option value="men">남성용</option>
                        <option value="women">여성용</option>
                        <option value="kids">유아용</option>
                    </select>
                </div>

                {/* 상품 설명 */}
                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">상품 설명</label>
                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        placeholder="상품에 대한 설명을 입력하세요"
                        rows={5}
                        className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    />
                </div>

                {/* 상품 가격 */}
                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">상품 가격</label>
                    <div className="relative">
                        <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">￦</span>
                        <input
                        type="text"
                        value={productPrice}
                        onChange={handlePriceChange}
                        placeholder="가격을 입력하세요"
                        className="block w-full pl-10 px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                        />
                    </div>
                    {productPrice && (
                        <p className="text-sm text-gray-500 mt-2">입력된 가격: ￦{parseInt(productPrice).toLocaleString()}</p>
                        )}
                </div>

                {/* 상품 등록 버튼 */}
                <div className="flex justify-end space-x-4">
                    <button
                        type="button"
                        className="px-6 py-2 bg-gray-300 text-gray-700 rounded-lg shadow-sm hover:bg-gray-400">
                        취소
                    </button>
                    <button
                        type="submit"
                        className="px-6 py-2 bg-blue-500 text-white rounded-lg shadow-sm hover:bg-blue-600">
                        등록
                    </button>
                </div>
            </form>
        </div>
    </UploadLayout>
  );
}

export default ProductUploadPage;

import React, { useState, useRef } from "react";
import UploadLayout from "../layouts/UploadLayout";
import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css"; // Editor styles
import "@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css"; // Plugin styles
import colorSyntax from "@toast-ui/editor-plugin-color-syntax";
import { registerProduct } from "../api/productApi";
import { useSelector } from "react-redux";
import useCustomMove from "../hook/useCustomMove";

const productEnum = {
  SLIPPERS: { description: "슬리퍼" },
  SANDALS: { description: "샌들" },
  SNEAKERS: { description: "스니커즈" },
  RUNNING_SHOES: { description: "운동화" },
  LOAFERS: { description: "로퍼" },
  HIGH_HEELS: { description: "하이힐" },
  FLAT_SHOES: { description: "플랫슈즈" },
  BOOTS: { description: "부츠" },
  WALKERS: { description: "워커" },
  SLIP_ON: { description: "슬립온" },
  CHELSEA_BOOTS: { description: "첼시부츠" },
  OXFORD_SHOES: { description: "옥스퍼드 슈즈" },
  WINTER_BOOTS: { description: "방한화" },
  RAIN_BOOTS: { description: "레인부츠" },
  AQUA_SHOES: { description: "아쿠아슈즈" },
  DRESS_SHOES: { description: "드레스 신발" },
};

function ProductUploadPage() {
  const loginSlice = useSelector((state) => state.loginSlice);

  const [productName, setProductName] = useState("");
  const [productCategory, setProductCategory] = useState("");
  const [personCategory, setPersonCategory] = useState("");
  const [seasonCategory, setSeasonCategory] = useState("");
  const [description, setDescription] = useState("");
  const [productPrice, setProductPrice] = useState("");
  const [representImage, setRepresentImage] = useState(null);
  const [images, setImages] = useState([]);
  // const [hoveredImage, setHoveredImage] = useState(null); // 팝업 이미지 상태
  // const [popupPosition, setPopupPosition] = useState({ x: 0, y: 0 });
  const fileInputRef = useRef(null);
  const multiFileInputRef = useRef(null);
  const editorRef = useRef(null); // TOAST UI Editor reference
  const [sizeRange, setSizeRange] = useState({ min: "", max: "" });
  const [sizeInterval, setSizeInterval] = useState(""); // 간격 (5 또는 10)
  const [sizeQuantities, setSizeQuantities] = useState({});

  const { moveToProductAbs } = useCustomMove();

  const handleSizeRangeChange = (e) => {
    const { name, value } = e.target;
    setSizeRange((prevRange) => ({
      ...prevRange,
      [name]: value,
    }));
  };

  const handleSizeIntervalChange = (e) => {
    setSizeInterval(Number(e.target.value));
  };

  const handleQuantityChange = (size, e) => {
    setSizeQuantities((prevQuantities) => ({
      ...prevQuantities,
      [size]: e.target.value,
    }));
  };

  const renderSizeOptions = () => {
    const sizes = [];
    const { min, max } = sizeRange;
    const minVal = Math.max(0, Number(min)) || 0;
    const maxVal = Math.max(minVal, Number(max)) || minVal;
    const interval = [5, 10].includes(Number(sizeInterval)) ? Number(sizeInterval) : 5;
    for (let size = minVal; size <= maxVal; size += interval) {
      sizes.push(size);
    }
    return sizes;
  };

  {
    /* 상품 대표 이미지(단일) */
  }
  const handleRepresentImageUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      setRepresentImage({
        file,
        preview: URL.createObjectURL(file),
      });
    }
  };

  {
    /* 상품 추가 이미지 업로드(다수) */
  }
  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files).map((file) => ({
      file,
      preview: URL.createObjectURL(file),
    }));
    setImages((prevImages) => [...prevImages, ...files]);
  };

  {
    /* 대표 이미지 삭제 */
  }
  const handleRemoveRepresentImage = () => {
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
    setRepresentImage(null);
  };

  {
    /* 상품 이미지 삭제 */
  }
  const handleRemoveImage = (index) => {
    setImages((prevImages) => {
      const updatedImages = prevImages.filter((_, i) => i !== index); // 이미지 삭제
      URL.revokeObjectURL(prevImages[index].preview); // 업로드된 파일 URL 해제
      return updatedImages;
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    let accessToken = loginSlice.accessToken;

    // TOAST UI Editor에서 Markdown 데이터 가져오기
    const editorInstance = editorRef.current.getInstance();
    const markdownContent = editorInstance.getMarkdown();

    // 유효성 검사
    if (
      !productName ||
      !productCategory ||
      !personCategory ||
      !seasonCategory ||
      !productPrice ||
      !representImage
    ) {
      alert("모든 필드를 올바르게 입력해 주세요.");
      return;
    }

    // 가격이 숫자 형식인지 확인
    if (isNaN(productPrice) || productPrice <= 0) {
      alert("상품 가격은 숫자로 입력해 주세요.");
      return;
    }

    // 사이즈가 있을 경우 사이즈 관련 값이 비어 있지 않은지 체크
    if (sizeRange.min && sizeRange.max && sizeInterval) {
      // 사이즈별 수량이 하나라도 입력되지 않았다면
      const missingQuantities = renderSizeOptions().some(
        (size) => !sizeQuantities[size] || sizeQuantities[size] <= 0
      );
      if (missingQuantities) {
        alert("사이즈별 수량을 모두 입력해 주세요.");
        return;
      }
    }

    // 사이즈별 수량 맵 생성
    const sizeQuantityMap = renderSizeOptions().reduce((acc, size) => {
      if (sizeQuantities[size]) {
        acc[size] = sizeQuantities[size];
      }
      return acc;
    }, {});

    console.log("사이즈별 수량 맵:", sizeQuantityMap); // 예시: { 250: 10, 255: 5, 260: 8 }

    try {
      // FormData 객체 생성
      const formData = new FormData();

      const addProductInforms = {
        productName: productName,
        price: productPrice,
        productCategory: productCategory,
        personCategory: personCategory,
        seasonCategory: seasonCategory,
        description: description,
        sizeAndQuantity: sizeQuantityMap,
      };
      formData.append(
        "addProductInforms",
        new Blob([JSON.stringify(addProductInforms)], {
          type: "application/json",
        })
      );

      // 대표 이미지 추가
      if (representImage) {
        formData.append("mainImgFile", representImage.file);
      }

      // 추가 이미지들 추가
      images.forEach((image, index) => {
        formData.append(`sideImgFile`, image.file);
      });

      // FormData를 객체로 변환하여 콘솔에 출력 (디버깅용)
      const formDataObject = {};
      for (let [key, value] of formData.entries()) {
        if (value instanceof File) {
          formDataObject[key] = {
            fileName: value.name,
            fileType: value.type,
            fileSize: value.size,
          };
        } else {
          formDataObject[key] = value;
        }
      }
      console.log("전송될 데이터:", formData);

      registerProduct(formData, accessToken).then((res) => {
        if (res.code === 200) {
          moveToProductAbs();
        } else {
          alert("상품등록에 실패했습니다.");
        }
      });
    } catch (error) {
      console.error("상품 등록 중 오류 발생:", error);
      alert("상품 등록 중 오류가 발생했습니다.");
    }
  };

  // {/* 상품 대표 이미지 팝업 위치 조정 */}
  // const handleHoverRepresentImage = (image, event) => {
  //   const imageRect = event.currentTarget.getBoundingClientRect();
  //   setHoveredImage(image.preview);
  //   setPopupPosition({
  //     x: imageRect.left + 250,
  //     y: imageRect.top + 180,
  //   });
  // };

  // {/* 상품 추가 이미지 팝업 위치 조정 */}
  // const handleHoverImage = (image, event) => {
  //   const imageRect = event.currentTarget.getBoundingClientRect();
  //   setHoveredImage(image.preview);
  //   setPopupPosition({
  //     x: imageRect.left + 250,
  //     y: imageRect.top,
  //   });
  // };

  // {/* 상품 이미지 팝업 상호작용 */}
  // const handleMouseLeave = () => {
  //   setHoveredImage(null);
  // };

  {
    /* 상품 등록 버튼 */
  }
  // const handleSubmit = (e) => {
  //   e.preventDefault();

  //   // TOAST UI Editor에서 Markdown 데이터 가져오기
  //   const editorInstance = editorRef.current.getInstance();
  //   const markdownContent = editorInstance.getMarkdown();
  //   alert("상품이 등록되었습니다!");
  // };

  return (
    <UploadLayout>
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
                  // onMouseMove={(e) => handleHoverRepresentImage(representImage, e)}
                  // onMouseLeave={handleMouseLeave}
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
                  // onMouseMove={(e) => handleHoverImage(image, e)}
                  // onMouseLeave={handleMouseLeave}
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

          {/* 상품 카테고리 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              상품 종류
            </label>
            <select
              value={productCategory}
              onChange={(e) => setProductCategory(e.target.value)}
              className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              style={{
                position: "relative",
                zIndex: 10,
                maxHeight: "50px", // 드롭다운의 최대 높이 설정
                overflowY: "auto", // 스크롤 활성화
              }}
            >
              <option value="" disabled>
                상품을 선택해주세요.
              </option>
              {Object.entries(productEnum).map(([key, { description }]) => (
                <option key={key} value={key}>
                  {description}
                </option>
              ))}
            </select>
          </div>

          {/* 사람 카테고리 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              성별
            </label>
            <select
              value={personCategory}
              onChange={(e) => setPersonCategory(e.target.value)}
              className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              style={{
                position: "relative", // select 요소의 상대적 위치 지정
                zIndex: 10, // 드롭다운 메뉴가 다른 요소들에 가려지지 않도록 설정
              }}
            >
              <option value="" disabled>
                성별을 선택해주세요.
              </option>
              <option value="ALL_PERSON">남여공용</option>
              <option value="MEN">남성</option>
              <option value="WOMEN">여성</option>
              <option value="CHILDREN">아동</option>
            </select>
          </div>

          {/* 계절 카테고리 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              계절
            </label>
            <select
              value={seasonCategory}
              onChange={(e) => setSeasonCategory(e.target.value)}
              className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              style={{
                position: "relative", // select 요소의 상대적 위치 지정
                zIndex: 10, // 드롭다운 메뉴가 다른 요소들에 가려지지 않도록 설정
              }}
            >
              <option value="" disabled>
                계절을 선택해주세요.
              </option>
              <option value="SUMMER">여름</option>
              <option value="WINTER">겨울</option>
              <option value="ALL_SEASON">모든계절</option>
            </select>
          </div>

          <div className="w-full max-w-4xl mx-auto p-4">
            <label className="block text-sm font-medium text-gray-700 mb-4">
              신발 사이즈 및 수량
            </label>

            {/* Size Range Controls - Responsive Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mb-6">
              <div className="w-full">
                <label className="block text-sm text-gray-700 mb-2">
                  최소 사이즈
                </label>
                <input
                  type="number"
                  name="min"
                  value={sizeRange.min}
                  onChange={handleSizeRangeChange}
                  className="w-full text-sm px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                  placeholder="최소"
                  min={0}
                />
              </div>

              <div className="w-full">
                <label className="block text-sm text-gray-700 mb-2">
                  최대 사이즈
                </label>
                <input
                  type="number"
                  name="max"
                  value={sizeRange.max}
                  onChange={handleSizeRangeChange}
                  className="w-full text-sm px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                  placeholder="최대"
                  min={0}
                />
              </div>

              <div className="w-full">
                <label className="block text-sm text-gray-700 mb-2">
                  사이즈 간격
                </label>
                <select
                  value={sizeInterval}
                  onChange={handleSizeIntervalChange}
                  className="w-full text-sm px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                >
                  <option value="" disabled>
                    선택
                  </option>
                  <option value={5}>5</option>
                  <option value={10}>10</option>
                </select>
              </div>
            </div>

            {/* Error Messages */}
            {sizeInterval &&
              !sizeRange.min &&
              !sizeRange.max &&
              !sizeRange.errorMessage && (
                <p className="text-red-500 text-sm mb-4">
                  최소 사이즈와 최대 사이즈를 입력해주세요.
                </p>
              )}

            {sizeInterval &&
              sizeRange.min &&
              sizeRange.max &&
              (sizeRange.min > sizeRange.max ||
                sizeRange.min % sizeInterval !== 0 ||
                sizeRange.max % sizeInterval !== 0) &&
              !sizeRange.errorMessage && (
                <p className="text-red-500 text-sm mb-4">
                  정확한 사이즈 정보를 입력해주세요.
                </p>
              )}

            {/* Size Quantity Inputs - Responsive Grid */}
            {/* Size Quantity Inputs - Responsive Grid */}
            {sizeInterval &&
              sizeRange.min &&
              sizeRange.max &&
              sizeRange.min % sizeInterval === 0 &&
              sizeRange.max % sizeInterval === 0 &&
              sizeRange.min <= sizeRange.max && (
                <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                  {renderSizeOptions().map((size) => (
                    <div
                      key={size}
                      className="flex items-center justify-between p-3 bg-gray-50 rounded-lg"
                    >
                      <label className="text-sm text-gray-700 flex-shrink-0">
                        {size} 사이즈
                      </label>
                      <div className="flex-grow mx-2" /> {/* Spacer */}
                      <input
                        type="number"
                        value={sizeQuantities[size] || ""}
                        onChange={(e) => handleQuantityChange(size, e)}
                        placeholder="수량"
                        min={0}
                        className="w-20 text-sm px-3 py-1.5 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                      />
                    </div>
                  ))}
                </div>
              )}
          </div>

          {/* 상품 설명란 */}
          <div>
            <label
              className="block text-sm font-medium mb-2"
              htmlFor="description"
            >
              상품 설명
            </label>
            <Editor
              ref={editorRef}
              initialValue={description}
              previewStyle="vertical"
              height="400px"
              initialEditType="wysiwyg"
              useCommandShortcut={true}
              plugins={[colorSyntax]}
              onChange={() => {
                const editorInstance = editorRef.current.getInstance();
                setDescription(editorInstance.getMarkdown());
              }}
            />
          </div>

          {/* 상품 가격 */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              상품 가격
            </label>
            <div className="relative">
              <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">
                ₩
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
              onClick={handleSubmit}
              type="submit"
              className="px-6 py-2 bg-blue-500 text-white rounded-lg shadow-sm hover:bg-blue-600"
            >
              등록
            </button>
          </div>
        </form>

        {/* 이미지 팝업 */}
        {/* {hoveredImage && (
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
                width: "300px",
                height: "auto",
                objectFit: "cover",
              }}
            />
          </div>
        )} */}
      </div>
    </UploadLayout>
  );
}

export default ProductUploadPage;

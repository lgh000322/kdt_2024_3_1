import React, { useState, useRef, useEffect } from "react";

import UploadLayout from "../layouts/UploadLayout";

import { useSelector } from "react-redux";
import useCustomMove from "../hook/useCustomMove";
import { useParams } from "react-router-dom";
import { getProductDetails, updateProduct } from "../api/productApi";
import { X } from "lucide-react";



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

function ProductUpdatePage() {

  const loginSlice = useSelector((state) => state.loginSlice);

  const { moveToProductOne } = useCustomMove();

  const { productId } = useParams(); // URL에서 productId 가져오기

  const [productName, setProductName] = useState("");
  const [productCategory, setProductCategory] = useState("");
  const [personCategory, setPersonCategory] = useState("");
  const [seasonCategory, setSeasonCategory] = useState("");
  const [description, setDescription] = useState("");
  const [productPrice, setProductPrice] = useState("");
  const fileInputRef = useRef(null);
  const multiFileInputRef = useRef(null);

  // 업로드 상품 이미지 정보
  const [representImage, setRepresentImage] = useState(null);
  const [images, setImages] = useState([]);

  // 삭제된 이미지 정보를 저장할 상태
  const [deletedMainImgInforms, setDeletedMainImgInforms] = useState(null);
  const [deletedSideImgInforms, setDeletedSideImgInforms] = useState([]);
  

  // 서버로 부터 받은 사이즈와 사이즈별수량.
  const [sizeQuantities, setSizeQuantities] = useState([]);




  useEffect(() => {
    

    const fetchProductDetails = async () => {

    
      try {
        const accessToken = loginSlice.accessToken;
        // console.log("Access Token:", accessToken); // accessToken 값 확인
        console.log("productId: ", productId);
        const res = await getProductDetails(productId, accessToken);
  
        console.log("API 응답:", res); // API 응답 확인
  
        if (res.code === 200) {
          const data = res.data;
  
          console.log("받아온 데이터" , data);

          // 기본 정보 설정
          setProductName(data.productName);
          setProductCategory(data.productCategory);
          setPersonCategory(data.personCategory);
          setSeasonCategory(data.seasonCategory);
          setDescription(data.description);
          setProductPrice(data.price);
          
          

          console.log("mainImgId", data.mainImgId);
          // 대표 이미지 설정
          setRepresentImage({
            preview: data.mainImgUrl,
            // isExisting: true,
            imgId: data.mainImgId,
          });
  
          // 추가 이미지 설정
          const formattedImages = data.sideImgInforms.map((img) => ({
            preview: img.imgUrl,
           // isExisting: true,
            imgId: img.imgId,
          }));
          setImages(formattedImages);
  
         // Add this part to handle size quantities
          if (data.productDetailsList) {
            setSizeQuantities(
              data.productDetailsList.map(detail => ({
                productDetailId: detail.productDetailsId,
                size: detail.size,
                quantityBySize: detail.quantityBySize
              }))
            );
          }

        } else {
          alert("상품 정보를 불러오는데 실패했습니다.");
        }
      } catch (err) {
        console.error(err);
        alert("상품 정보를 불러오는데 오류가 발생했습니다.");
      }
    };
  
    if (productId) {
      fetchProductDetails();
    }
  }, [productId]);


  // 대표이미지 업로드
  const handleRepresentImageUpload = (e) => {
    const file = e.target.files[0];

    if (file) {
      // 이전 이미지에 fileId가 있다면 삭제 정보 저장
      if (representImage && representImage.imgId) {
        const imgInforms = {
          imgId: representImage.imgId, // 이전 이미지 ID
          imgUrl: representImage.preview // 이전 이미지 URL
        };
        setDeletedMainImgInforms(imgInforms); // 삭제할 이미지 정보 저장
      }
  
      // 새로운 이미지 상태 업데이트
      setRepresentImage({
        file,
        preview: URL.createObjectURL(file), // 새로 업로드된 이미지의 preview URL
      });
    }
  };

    // 사이드 이미지 업로드
    const handleImageUpload = (e) => {
      const files = Array.from(e.target.files).map((file) => ({
        file,
        preview: URL.createObjectURL(file),
      }));
      setImages((prevImages) => [...prevImages, ...files]);
    };


    // 대표 이미지 삭제 핸들러
  const handleRemoveRepresentImage = () => {
    console.log("이미지 제거");
    console.log('대표이미지정보', representImage.id, representImage.preview)
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
    console.log("mainImgId", representImage.imgId);
  

    if (representImage && representImage.preview) {
      // 새로 업로드한 이미지라면 ID가 없을 수 있음
      const imgInforms = {
        imgId: representImage.imgId || null,  // ID가 없다면 null을 설정
        imgUrl: representImage.preview,
      };
  
      // imgId가 null이 아니면 setDeletedMainImgInforms를 실행 -> 서버로 부터 들어온 정보일경우 삭제 정보에 추가
      if (imgInforms.imgId) {
      
        setDeletedMainImgInforms(imgInforms);
        
      }
    }
  
    setRepresentImage(null);
  };



    // 상품 이미지 삭제 핸들러
  const handleRemoveImage = (index) => {
    setImages((prevImages) => {
      const updatedImages = prevImages.filter((_, i) => i !== index);
  
      if (prevImages[index].preview) {
        const imgInforms = {
          imgId: prevImages[index].imgId,
          imgUrl: prevImages[index].preview,
        };
  
        // imgId가 null이 아닐 때만 setDeletedSideImgInforms 호출 -> 서버로 부터 들어온 정보일경우 삭제 정보에 추가
        if (imgInforms.imgId) {
          setDeletedSideImgInforms((prevSideImgs) => [
            ...prevSideImgs,
            imgInforms,
          ]);
        }
      }
  
      return updatedImages;
    });
  };



  // 수량 변경 핸들러
  const handleQuantityChange = (productDetailId, value) => {
  
      // 사용자가 입력한 값이 숫자가 아니면 0으로 처리
    const newValue = isNaN(value) || value === "" ? 0 : Math.max(0, parseInt(value));

    // 기존 sizeQuantities에서 해당 productDetailsId를 찾아 업데이트
    setSizeQuantities((prev) =>
      prev.map((item) =>
        item.productDetailId === productDetailId
          ? { ...item, quantityBySize: newValue }
          : item
      )
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();


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


    let accessToken = loginSlice.accessToken;

    try {

      
      // FormData 객체 생성
      const formData = new FormData();


      console.log("productName", productName);
      console.log("deletedMainImgInforms", deletedMainImgInforms);
      console.log("deletedSideImgInforms", deletedSideImgInforms);
      console.log("productCategory", productCategory);
      console.log("personCategory", personCategory);
      console.log("seasonCategory", seasonCategory);
      console.log("description", description);

      const updateProductReq = {
        productName: productName,
        deletedMainImgInforms: deletedMainImgInforms,
        deletedSideImgInforms: deletedSideImgInforms,
        productCategory: productCategory,
        personCategory: personCategory,
        seasonCategory: seasonCategory,
        description: description,
        price: productPrice,
        updateProductDetailsInforms: sizeQuantities,
      };
  
      //대표이미지에 아이디가 없으면 추가
      if (representImage?.file && !representImage.imgId) {
        formData.append("updateMainImg", representImage.file);
      }

      //사이드 이미지에 아이디가 없으면 추가
      images.forEach((image) => {
    
        if (image.file && !image.imgId) {
          formData.append("updateSideImgs", image.file);
        }
      });



      formData.append(
        "updateProductReq",
        new Blob([JSON.stringify(updateProductReq)], {
          type: "application/json",
        })
      );

      console.log("dfasdf", updateProductReq);
      

      updateProduct(productId, formData, accessToken).then((res) => {

        console.log("productId", productId);

              if (res.code === 200) {
                moveToProductOne(productId);
              } else {
                alert("상품등록에 실패했습니다.");
              }
      });
    } catch (error) {
      console.error("Failed to update product:", error);
      // Handle error (show message to user)
    }
  };

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
                    <div className="mt-4 relative w-60 h-auto">
                      <img
                        src={representImage.preview}
                        alt="대표 이미지"
                        className="w-full h-auto object-cover rounded-lg"
                      />
                      <button
                        type="button"
                        onClick={handleRemoveRepresentImage}
                        className="absolute top-2 right-2 bg-red-500 hover:bg-red-600 text-white w-6 h-6 flex items-center justify-center rounded-full transition-colors"
                      >
                        <X size={16} />
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
                      <div key={index} className="relative">
                        <img
                          src={image.preview}
                          alt={`uploaded-${index}`}
                          className="w-full h-auto object-cover rounded-lg"
                        />
                        <button
                          type="button"
                          onClick={() => handleRemoveImage(index)}
                          className="absolute top-2 right-2 bg-red-500 hover:bg-red-600 text-white w-6 h-6 flex items-center justify-center rounded-full transition-colors"
                        >
                          <X size={16} />
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
          <div className="bg-white rounded-lg shadow overflow-hidden">
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      사이즈
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      수량
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {sizeQuantities.map((item) => (
                    <tr key={item.productDetailId}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {item.size}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <input
                          type="number"
                          min="0"
                          value={item.quantityBySize}
                          onChange={(e) => handleQuantityChange(item.productDetailId, e.target.value)}
                          className="w-24 px-3 py-2 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                        />
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>

        {/* Toast UI Editor를 textarea로 대체 */}
        <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              상품 설명
            </label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="상품에 대한 설명을 입력하세요"
              className="block w-full px-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              rows={10}
              style={{ resize: 'vertical' }}
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
            수정
          </button>
        </div>
      </form>


    </div>
  </UploadLayout>
  );
}

export default ProductUpdatePage;

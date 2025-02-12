import axios from "axios";
import { ApiHost } from "./ApiConst";

const basicProductUrl = `${ApiHost}/product`;
const defaultProductsUrl = `${ApiHost}/products`;




// 상품 업데이트요청
export const updateProduct = async (productId, updateProductInforms, accessToken) => {


  if (!accessToken) {
    throw new Error("Access Token is required for authorization.");
  }

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "multipart/form-data",
    },
    withCredentials: true,
  };

  console.log(productId);

  console.log("서버에 보낼 데이터: ", updateProductInforms);


  try {
    
    const res = await axios.put(
      `${basicProductUrl}/${productId}`,
      updateProductInforms,
      config
    );
    return res.data;

  } catch (error) {

    console.error("상품 등록 오류:", error.response || error.message);
    throw error;
  
  }
}

// 상품상세조회
export const getProductDetails = async (productId, accessToken) => {

  console.log("accessToken : ", accessToken);

  if (!accessToken) {
    throw new Error("Access Token is required for authorization.");
  }


  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    withCredentials: true,
  };

  
  console.log("Request Headers: ", config);
  const res = await axios.get(`${defaultProductsUrl}/${productId}`, config);

  return res.data; // API에서 받은 데이터를 반환
};


// 판매자 상품등록 요청
export const registerProduct = async (addProductInforms, accessToken) => {
  if (!accessToken) {
    throw new Error("Access Token is required for authorization.");
  }

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "multipart/form-data",
    },
    withCredentials: true,
  };

  console.log("서버에 보낼 데이터: ", addProductInforms);

  try {
    const res = await axios.post(
      `${basicProductUrl}`,
      addProductInforms,
      config
    );
    return res.data;
  } catch (error) {
    console.error("상품 등록 오류:", error.response || error.message);
    throw error;
  }
};

// 전체 상품 조회
export const getProductList = async (
  page,
  size,
  seasonCategory,
  personCategory,
  productCategory,
  sortingOption,
  search
) => {
  const params = new URLSearchParams();

  // null이나 undefined 체크하여 쿼리 파라미터로 추가
  if (page !== null && page !== undefined) params.append("page", page);
  if (size !== null && size !== undefined) params.append("size", size);
  if (seasonCategory !== null && seasonCategory !== undefined)
    params.append("seasonCategory", seasonCategory);
  if (personCategory !== null && personCategory !== undefined)
    params.append("personCategory", personCategory);
  if (productCategory !== null && productCategory !== undefined)
    params.append("productCategory", productCategory);
  if (sortingOption !== null && sortingOption !== undefined)
    params.append("sortingOption", sortingOption);
  if (search !== null && search !== undefined) params.append("search", search);

  const queryString = params.toString(); // URLSearchParams를 문자열로 변환

  const res = await axios.get(`${defaultProductsUrl}?${queryString}`);
  return res.data;
};

// 특정 상품 조회
export const getProductOne = async (productId) => {
  const res = await axios.get(`${defaultProductsUrl}/${productId}`);
  return res.data;
};

// 판매자가 등록한 상품 조회
export const getSellerProductList = async (accessToken, page, size) => {
  const header = {
    headers: { Authorization: `Bearer ${accessToken}` },
    withCredentials: true,
  };

  const res = await axios.get(
    `${basicProductUrl}/seller?page=${page}&size=${size}`,
    header
  );
  return res.data;
};

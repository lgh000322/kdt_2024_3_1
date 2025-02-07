import axios from "axios";
import { ApiHost } from "./ApiConst";

const basicProductUrl = `${ApiHost}/product`;

    // 판매자 상품등록 요청
    export const registerProduct = async (addProductInforms, accessToken) => {
        if (!accessToken) {
            throw new Error("Access Token is required for authorization.");
          }
        
          const config = {
            headers: { 
              Authorization: `Bearer ${accessToken}`,
              'Content-Type': 'multipart/form-data', 
             },
            withCredentials: true,
          };
        
          console.log("서버에 보낼 데이터: ",addProductInforms);
          
          try {
            const res = await axios.post(`${basicProductUrl}`, addProductInforms, config);
            return res.data;
          } catch (error) {
            console.error("상품 등록 오류:", error.response || error.message);
            throw error;
         }
    };

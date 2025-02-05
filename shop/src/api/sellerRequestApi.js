import axios from "axios";
import { ApiHost } from "./ApiConst";

const preFix = `${ApiHost}/authority`;

export const requestSellerAuthroity = async (files, requestData, token) =>{
        // FormData 생성
        const formData = new FormData();

        // 파일 추가
        files.forEach((file) => {
            formData.append('file', file); // 'file' 키로 여러 개의 파일 추가
        });

        // JSON 데이터를 Blob으로 변환 후 추가
        formData.append(
            'data',
            new Blob([JSON.stringify(requestData)], { type: 'application/json' })
        );

        // 요청 보내기
        const res = await axios.post(`${preFix}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
                Authorization: `Bearer ${token}`, // JWT 토큰 (필요하면 추가)
            },
        });

        return res.data;

}

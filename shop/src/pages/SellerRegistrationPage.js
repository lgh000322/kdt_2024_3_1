import React, { useState } from "react";
import BasicLayout from "../layouts/BasicLayout";
import { useSelector } from "react-redux";
import { requestSellerAuthroity } from "../api/sellerRequestApi";

function SellerRequestPage() {
  const [selectedFiles, setSelectedFiles] = useState([]); // 파일 리스트 상태
  const [reason, setReason] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false);
  const loginState = useSelector((state)=>state.loginSlice)

   // 파일 선택 시 상태 업데이트
   const handleFileChange = (event) => {
    const files = Array.from(event.target.files); // FileList → 배열 변환
    setSelectedFiles(files);
  };

  const handleReasonChange = (event) => {
    setReason(event.target.value);
  };

  const handleSubmit = async () => {
    if (!reason || !selectedFiles) {
      alert('판매자 등록 사유와 첨부파일을 모두 입력해주세요.');
      return;
    }

    try {
      const accessToken = loginState.accessToken;
      const content={
        "content":reason
      };
      // API 호출
      requestSellerAuthroity(selectedFiles,content,accessToken).then(res=>{
        if(res.code === 200){
          alert("성공");
        }
      })

      alert('제출이 완료되었습니다.');
      setIsSubmitted(true); // 제출 완료 상태 변경
    } catch (error) {
      console.error('판매자 권한 신청 중 오류 발생:', error);
      alert(`제출 실패: ${error.response?.data?.message || '알 수 없는 오류'}`);
    }
  };

  return (
    <BasicLayout>
      <div className="max-w-7xl mx-auto px-4 pt-12">
        <div className="border rounded-lg shadow-lg p-6 bg-white">
          <h2 className="text-xl font-bold mb-4 text-center">
            판매자 등록 신청{" "}
          </h2>
          <div className="mb-4 pt-5">
            <label className="block text-sm font-medium mb-1" htmlFor="reason">
              판매자 등록 사유
            </label>
            <input
              type="text"
              id="reason"
              placeholder="사유를 입력하세요"
              value={reason}
              onChange={handleReasonChange}
              disabled={isSubmitted}
              className={`w-full border px-3 py-2 rounded-lg shadow-sm focus:outline-none focus:ring ${
                isSubmitted ? 'bg-gray-200 cursor-not-allowed' : 'focus:ring-green-300'
              }`}
            />
          </div>
          <div className="mb-4 pt-5">
            <label
              className="block text-sm font-medium mb-1"
              htmlFor="fileUpload"
            >
              제출서류
            </label>
            <input
              type="file"
              multiple
              id="fileUpload"
              accept=".pdf,.hwp,.doc,.docx"
              onChange={handleFileChange}
              disabled={isSubmitted}
              className={`w-full border px-3 py-2 rounded-lg shadow-sm focus:outline-none focus:ring ${
                isSubmitted ? 'bg-gray-200 cursor-not-allowed' : 'focus:ring-green-300'
              }`}
            />
            {selectedFiles.map((file, index) => (
               <li key={index}>{file.name}</li>
            ))}
          </div>
          <h3 className="text-gray-500 text-opacity-30">
            *제출서류에 (사업자 등록증, 신분증 사본, 통장 사본) 이미지 또는 PDF 문서로 업로드 바랍니다.
          </h3>
          <hr></hr>
          <button
            type="button"
            onClick={handleSubmit}
            disabled={isSubmitted}
            className={`w-full font-semibold py-2 rounded-lg ${
              isSubmitted ? 'bg-gray-400 cursor-not-allowed' : 'bg-green-500 hover:bg-green-600 text-white'
            }`}
          >
            {isSubmitted ? '제출 완료' : '제출하기'}
          </button>
        </div>
      </div>
    </BasicLayout>
  );
}

export default SellerRequestPage;

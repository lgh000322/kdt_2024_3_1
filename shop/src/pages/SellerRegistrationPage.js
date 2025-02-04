import React, { useState } from 'react';
import BasicLayout from '../layouts/BasicLayout';

function SellerRequestPage() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [reason, setReason] = useState(''); // 판매자 등록 사유 상태
  const [isSubmitted, setIsSubmitted] = useState(false); // 제출 여부 상태

  // 파일 변경 핸들러
  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setSelectedFile(file);
  };

  // 판매자 등록 사유 변경 핸들러
  const handleReasonChange = (event) => {
    setReason(event.target.value);
  };

  // 제출 버튼 클릭 핸들러
  const handleSubmit = () => {
    if (!reason || !selectedFile) {
      alert('판매자 등록 사유와 첨부파일을 모두 입력해주세요.');
      return;
    }
    setIsSubmitted(true); // 제출 완료 상태로 변경
    alert('제출이 완료되었습니다.');
  };

  return (
    <BasicLayout role="consumer">
      {/* 상품 그리드 섹션 */}
      <div className="max-w-7xl mx-auto px-4 pt-12">
        {/* 중앙 본문 세션 */}
        <div className="border rounded-lg shadow-lg p-6 bg-white">
          <h2 className="text-xl font-bold mb-4 text-center">판매자 등록 신청 </h2>
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
              disabled={isSubmitted} // 제출 후 비활성화
              className={`w-full border px-3 py-2 rounded-lg shadow-sm focus:outline-none focus:ring ${
                isSubmitted ? 'bg-gray-200 cursor-not-allowed' : 'focus:ring-green-300'
              }`}
            />
          </div>
          <div className="mb-4 pt-5">
            <label className="block text-sm font-medium mb-1" htmlFor="fileUpload">
              제출서류
            </label>
            <input
              type="file"
              id="fileUpload"
              accept=".pdf,.hwp,.doc,.docx"
              onChange={handleFileChange}
              disabled={isSubmitted} // 제출 후 비활성화
              className={`w-full border px-3 py-2 rounded-lg shadow-sm focus:outline-none focus:ring ${
                isSubmitted ? 'bg-gray-200 cursor-not-allowed' : 'focus:ring-green-300'
              }`}
            />
            {selectedFile && (
              <p className="mt-2 text-sm text-gray-500">
                첨부된 파일: {selectedFile.name}
              </p>
            )}
          </div>
          <hr />
          <h3 className="text-gray-500 text-opacity-30">
            *제출서류에 (사업자 등록증, 신분증 사본, 통장 사본) 이미지 또는 PDF 문서로 업로드 바랍니다.
          </h3>

          <button
            type="button"
            onClick={handleSubmit}
            disabled={isSubmitted} // 제출 후 버튼 비활성화
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
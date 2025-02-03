import React, { useState } from "react";
import BasicLayout from "../layouts/BasicLayout";

function SellerRequestPage() {
  const [selectedFile, setSelectedFile] = useState(null);

  // 파일 변경 핸들러
  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setSelectedFile(file);
  };

  return (
    <BasicLayout>
      {/* 상품 그리드 섹션 */}
      <div className="max-w-7xl mx-auto px-4 pt-12">
        {/* 중앙 본문 세션 */}
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
              className="w-full border px-3 py-2 rounded-lg shadow-sm focus:outline-none focus:ring focus:ring-green-300"
            />
          </div>
          <div></div>
          <div className="mb-4 pt-5">
            <label
              className="block text-sm font-medium mb-1"
              htmlFor="fileUpload"
            >
              제출서류
            </label>
            <input
              type="file"
              id="fileUpload"
              accept=".pdf,.hwp,.doc,.docx"
              className="w-full border px-3 py-2 rounded-lg shadow-sm focus:outline-none focus:ring focus:ring-green-300"
              onChange={handleFileChange}
            />
            {selectedFile && (
              <p className="mt-2 text-sm text-gray-500">
                첨부된 파일: {selectedFile.name}
              </p>
            )}
          </div>
          <hr></hr>
          <h3 className="text-gray-500 text-opacity-30">
            *제출서류에 (사업자 등록증, 신분증 사본, 통장 사본) 이미지 또는 PDF
            문서로 업로드 바랍니다.
          </h3>

          <button
            type="button"
            className="w-full bg-green-500 text-white font-semibold py-2 rounded-lg hover:bg-green-600"
          >
            제출하기
          </button>
        </div>
      </div>
    </BasicLayout>
  );
}

export default SellerRequestPage;

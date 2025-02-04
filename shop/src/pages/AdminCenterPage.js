import React from "react";
import BasicLayout from "../layouts/BasicLayout";

function AdminCenterPage() {
  return (
    <BasicLayout>
      {/* 전체 컨테이너 */}
      <div className="max-w-7xl mx-auto px-4 pt-12">
        {/* 본문 영역 */}
        <div className="space-y-6">
          {/* 판매/배송 섹션 */}
          <div className="bg-gray-100 p-5 rounded-lg shadow-md">
            <h2 className="text-lg font-bold mb-1">판매/배송</h2>
            <p className="text-sm text-gray-600 mb-10">최근 14일 기준</p>
            <div className="flex justify-evenly items-center text-center mb-16">
              {["결제완료", "상품준비중", "배송지시", "배송중", "배송완료"].map(
                (item, index) => (
                  <React.Fragment key={index}>
                    <div className="flex flex-col items-center">
                      <p className="text-3xl font-bold mb-2">0</p>
                      <p className="text-sm">{item}</p>
                    </div>
                    {/* '>' 기호 추가 (마지막 항목 제외) */}
                    {index !== 4 && (
                      <div className="text-gray-400 text-xl font-bold mx-2">
                        {">"}
                      </div>
                    )}
                  </React.Fragment>
                )
              )}
            </div>
          </div>

          {/* 하단 영역: 취소/반품/교환 및 미답변문의 */}
          <div className="grid grid-cols-2 gap-4">
            {/* 취소/반품/교환 섹션 */}
            <div className="bg-gray-100 p-6 rounded-lg shadow-md">
              <h2 className="text-lg font-bold mb-1">취소/반품/교환</h2>
              <p className="text-sm text-gray-600 mb-4">최근 30일 기준</p>
              <div className="space-y-2">
                {["출고중지요청", "반품접수", "교환접수"].map((item, index) => (
                  <div
                    key={index}
                    className="flex justify-between items-center"
                  >
                    <p className="text-sm text-gray-600">{item}</p>
                    <div className="flex items-center space-x-1">
                      <p className="text-xl font-bold">0</p>
                      <span className="text-gray-400">{">"}</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* 미답변문의 섹션 */}
            <div className="bg-gray-100 p-6 rounded-lg shadow-md">
              <h2 className="text-lg font-bold mb-1">미답변문의</h2>
              <p className="text-sm text-gray-600 mb-4">최근 30일 기준</p>
              <div className="space-y-2">
                {["고객센터문의", "고객문의"].map((item, index) => (
                  <div
                    key={index}
                    className="flex justify-between items-center"
                  >
                    <p className="text-sm text-gray-600">{item}</p>
                    <div className="flex items-center space-x-1">
                      <p className="text-xl font-bold">0</p>
                      <span className="text-gray-400">{">"}</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </BasicLayout>
  );
}

export default AdminCenterPage;

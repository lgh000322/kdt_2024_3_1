import React, { useState, useEffect, useRef } from "react";
import AdminPageLayout from "../layouts/AdminPageLayout";

const userRole = "manager";

function AdminBannerPage() {
  const [banners, setBanners] = useState([
    { id: 1, image: null },
    { id: 2, image: null },
    { id: 3, image: null },
  ]);
  
  const fileInputRefs = useRef([]);

  useEffect(() => {
    fileInputRefs.current = fileInputRefs.current.slice(0, banners.length);
  }, [banners]);

  const handleUploadClick = (index) => {
    fileInputRefs.current[index].click();
  };

  const handleFileUpload = (e, id) => {
    const file = e.target.files[0];
    if (file) {
      if (!file.type.startsWith('image/')) {
        alert('이미지 파일만 업로드 가능합니다.');
        return;
      }

      setBanners((prevBanners) =>
        prevBanners.map((banner) =>
          banner.id === id ? { ...banner, image: URL.createObjectURL(file) } : banner
        )
      );
    }
  };

  const handleFileDelete = (id, index) => {
    if (fileInputRefs.current[index]) {
      fileInputRefs.current[index].value = '';
    }
    
    setBanners((prevBanners) =>
      prevBanners.map((banner) =>
        banner.id === id ? { ...banner, image: null } : banner
      )
    );
  };

  const handleSubmit = () => {
    console.log("Changes submitted:", banners);
  };

  return (
    <AdminPageLayout role={userRole}>
      <div className="min-h-screen bg-gray-100">
        <div className="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
          <div className="bg-white rounded-lg shadow-lg overflow-hidden">
            <div className="px-6 py-4 border-b border-gray-200">
              <h2 className="text-2xl font-bold text-gray-800">배너 관리</h2>
            </div>
            
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">번호</th>
                  <th className="px-6 py-4 text-left text-sm font-medium text-gray-500 uppercase tracking-wider">배너 사진</th>
                  <th className="px-6 py-4 text-center text-sm font-medium text-gray-500 uppercase tracking-wider">파일 업로드</th>
                  <th className="px-6 py-4 text-center text-sm font-medium text-gray-500 uppercase tracking-wider">파일 삭제</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {banners.map((banner, index) => (
                  <tr key={banner.id} className="hover:bg-gray-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{banner.id}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {banner.image ? (
                        <div className="flex items-center">
                          <img
                            src={banner.image}
                            alt={`배너 ${banner.id}`}
                            className="w-32 h-20 object-cover rounded-md shadow-sm"
                          />
                        </div>
                      ) : (
                        <span className="text-sm text-gray-500">이미지 없음</span>
                      )}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-center">
                      <input
                        type="file"
                        ref={el => fileInputRefs.current[index] = el}
                        className="hidden"
                        onChange={(e) => handleFileUpload(e, banner.id)}
                        accept="image/*"
                      />
                      <button 
                        onClick={() => handleUploadClick(index)}
                        className="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-all"
                      >
                        업로드
                      </button>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-center">
                      <button
                        onClick={() => handleFileDelete(banner.id, index)}
                        className="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition-all"
                      >
                        삭제
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          <div className="mt-6 flex justify-end">
            <button
              onClick={handleSubmit}
              className="inline-flex items-center px-6 py-3 border border-transparent rounded-md shadow-sm text-base font-medium text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 transition-all"
            >
              수정
            </button>
          </div>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminBannerPage;

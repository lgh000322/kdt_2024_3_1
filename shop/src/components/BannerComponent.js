import React, { useState, useEffect } from "react";

function BannerComponent({ bannerList }) {
  const [currentSlide, setCurrentSlide] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      nextSlide(); // 일정 시간마다 다음 슬라이드로 이동
    }, 4000); // 4초 간격

    return () => clearInterval(timer); // 컴포넌트 언마운트 시 타이머 정리
  }, [bannerList]); // bannerList에 의존하여 타이머가 변경되도록 처리

  // 다음 슬라이드로 이동
  const nextSlide = () => {
    setCurrentSlide((prev) => (prev === bannerList.length - 1 ? 0 : prev + 1));
  };

  // 이전 슬라이드로 이동
  const prevSlide = () => {
    setCurrentSlide((prev) => (prev === 0 ? bannerList.length - 1 : prev - 1));
  };

  // 특정 슬라이드로 직접 이동
  const goToSlide = (index) => {
    setCurrentSlide(index);
  };

  return (
    <div className="relative w-full overflow-hidden h-[400px] pt-13">
      {/* 배너 슬라이드 */}
      <div
        className="flex transition-transform duration-500 ease-in-out h-full"
        style={{ transform: `translateX(-${currentSlide * 100}%)` }}
      >
        {bannerList.map((banner, index) => (
          <div
            key={banner.bannerId || index}
            className="min-w-full h-full relative"
          >
            <img
              src={banner.imageUrl}
              className="w-full h-full object-contain"
              alt={`Slide ${index + 1}`}
            />
          </div>
        ))}
      </div>

      {/* 이전/다음 버튼 */}
      <button
        onClick={prevSlide}
        className="absolute left-4 top-1/2 transform -translate-y-1/2 bg-black/50 text-white p-2 rounded-full hover:bg-black/70"
      >
        &#10094;
      </button>
      <button
        onClick={nextSlide}
        className="absolute right-4 top-1/2 transform -translate-y-1/2 bg-black/50 text-white p-2 rounded-full hover:bg-black/70"
      >
        &#10095;
      </button>

      {/* 하단 인디케이터 */}
      <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2">
        {bannerList.map((_, index) => (
          <button
            key={index}
            onClick={() => goToSlide(index)}
            className={`w-3 h-3 rounded-full transition-colors ${
              currentSlide === index ? "bg-black" : "bg-black/50"
            }`}
          />
        ))}
      </div>
    </div>
  );
}

export default BannerComponent;

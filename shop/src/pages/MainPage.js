import React, { useEffect, useState, useRef, useCallback } from "react";
import ProductLayout from "../layouts/ProductLayout";
import ProductCardComponent from "../components/ProductCardComponent";
import { getProductList } from "../api/productApi";
import { useSearchParams } from "react-router-dom";

function MainPage() {
  const [products, setProduct] = useState([]);
    const [loading, setLoading] = useState(false);
    const [noMoreProducts, setNoMoreProducts] = useState(false);
    const [searchParams, setSearchParams] = useSearchParams();
    const loadingRef = useRef(false);
    const scrollPositionRef = useRef(0);
    
    const fetchProducts = useCallback(async (page, query) => {
      if (loadingRef.current) return;
      
      loadingRef.current = true;
      setLoading(true);
      
      try {
        // 현재 스크롤 위치 저장
        scrollPositionRef.current = window.scrollY;
        
        const res = await getProductList(page, 10, null, null, null, null, query);
        const data = res.data;
        
        if (data.length === 0 || data.length < 10) {
          setNoMoreProducts(true);
        }
        
        if (page === "0") {
          setProduct(data);
        } else {
          setProduct(prev => [...prev, ...data]);
          // 데이터 업데이트 후 스크롤 위치 복원
          requestAnimationFrame(() => {
            window.scrollTo(0, scrollPositionRef.current);
          });
        }
      } catch (error) {
        console.error("Failed to fetch products:", error);
      } finally {
        setLoading(false);
        loadingRef.current = false;
      }
    }, []);
  
    // 초기 데이터 로드 및 검색 처리
    useEffect(() => {
      const query = searchParams.get("query");
      const page = searchParams.get("page") || "0";
      
      if (noMoreProducts) return;
      
      // 페이지가 0일 때는 스크롤 위치 초기화
      if (page === "0") {
        window.scrollTo(0, 0);
      }
      
      fetchProducts(page, query);
    }, [searchParams, fetchProducts]);
  
    // 무한 스크롤 처리
    useEffect(() => {
      const handleScroll = () => {
        if (loadingRef.current || noMoreProducts) return;
  
        const scrollHeight = document.documentElement.scrollHeight;
        const scrollTop = window.scrollY;
        const clientHeight = window.innerHeight;
  
        if (scrollHeight - scrollTop <= clientHeight + 100) {
          const currentPage = parseInt(searchParams.get("page") || "0");
          const query = searchParams.get("query") || "";
          
          // 스크롤 위치 저장
          scrollPositionRef.current = window.scrollY;
          
          // URL 파라미터 조용히 업데이트
          setSearchParams(
            {
              page: currentPage + 1,
              query: query,
            },
            { replace: true, preventScrollReset: true }
          );
        }
      };
  
      window.addEventListener("scroll", handleScroll, { passive: true });
      return () => window.removeEventListener("scroll", handleScroll);
    }, [noMoreProducts, searchParams, setSearchParams]);
  
    // 컴포넌트 마운트/언마운트 시 스크롤 위치 관리
    useEffect(() => {
      const savedScrollPosition = scrollPositionRef.current;
      
      return () => {
        scrollPositionRef.current = savedScrollPosition;
      };
    }, []);
  

  return (
    <div>
      <ProductLayout
        setSearchParams={setSearchParams}
        setNoMoreProducts={setNoMoreProducts}
      >
        <div className="max-w-7xl mx-auto px-4">
          <h2 className="text-2xl font-bold mb-6">신상품</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {products.map((product) => (
              <div key={product.productId} className="w-full">
                <ProductCardComponent
                  id={product.productId}
                  title={product.productName}
                  price={product.price}
                  image={product.imgUrl}
                />
              </div>
            ))}
            {loading && (
              <div className="col-span-full text-center">
                <div className="spinner">Loading...</div>
              </div>
            )}
          </div>
        </div>
      </ProductLayout>
    </div>
  );
}

export default MainPage;
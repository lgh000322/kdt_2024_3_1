import React, { useEffect, useState, useRef } from "react";
import ProductCardComponent from "../components/ProductCardComponent";
import SubProductLayout from "../layouts/SubProductLayout";
import { getProductList } from "../api/productApi";
import { useSearchParams } from "react-router-dom";

function KidsPage() {
  const [products, setProduct] = useState([]);
  const [loading, setLoading] = useState(false); // 로딩 상태 (중복 API 요청 방지)
  const [noMoreProducts, setNoMoreProducts] = useState(false); // 데이터가 없을 때 호출하는 것을 방지
  const [searchParams, setSearchParams] = useSearchParams();

  const fetchProducts = async (page, query) => {
    if (loading) return;

    setLoading(true);

    try {
      const res = await getProductList(
        page,
        10,
        null,
        "CHILDREN",
        null,
        null,
        query
      );
      const data = res.data;

      if (data.length === 0 || data.length < 10) {
        setNoMoreProducts(true);
      }

      if (page === "0") {
        setProduct(data);
      } else {
        setProduct((prev) => [...prev, ...data]);
      }
    } catch (error) {
      console.error("Failed to fetch products:", error);
    } finally {
      setLoading(false);
    }
  };

  // 초기 데이터 로드 및 검색 처리
  useEffect(() => {
    const query = searchParams.get("query");
    const page = searchParams.get("page") || "0";

    if (noMoreProducts) return;

    fetchProducts(page, query);
  }, [searchParams]);

  // 무한 스크롤 처리
  useEffect(() => {
    const handleScroll = () => {
      if (loading || noMoreProducts) return;

      const scrollHeight = document.documentElement.scrollHeight;
      const scrollTop = window.scrollY;
      const clientHeight = window.innerHeight;

      if (scrollHeight - scrollTop <= clientHeight + 100) {
        const currentPage = parseInt(searchParams.get("page") || "0");
        const query = searchParams.get("query") || "";

        // URL 파라미터 업데이트 대신 직접 다음 페이지 데이터 요청
        fetchProducts(String(currentPage + 1), query);

        // URL 파라미터 조용히 업데이트
        setSearchParams(
          {
            page: currentPage + 1,
            query: query,
          },
          { replace: true, preventScrollReset: true } // replace 옵션을 사용하여 히스토리 스택에 추가하지 않음
        );
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [searchParams]);

  return (
    <SubProductLayout
      setSearchParams={setSearchParams}
      setNoMoreProducts={setNoMoreProducts}
    >
      {/* 그리드 레이아웃 클래스 추가 */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((product) => (
          <div key={product.productId}>
            <ProductCardComponent
              id={product.productId}
              title={product.productName}
              price={product.price}
              image={product.imgUrl}
            />
          </div>
        ))}
        {loading && (
          <div className="col-span-full flex justify-center py-4">
            <div className="spinner">Loading...</div>
          </div>
        )}
      </div>
    </SubProductLayout>
  );
}

export default KidsPage;

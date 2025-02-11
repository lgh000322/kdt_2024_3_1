import React, { useEffect, useState } from "react";
import ProductCardComponent from "../components/ProductCardComponent";
import SubProductLayout from "../layouts/SubProductLayout";
import { getProductList } from "../api/productApi";
import { useSearchParams } from "react-router-dom";

function WinterPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false); // 로딩 상태 (중복 API 요청 방지)
  const [noMoreProducts, setNoMoreProducts] = useState(false); // 데이터가 없을 때 호출하는 것을 방지
  const [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    if (noMoreProducts || loading) return; // 로딩 중일 경우 API 요청 방지

    let query = searchParams.get("query");
    let page = searchParams.get("page");

    setLoading(true);
    if (query) {
      if (page === "0") {
        getProductList(page, 10, "WINTER", null, null, null, query)
          .then((res) => {
            const data = res.data;
            if (data.length === 0) {
              setNoMoreProducts(true);
            }
            setProducts(data); // 새로운 상품을 기존 상품 리스트에 추가
          })
          .finally(() => {
            setLoading(false); // 로딩 완료 후 상태 리셋
          });
      } else {
        getProductList(page, 10, "WINTER", null, null, null, query).then(
          (res) => {
            const data = res.data;
            if (data.length === 0) {
              setNoMoreProducts(true);
            }
            setProducts((prevProducts) => [...prevProducts, ...data]);
          }
        );
      }
    } else {
      getProductList(page, 10, "WINTER", null, null, null, null)
        .then((res) => {
          const data = res.data;
          if (data.length === 0) {
            setNoMoreProducts(true);
          }
          setProducts((prevProducts) => [...prevProducts, ...data]); // 새로운 상품을 기존 상품 리스트에 추가
        })
        .finally(() => {
          setLoading(false); // 로딩 완료 후 상태 리셋
        });
    }
  }, [searchParams]);

  // 스크롤 이벤트를 감지하여 페이지 하단에 도달하면 더 많은 상품을 로드
  useEffect(() => {
    if (loading || noMoreProducts) return;

    const handleScroll = () => {
      const scrollableHeight = document.documentElement.scrollHeight;
      const currentScroll = window.innerHeight + window.scrollY;

      // 페이지 하단에 도달했을 때 더 많은 상품을 불러오기
      if (currentScroll >= scrollableHeight - 100 && !loading) {
        console.log(searchParams.get("page"));
        console.log(searchParams.get("query"));
        let page = parseInt(searchParams.get("page")) || 0; // 페이지 번호가 없을 경우 0으로 설정
        let updatedPage = page + 1;
        let query = searchParams.get("query") || ""; // 쿼리 파라미터 가져오기

        setSearchParams({
          page: updatedPage,
          query: query,
        });
      }
    };

    window.addEventListener("scroll", handleScroll);

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [searchParams]);

  return (
    <SubProductLayout
      setSearchParams={setSearchParams}
      setNoMoreProducts={setNoMoreProducts}
    >
      {/* 상품 그리드 섹션 */}
      <div className="max-w-7xl mx-auto px-4 pt-12">
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
        </div>
      </div>
    </SubProductLayout>
  );
}

export default WinterPage;

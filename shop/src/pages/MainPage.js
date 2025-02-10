import React, { useEffect, useState } from "react";
import ProductLayout from "../layouts/ProductLayout";
import ProductCardComponent from "../components/ProductCardComponent";
import { getProductList } from "../api/productApi";
import { useSearchParams } from "react-router-dom";

function MainPage() {
  const [products, setProduct] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const [loading, setLoading] = useState(false); // 로딩 상태 (중복 API 요청 방지)
  const [noMoreProducts, setNoMoreProducts] = useState(false); // 데이터가 없을 때 호출하는 것을 방지

  useEffect(() => {
    let query = searchParams.get("query");
    let page = searchParams.get("page");

    if (noMoreProducts || loading) return; // 로딩 중일 경우 API 요청 방지

    setLoading(true);
    if (query) {
      if (page === "0") {
        getProductList(page, 10, null, null, null, null, query)
          .then((res) => {
            const data = res.data;
            if (data.length === 0 || data.length < 10) {
              setNoMoreProducts(true);
            }
            setProduct(data);
          })
          .finally(() => {
            setLoading(false);
          });
      } else {
        getProductList(page, 10, null, null, null, null, query)
          .then((res) => {
            const data = res.data;
            if (data.length === 0 || data.length < 10) {
              setNoMoreProducts(true);
            }
            setProduct((prevProducts) => [...prevProducts, ...data]);
          })
          .finally(() => {
            setLoading(false);
          });
      }
    } else {
      getProductList(page, 10, null, null, null, null, null)
        .then((res) => {
          const data = res.data;
          if (data.length === 0 || data.length < 10) {
            setNoMoreProducts(true);
          }
          setProduct((prevProducts) => [...prevProducts, ...data]); // 새로운 상품을 기존 상품 리스트에 추가
        })
        .finally(() => {
          setLoading(false); // 로딩 완료 후 상태 리셋
        });
    }
  }, [searchParams]); // searchParams가 변경될 때마다 실행되도록 설정

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
    <div>
      <ProductLayout
        setSearchParams={setSearchParams}
        setNoMoreProducts={setNoMoreProducts}
      >
        {/* 상품 그리드 섹션 */}
        <div className="max-w-7xl mx-auto px-4">
          <h2 className="text-2xl font-bold mb-6">신상품</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {loading ? (
              <div className="spinner">Loading...</div> // You can replace this with a spinner or another indicator
            ) : (
              products.map((product) => (
                <div key={product.productId} className="w-full">
                  <ProductCardComponent
                    id={product.productId}
                    title={product.productName}
                    price={product.price}
                    image={product.imgUrl}
                  />
                </div>
              ))
            )}
          </div>
        </div>
      </ProductLayout>
    </div>
  );
}

export default MainPage;

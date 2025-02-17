import React, { useEffect, useState, useRef, useCallback } from "react";
import ProductCardComponent from "../components/ProductCardComponent";
import SubProductLayout from "../layouts/SubProductLayout";
import { getProductList } from "../api/productApi";
import { useSearchParams } from "react-router-dom";

function MenPage() {
  const [products, setProduct] = useState([]);
  const [loading, setLoading] = useState(false);
  const [noMoreProducts, setNoMoreProducts] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();

  const fetchProducts = useCallback(async (page, query) => {
    if (loading) return;
    setLoading(true);

    try {
      const res = await getProductList(
        page,
        10,
        null,
        "MEN",
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
        window.scrollTo(0, 0);
      } else {
        setProduct((prev) => [...prev, ...data]);
      }
    } catch (error) {
      console.error("Failed to fetch products:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    const query = searchParams.get("query");
    const page = searchParams.get("page") || "0";

    if (noMoreProducts) return;
    fetchProducts(page, query);
  }, [searchParams]);

  useEffect(() => {
    const handleScroll = () => {
      if (loading || noMoreProducts) return;

      const scrollHeight = document.documentElement.scrollHeight;
      const scrollTop = window.scrollY;
      const clientHeight = window.innerHeight;

      if (scrollHeight - scrollTop <= clientHeight + 100) {
        const currentPage = parseInt(searchParams.get("page") || "0");
        const query = searchParams.get("query") || "";

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
  }, [searchParams]);

  return (
    <SubProductLayout
      setNoMoreProducts={setNoMoreProducts}
      setSearchParams={setSearchParams}
    >
      {/* 그리드 레이아웃 클래스 추가 */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 mt-12">
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

export default MenPage;

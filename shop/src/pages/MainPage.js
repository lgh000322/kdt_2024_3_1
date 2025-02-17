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

  const fetchProducts = useCallback(async (page, query) => {
    if (loadingRef.current) return;

    loadingRef.current = true;
    setLoading(true);

    try {
      const res = await getProductList(page, 10, null, null, null, null, query);
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
      loadingRef.current = false;
    }
  }, []);

  useEffect(() => {
    const query = searchParams.get("query");
    const page = searchParams.get("page") || "0";

    if (noMoreProducts) return;
    fetchProducts(page, query);
  }, [searchParams, fetchProducts, noMoreProducts]);

  useEffect(() => {
    const handleScroll = () => {
      if (loadingRef.current || noMoreProducts) return;

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
  }, [noMoreProducts, searchParams, setSearchParams]);

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

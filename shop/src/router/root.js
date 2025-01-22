import { Suspense, lazy } from "react";
import { createBrowserRouter, Navigate } from "react-router-dom";

const Loading = <div>Loading ...</div>;

const Main = lazy(() => import("../pages/MainPage"));
const Login = lazy(() => import("../pages/LoginPage"));
const Summer = lazy(() => import("../pages/SummerPage"));
const Winter = lazy(() => import("../pages/WinterPage"));
const Men = lazy(() => import("../pages/MenPage"));
const Women = lazy(() => import("../pages/WomenPage"));
const Kids = lazy(() => import("../pages/KidsPage"));
const Mypage = lazy(() => import("../pages/Mypage"))
const AdminUserPage = lazy(() => import("../pages/AdminUserPage"));
const AdminSellerPage = lazy(() => import("../pages/AdminSellerPage"))
const AdminAcceptPage = lazy(()=> import("../pages/AdminAcceptPage"))
const AdminStatisticPage = lazy(()=> import("../pages/AdminStatisticPage"))
const AdminBannerPage = lazy(()=> import("../pages/AdminBannerPage"))
const SellerRegistration = lazy(() => import("../pages/SellerRegistrationPage"));
const ProductUploadPage = lazy(() => import("../pages/ProductUploadPage"));

const root = createBrowserRouter([
  {
    path: "",
    element: <Navigate to="/product" />,
  },
  {
    path: "product",
    element: (
      <Suspense fallback={Loading}>
        <Main />
      </Suspense>
    ),
 
  },
  {
    path: "login",
    element: (
      <Suspense fallback={Loading}>
        <Login />
      </Suspense>
    ),
  },
  {
    path: "summer",
    element: (
      <Suspense fallback={Loading}>
        <Summer />
      </Suspense>
    )
  },
  {
    path: "winter",
    element: (
      <Suspense fallback={Loading}>
        <Winter />
      </Suspense>
    )
  },
  {
    path: "men",
    element: (
      <Suspense fallback={Loading}>
        <Men />
      </Suspense>
    )
  },
  {
    path: "women",
    element: (
      <Suspense fallback={Loading}>
        <Women />
      </Suspense>
    )
  },
  {
    path: "kids",
    element: (
      <Suspense fallback={Loading}>
        <Kids />
      </Suspense>
    )
  },
  { 
    path: "mypage/order-list",
    element: (
      <Suspense fallback={Loading}>
        <Mypage/>
      </Suspense>
    ),
  },
  {
    path: "admin_user",
    element: (
      <Suspense fallback={Loading}>
        <AdminUserPage />
      </Suspense>
    ),
  },
  {
    path: "admin_seller",
    element:(
      <Suspense fallback={Loading}>
        <AdminSellerPage />
      </Suspense>
    )
  },
  {
    path: "admin_accept",
    element:(
      <Suspense fallback={Loading}>
        <AdminAcceptPage />
      </Suspense>
    )
  },
  {
    path: "admin_statistic",
    element:(
      <Suspense fallback={Loading}>
        <AdminStatisticPage />
      </Suspense>
    )
  },
  {
    path: "admin_banner",
    element:(
      <Suspense fallback={Loading}>
        <AdminBannerPage />
      </Suspense>
    )
  },
  {
    path: "mypage/seller-registration",
    element: (
      <Suspense fallback={Loading}>
        <SellerRegistration/>
      </Suspense>
    ),
  },
  {
    path: "productupload",
    element: (
      <Suspense fallback={Loading}>
        <ProductUploadPage/>
      </Suspense>
    ),
  }
]);

export default root;

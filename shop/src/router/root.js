import { Suspense, lazy } from "react";
import { createBrowserRouter, Navigate } from "react-router-dom";

const Loading = <div>Loading ...</div>;

const Main = lazy(() => import("../pages/MainPage"));
const Login = lazy(() => import("../pages/LoginPage"));
const Mypage = lazy(() => import("../pages/Mypage"))

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
    path: "mypage/order-list",
    element: (
      <Suspense fallback={Loading}>
        <Mypage/>
      </Suspense>
    ),
  }
]);

export default root;

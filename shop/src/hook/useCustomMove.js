import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { login, logout } from "../slice/loginSlice";

const useCustomMove = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const doLogin = (accessToken, role) => {
    dispatch(login({ accessToken: accessToken, role: role }));
  };

  const doLogout = () => {
    dispatch(logout());
  };

  const moveToProduct = () => {
    navigate({ pathname: "../product" });
  };

  const moveToProductAbs = () => {
    navigate({ pathname: "/product" });
  };

  const moveToOrderDetailPage = (orderHistoryId, accessToken) => {
    if (!accessToken) return;

    navigate(
      `/mypage/order-detail/${orderHistoryId}?token=${encodeURIComponent(
        accessToken
      )}`
    );
  };

  const moveToCheckOut = (price) => {
    navigate({ pathname: "/checkout" });
  };

  const moveToProductOne = (productId) => {
    navigate({ pathname: `/product/${productId}` });
  };

  const moveToAuthenticated = () => {
    navigate({ pathname: "/login/authenticate" });
  };

  const moveToLoginPage = () => {
    navigate({ pathname: "/login" });
  };

  const moveToCart = () => {
    navigate({ pathname: "/cart" });
  };

  const moveToDestination = () => {
    navigate({ pathname: "/mypage/shipping-address" });
  };

  const moveToProductPayment = (productName, totalPrice) => {
    navigate({
      pathname: "/productpayment",
      search: `?productName=${encodeURIComponent(
        productName
      )}&totalPrice=${totalPrice}`,
    });
  };

  const moveToPay = (productName, price, orderId) => {
    navigate({
      pathname: "/checkout",
      search: `?productName=${encodeURIComponent(
        productName
      )}&price=${price}&orderId=${orderId}`,
    });
  };

  return {
    moveToProduct,
    moveToProductAbs,
    moveToAuthenticated,
    moveToOrderDetailPage,
    doLogin,
    doLogout,
    moveToLoginPage,
    moveToProductOne,
    moveToCheckOut,
    moveToCart,
    moveToDestination,
    moveToProductPayment,
    moveToPay,
  };
};

export default useCustomMove;

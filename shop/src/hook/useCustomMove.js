import { useNavigate } from "react-router-dom";

const useCustomMove = () => {
  const navigate = useNavigate();

  const moveToProduct = () => {
    navigate({ pathname: "../product" });
  };

  const moveToProductAbs = () => {
    navigate({ pathname: "/product" });
  };

  const moveToAuthenticated = () => {
    navigate({ pathname: "/login/second" });
  };

  return { moveToProduct, moveToProductAbs, moveToAuthenticated };
};

export default useCustomMove;

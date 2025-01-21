import { useNavigate } from "react-router-dom";

const useCustomMove = () => {
  const navigate = useNavigate();

  const moveToProduct = () => {
    navigate({ pathname: "../product" });
  };

  return { moveToProduct };
};

export default useCustomMove;
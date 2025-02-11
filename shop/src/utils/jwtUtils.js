export const getRoleFromAccessToken = (accessToken) => {
  // JWT의 Payload 부분 가져오기 (가운데 부분)
  const base64Url = accessToken.split(".")[1];
  if (!base64Url) return null;

  // Base64 디코딩
  try {
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const decodedPayload = JSON.parse(atob(base64));

    // role 정보 추출
    const role = decodedPayload.role;
    return { role };
  } catch (error) {
    console.error("Failed to decode JWT:", error);
    return null;
  }
};

export const getSubFromJWT = (accessToken) => {
  // JWT의 Payload는 토큰의 두 번째 부분입니다.
  const base64Url = accessToken.split(".")[1];

  // Base64 URL을 디코딩합니다.
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split("")
      .map(function (c) {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join("")
  );

  // JSON으로 변환하고 sub 값만 반환합니다.
  return JSON.parse(jsonPayload).sub;
};

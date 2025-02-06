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
    return { accessToken, role };
  } catch (error) {
    console.error("Failed to decode JWT:", error);
    return null;
  }
};

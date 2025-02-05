export const getRoleFromAccessToken = (accessToken) => {
  const base64Url = accessToken.split(".")[1];
  if (!base64Url) return null;

  try {
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const decodedPayload = JSON.parse(atob(base64));
    return decodedPayload.role; // role 값만 반환
  } catch (error) {
    console.error("Failed to decode JWT:", error);
    return null;
  }
};

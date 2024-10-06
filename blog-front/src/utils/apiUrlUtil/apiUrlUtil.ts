export const KEYCLOAK_URL = () => `http://localhost:8181/`;
export const BLOG_URL = () => `http://localhost:3000`;
export const API_GATEWAY_URL = () => `http://localhost:4040`;



// export const KEYCLOAK_URL = () => `https://keycloak.ghtjr.com/`;
// export const BLOG_URL = () => `https://blog.ghtjr.com`;
// export const API_GATEWAY_URL = () => `https://api.ghtjr.com`;


// 환경 공통
export const CLOUD_FRONT_URL = () => `https://images.ghtjr.com/`;
export const API_IMAGE_PRESIGNED_URL = () => API_GATEWAY_URL +`/api/user/images/presigned-url`;
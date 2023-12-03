import { Cookies } from 'react-cookie';

const cookies = new Cookies();
const COOKIE_USER_ID = 'user_id';

const setCookie = (userId: string) => {
  cookies.set(COOKIE_USER_ID, userId, {
    path: '/',
    sameSite: 'strict',
  });
};

const getCookie = () => {
  return cookies.get(COOKIE_USER_ID);
};

export { setCookie, getCookie };

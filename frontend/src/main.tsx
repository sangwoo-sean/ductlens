import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import AdminApp from './components/admin/App.tsx';

import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { getCookie } from './lib/cookie.ts';
import { APIClient } from './lib/APIClient.ts';

const UserIdProvider = ({ children }: { children: React.ReactNode }) => {
  const cookie = getCookie();
  console.log(cookie);
  // 최초진입시 쿠키 검사
  // 없으면 쿠키생성 엔드포인트로가서 서버에서 저장한 UUID 보내줌
  // 클라에 쿠키 저장
  // 요청시에 쿠키 뽑아 쓸 수 있게 됨

  // if (!cookie) {
  //   // send API for setting cookie UUID
  //   new APIClient().fetch<string>('/user').then((res) => {
  //     console.log(res);
  //   });
  // }

  return children;
};

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <UserIdProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="/admin/*" element={<AdminApp />} />
        </Routes>
      </BrowserRouter>
    </UserIdProvider>
  </React.StrictMode>,
);

import { Link, Route, Routes } from 'react-router-dom';
import Home from './Home';
import Products from './Products';
import ProductAddForm from './ProductAddForm';
import logoPng from '@assets/logo.png';

function App() {
  return (
    <div className="flex w-full h-[100vh]">
      <aside className="w-[200px] border-r p-2">
        <div className="h-20 flex justify-center">
          <Link to="/admin">
            <img src={logoPng} alt="logo" className="w-20 h-20"/>
          </Link>
        </div>
        <ul>
          <li className="flex flex-col">
            <Link to="/admin/products" className="border-t p-2 hover:bg-gray-300">Products</Link>
            <Link to="/admin/products/add" className="border-t p-2 hover:bg-gray-300">신규등록</Link>
          </li>
        </ul>
      </aside>
      <main className="flex-1">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/products" element={<Products />}></Route>
          <Route path="/products/add" element={<ProductAddForm />}></Route>
        </Routes>
      </main>
    </div>
  );
}

export default App;

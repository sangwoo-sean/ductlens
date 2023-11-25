import { Link, Route, Routes } from 'react-router-dom';
import Home from './Home';
import Products from './Products';

function App() {
  return (
    <div className="flex w-full h-[100vh]">
      <aside className="w-[200px] border-r p-2">
        <div className="h-[40px]">
          <Link to="/admin">logo</Link>
        </div>
        <ul>
          <li>
            <Link to="/admin/products">Products</Link>
          </li>
        </ul>
      </aside>
      <main className="flex-1">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/products" element={<Products />}></Route>
        </Routes>
      </main>
    </div>
  );
}

export default App;
